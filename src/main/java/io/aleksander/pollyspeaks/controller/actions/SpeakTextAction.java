package io.aleksander.pollyspeaks.controller.actions;

import static io.aleksander.pollyspeaks.utils.StringResource.SOUND_PLAYBACK_ERROR;
import static io.aleksander.pollyspeaks.utils.StringResource.SSML_PARSE_ERROR;

import com.amazonaws.services.polly.model.InvalidSsmlException;
import io.aleksander.pollyspeaks.model.AudioStreamPlayer;
import io.aleksander.pollyspeaks.model.TextToSpeechEngine;
import io.aleksander.pollyspeaks.utils.StringResource;
import java.awt.Component;
import java.io.InputStream;
import java.util.Objects;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;

public class SpeakTextAction implements Action {

  private final String text;
  private final TextToSpeechEngine textToSpeechEngine;
  private final Component frame;
  private final AudioStreamPlayer audioStreamPlayer;

  public SpeakTextAction(
      Component frame,
      TextToSpeechEngine textToSpeechEngine,
      AudioStreamPlayer audioStreamPlayer,
      String text) {
    Objects.requireNonNull(frame);
    Objects.requireNonNull(text);
    Objects.requireNonNull(textToSpeechEngine);
    Objects.requireNonNull(audioStreamPlayer);

    this.frame = frame;
    this.text = text.trim();
    this.textToSpeechEngine = textToSpeechEngine;
    this.audioStreamPlayer = audioStreamPlayer;
  }

  @Override
  public void performAction() {
    if (!text.isEmpty()) {
      new Thread(
              () -> {
                try {
                  InputStream synthesizedText = textToSpeechEngine.convertTextToSpeech(text);
                  audioStreamPlayer.playStream(synthesizedText);
                } catch (JavaLayerException exception) {
                  JOptionPane.showMessageDialog(
                      frame,
                      StringResource.getString(SOUND_PLAYBACK_ERROR),
                      StringResource.getString(StringResource.ERROR),
                      JOptionPane.ERROR_MESSAGE);
                } catch (InvalidSsmlException exception) {
                  JOptionPane.showMessageDialog(
                      frame,
                      StringResource.getString(SSML_PARSE_ERROR),
                      StringResource.getString(StringResource.ERROR),
                      JOptionPane.ERROR_MESSAGE);
                }
              }).start();
    }
  }
}
