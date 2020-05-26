package io.aleksander.pollyspeaks.controller;

import static io.aleksander.pollyspeaks.utils.StringResource.APPLICATION_TITLE;

import com.amazonaws.services.polly.model.Voice;
import io.aleksander.pollyspeaks.controller.actions.ExitAction;
import io.aleksander.pollyspeaks.controller.actions.SelectLanguageAction;
import io.aleksander.pollyspeaks.controller.actions.SelectVoiceAction;
import io.aleksander.pollyspeaks.controller.actions.SpeakTextAction;
import io.aleksander.pollyspeaks.controller.actions.UseSSMLAction;
import io.aleksander.pollyspeaks.controller.listeners.JTextFieldChangeListener;
import io.aleksander.pollyspeaks.gui.MainFrame;
import io.aleksander.pollyspeaks.gui.viewmodel.VoiceSelectModelElement;
import io.aleksander.pollyspeaks.model.AudioStreamPlayer;
import io.aleksander.pollyspeaks.model.DocumentMetadata;
import io.aleksander.pollyspeaks.model.TextToSpeechEngine;
import io.aleksander.pollyspeaks.utils.StringResource;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
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
    setUpAudioPlayer();
    setUpLanguageSelector();
    setUpSSMLSelector();
    setUpSpeakButton();
    setUpMenuBar();
    setWindowCloseBehaviour();

    view.setLocationByPlatform(true);
    view.setVisible(true);
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
          new SelectLanguageAction(textToSpeechEngine, language).performAction();
        });
    languageSelector.setSelectedIndex(0);
  }

  private void setUpSSMLSelector() {
    boolean useSsml = documentMetadata.isSsmlMarkup();
    view.getSettingsPanel().getSsmlCheckbox().addActionListener(e -> {
      JCheckBox checkBox = (JCheckBox) e.getSource();
      new UseSSMLAction(documentMetadata, textToSpeechEngine, checkBox.isSelected()).performAction();
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
        new ExitAction(view, documentMetadata).performAction();
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
          if(selectedVoice != null) {
            new SelectVoiceAction(textToSpeechEngine, selectedVoice.getId()).performAction();
          }
        });
    voiceSelector.setSelectedIndex(0);
  }

  private VoiceSelectModelElement convertVoiceToVoiceSelectModel(Voice voice) {
    return new VoiceSelectModelElement(voice.getName() + ", " + voice.getGender(), voice.getId());
  }
}
