package io.aleksander.pollyspeaks.controller;

import static io.aleksander.pollyspeaks.utils.StringResource.APPLICATION_TITLE;

import com.amazonaws.services.polly.model.Voice;
import io.aleksander.pollyspeaks.controller.actions.SpeakTextAction;
import io.aleksander.pollyspeaks.controller.actions.WindowActions;
import io.aleksander.pollyspeaks.controller.listeners.JTextFieldChangeListener;
import io.aleksander.pollyspeaks.gui.MainFrame;
import io.aleksander.pollyspeaks.gui.viewmodel.VoiceSelectModelElement;
import io.aleksander.pollyspeaks.model.AudioStreamPlayer;
import io.aleksander.pollyspeaks.model.DocumentMetadata;
import io.aleksander.pollyspeaks.model.TextToSpeechEngine;
import io.aleksander.pollyspeaks.utils.StringResource;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Objects;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainController implements PropertyChangeListener {
  private static final Logger logger = LogManager.getLogger(MainController.class);

  private final TextToSpeechEngine textToSpeechEngine;
  DocumentMetadata documentMetadata;
  AudioStreamPlayer audioStreamPlayer;
  MainFrame view;

  public MainController() {
    textToSpeechEngine = new TextToSpeechEngine();
    textToSpeechEngine.addPropertyChangeListener(this);

    this.view = new MainFrame();

    setUpDocumentHandler();
    setUpTextArea();
    setUpAudioPlayer();
    setUpLanguageSelector();
    setUpSSMLSelector();
    setUpSpeakButton();
    setUpMenuBar();
    setWindowCloseBehaviour();

    view.setLocationByPlatform(true);
    view.setVisible(true);
  }

  private void setUpTextArea() {
    Font defaultFont = view.getTextArea().getFont();
    // Font is immutable, create a new default Font.
    Font newDefaultFont = new Font(defaultFont.getName(), defaultFont.getStyle(), 11);
    view.getTextArea().setFont(newDefaultFont);
  }

  private void setUpDocumentHandler() {
    documentMetadata = new DocumentMetadata();
    view.getTextArea().getDocument().addDocumentListener(new JTextFieldChangeListener(documentMetadata));
    documentMetadata.addPropertyChangeListener(this);
    setWindowTitle(documentMetadata);
  }

  private void setUpAudioPlayer() {
    audioStreamPlayer = new AudioStreamPlayer();
    audioStreamPlayer.addPropertyChangeListener(this);
  }

  private void setUpLanguageSelector() {
    JComboBox<String> languageSelector = view.getSettingsPanel().getLanguageSelector();
    DefaultComboBoxModel<String> languageComboBoxModel = new DefaultComboBoxModel<>();
    languageComboBoxModel.addAll(textToSpeechEngine.getAvailableLanguages());
    languageSelector.setModel(languageComboBoxModel);
    languageSelector.addActionListener(
        action -> {
          String language = (String) languageSelector.getSelectedItem();
          Objects.requireNonNull(language);
          textToSpeechEngine.setLanguage(language);
        });
    languageSelector.setSelectedIndex(0);
  }

  private void setUpSSMLSelector() {
    boolean useSsml = documentMetadata.isSsmlMarkup();
    view.getSettingsPanel().getSsmlCheckbox().addActionListener(e -> {
      JCheckBox checkBox = (JCheckBox) e.getSource();
      documentMetadata.setSsmlMarkup(checkBox.isSelected());
      textToSpeechEngine.setUseSSML(checkBox.isSelected());
    });
    view.getSettingsPanel().getSsmlCheckbox().setSelected(useSsml);
    textToSpeechEngine.setUseSSML(useSsml);
  }

  private void setUpSpeakButton() {
    view.getSettingsPanel().getSpeakButton().addActionListener(e -> {
      String text = view.getTextArea().getText();
      new SpeakTextAction(view, textToSpeechEngine, audioStreamPlayer, text).performAction();
    });
  }

  private void setUpMenuBar() {
    new ApplicationMenuBarController(view, view.getTextArea(), documentMetadata, textToSpeechEngine);
  }

  private void setWindowCloseBehaviour() {
    view.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        WindowActions.exitApplication(view, documentMetadata);
      }
    });

    view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
  }

  private void setWindowTitle(DocumentMetadata documentMetadata) {
    String name = documentMetadata.getDocumentName();

    if (documentMetadata.isTextIsAltered()) {
      name = "*" + name;
    }

    view.setTitle(name + " - " + StringResource.getString(APPLICATION_TITLE));
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    switch (evt.getPropertyName()) {
      case "isPlaying" -> {
        boolean isPlaying = (boolean) evt.getNewValue();
        view.getSettingsPanel().getSpeakButton().setEnabled(!isPlaying);
      }
      case "documentName", "textIsAltered" -> setWindowTitle(documentMetadata);
      case "availableVoices" -> {
        @SuppressWarnings("unchecked") List<Voice> availableVoices = (List<Voice>) evt.getNewValue();
        setAvailableVoices(availableVoices);
      }
    }
  }

  private void setAvailableVoices(List<Voice> availableVoices) {
    JComboBox<VoiceSelectModelElement> voiceSelector = view.getSettingsPanel().getVoiceSelector();
    DefaultComboBoxModel<VoiceSelectModelElement> voiceComboBoxModel = new DefaultComboBoxModel<>();

    availableVoices
        .forEach(voice -> voiceComboBoxModel.addElement(convertVoiceToVoiceSelectModel(voice)));

    voiceSelector.setModel(voiceComboBoxModel);
    voiceSelector.addActionListener(
        action -> {
          VoiceSelectModelElement selectedVoice = (VoiceSelectModelElement) voiceSelector.getSelectedItem();
          Objects.requireNonNull(selectedVoice);
          textToSpeechEngine.setVoiceId(selectedVoice.getId());
        });
    voiceSelector.setSelectedIndex(0);
  }

  private VoiceSelectModelElement convertVoiceToVoiceSelectModel(Voice voice) {
    return new VoiceSelectModelElement(voice.getName() + ", " + voice.getGender(), voice.getId());
  }
}
