package io.aleksander.controller;

import com.amazonaws.services.polly.model.Voice;
import io.aleksander.controller.actions.ExitAction;
import io.aleksander.controller.actions.SpeakTextAction;
import io.aleksander.controller.listeners.JTextFieldChangeListener;
import io.aleksander.gui.MainFrame;
import io.aleksander.gui.viewmodel.VoiceSelectModelElement;
import io.aleksander.model.AudioStreamPlayer;
import io.aleksander.model.DocumentMetadata;
import io.aleksander.model.TextToSpeechEngine;
import io.aleksander.utils.StringResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Objects;

import static io.aleksander.utils.StringResource.APPLICATION_TITLE;

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
          textToSpeechEngine.setLanguage(language);
        });
    languageSelector.setSelectedIndex(0);
  }

  private void setUpSpeakButton() {
    view.getSettingsPanel()
        .getSpeakButton()
        .addActionListener(e -> {
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
        (new ExitAction(view, documentMetadata)).performAction();
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
      default -> logger.debug("Ignoring property change: " + evt.getPropertyName());
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
          Objects.requireNonNull(selectedVoice, "VoiceSelectModelElement must not be null.");
          textToSpeechEngine.setVoiceId(selectedVoice.getId());
        });
    voiceSelector.setSelectedIndex(0);
  }

  private VoiceSelectModelElement convertVoiceToVoiceSelectModel(Voice voice) {
    return new VoiceSelectModelElement(voice.getName() + ", " + voice.getGender(), voice.getId());
  }
}
