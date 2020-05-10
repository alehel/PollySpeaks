package io.aleksander.controller.actions;

import io.aleksander.model.AudioStreamPlayer;
import io.aleksander.model.TextToSpeechEngine;
import io.aleksander.utils.StringResource;
import javazoom.jl.decoder.JavaLayerException;

import javax.swing.JOptionPane;
import java.awt.Component;
import java.io.InputStream;

import static io.aleksander.utils.StringResource.SOUND_PLAYBACK_ERROR;

public class SpeakTextAction implements Action {

  private final String text;
  private final TextToSpeechEngine textToSpeechEngine;
  private final Component parent;
  private final AudioStreamPlayer audioStreamPlayer;

  public SpeakTextAction(
      Component parent,
      TextToSpeechEngine textToSpeechEngine,
      AudioStreamPlayer audioStreamPlayer,
      String text) {
    this.parent = parent;
    this.text = text.trim();
    this.textToSpeechEngine = textToSpeechEngine;
    this.audioStreamPlayer = audioStreamPlayer;
  }

  @Override
  public void performAction() {
    if (!text.isEmpty()) {
      new Thread(
              () -> {
                InputStream synthesizedText = textToSpeechEngine.convertTextToSpeech(text);
                try {
                  audioStreamPlayer.playStream(synthesizedText);
                } catch (JavaLayerException exception) {
                  JOptionPane.showMessageDialog(
                      parent,
                      StringResource.getString(StringResource.ERROR),
                      StringResource.getString(SOUND_PLAYBACK_ERROR),
                      JOptionPane.ERROR_MESSAGE);
                }
              })
          .start();
    }
  }
}
