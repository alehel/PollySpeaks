package io.aleksander.controller;

import com.amazonaws.services.polly.model.Voice;
import io.aleksander.controller.listeners.TextAreaChangeListener;
import io.aleksander.gui.MainFrame;
import io.aleksander.gui.viewmodel.VoiceSelectModelElement;
import io.aleksander.model.AudioStreamPlayer;
import io.aleksander.model.DocumentMetadata;
import io.aleksander.model.TextToSpeechEngine;
import io.aleksander.utils.StringResource;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.util.List;

import static io.aleksander.utils.StringResource.APPLICATION_TITLE;
import static io.aleksander.utils.StringResource.SOUND_PLAYBACK_ERROR;

public class MainController implements PropertyChangeListener {
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

    view.setVisible(true);
  }

  private void setUpDocumentHandler() {
    documentMetadata = new DocumentMetadata();
    documentMetadata.addPropertyChangeListener(this);
    this.view
        .getTextArea()
        .getDocument()
        .addDocumentListener(new TextAreaChangeListener(documentMetadata));
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
        .addActionListener(
            event -> {
              String text = view.getTextArea().getText();
              text = text.trim();
              if (!text.isEmpty()) {
                speakText(text);
              }
            });
  }

  private void setUpMenuBar() {
    new ApplicationMenuBarController(view, view.getTextArea(), documentMetadata);
  }

  private void setWindowTitle(DocumentMetadata documentMetadata) {
    String name = documentMetadata.getDocumentName();

    if (documentMetadata.isTextIsAltered()) {
      name = "*" + name;
    }

    view.setTitle(name + " - " + StringResource.getString(APPLICATION_TITLE));
  }

  public void speakText(String text) {
    Thread thread =
        new Thread(
            () -> {
              InputStream synthesizedText = textToSpeechEngine.convertTextToSpeech(text);
              try {
                audioStreamPlayer.playStream(synthesizedText);
              } catch (JavaLayerException exception) {
                JOptionPane.showMessageDialog(
                    view,
                    StringResource.getString(StringResource.ERROR),
                    StringResource.getString(SOUND_PLAYBACK_ERROR),
                    JOptionPane.ERROR_MESSAGE);
              }
            });

    thread.start();
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
        if(evt.getNewValue() instanceof List<?>) {
          setAvailableVoices((List<Voice>) evt.getNewValue());
        }
      }
      default -> System.out.println("Ignoring property: " + evt.getPropertyName());
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
          textToSpeechEngine.setVoiceId(selectedVoice.getId());
        });
    voiceSelector.setSelectedIndex(0);
  }

  private VoiceSelectModelElement convertVoiceToVoiceSelectModel(Voice voice) {
    return new VoiceSelectModelElement(voice.getName() + ", " + voice.getGender(), voice.getId());
  }
}
