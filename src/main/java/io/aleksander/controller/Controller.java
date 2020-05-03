package io.aleksander.controller;

import com.amazonaws.services.polly.model.Voice;
import io.aleksander.gui.MainFrame;
import io.aleksander.gui.viewmodel.VoiceSelectModel;
import io.aleksander.model.AudioStreamPlayer;
import io.aleksander.model.TextToSpeechEngine;
import io.aleksander.utils.StringResource;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.io.InputStream;

import static io.aleksander.utils.StringResource.SOUND_PLAYBACK_ERROR;

public class Controller {
  private final TextToSpeechEngine textToSpeechEngine;
  AudioStreamPlayer audioStreamPlayer;
  MainFrame view;

  public Controller() {
    textToSpeechEngine = new TextToSpeechEngine();
    this.view = new MainFrame();

    setUpAudioPlayer();
    setUpLanguageSelector();
    setUpVoiceSelector();
    setUpSpeakButton();

    view.setVisible(true);
  }

  private void setUpSpeakButton() {
    view.getSettingsPanel()
        .getSpeakButton()
        .addActionListener(
            event -> {
              String text = view.getTextArea().getText();
              text = text.trim();
              if(!text.isEmpty()) {
                speakText(text);
              }
            });
  }

  private void setUpVoiceSelector() {
    JComboBox<VoiceSelectModel> voiceSelector = view.getSettingsPanel().getVoiceSelector();
    DefaultComboBoxModel<VoiceSelectModel> voiceComboBoxModel = new DefaultComboBoxModel<>();

    textToSpeechEngine
        .getAvailableVoices()
        .forEach(voice -> voiceComboBoxModel.addElement(convertVoiceToVoiceSelectModel(voice)));

    voiceSelector.setModel(voiceComboBoxModel);
    voiceSelector.addActionListener(
        action -> {
          VoiceSelectModel selectedVoice =
              (VoiceSelectModel) view.getSettingsPanel().getVoiceSelector().getSelectedItem();
          textToSpeechEngine.setVoiceId(selectedVoice.getId());
        });
    voiceSelector.setSelectedIndex(0);
  }

  private VoiceSelectModel convertVoiceToVoiceSelectModel(Voice voice) {
    return new VoiceSelectModel(voice.getName() + ", " + voice.getGender(), voice.getId());
  }

  private void setUpAudioPlayer() {
    audioStreamPlayer = new AudioStreamPlayer();
    audioStreamPlayer.addPropertyChangeListener(view.getSettingsPanel());
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
          setUpVoiceSelector();
        });
    languageSelector.setSelectedIndex(0);
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
}
