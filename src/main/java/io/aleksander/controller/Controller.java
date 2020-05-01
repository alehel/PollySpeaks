package io.aleksander.controller;

import com.amazonaws.services.polly.model.Voice;
import io.aleksander.gui.model.VoiceSelectModel;
import io.aleksander.utils.PollyClient;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
  private final PollyClient pollyClient;

  public Controller() {
    pollyClient = PollyClient.getInstance();
  }

  public List<VoiceSelectModel> getVoices() {
    List<Voice> pollyVoices = pollyClient.getVoices();
    return pollyVoices.stream().map(VoiceSelectModel::new).collect(Collectors.toList());
  }

  public void synthesizeText(String voiceId, String text) {
    InputStream synthesizedText = pollyClient.synthesizeText(voiceId, text);
    try {
      playStream(synthesizedText);
    } catch (JavaLayerException exception) {
      // TODO create and show warning message.
      System.out.println("playStream exception");
    }
  }

  private void playStream(InputStream inputStream) throws JavaLayerException {
    AdvancedPlayer player =
        new AdvancedPlayer(
            inputStream, javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

    player.setPlayBackListener(
        new PlaybackListener() {
          @Override
          public void playbackStarted(PlaybackEvent evt) {
            System.out.println("Playback started");
          }

          @Override
          public void playbackFinished(PlaybackEvent evt) {
            System.out.println("Playback finished");
          }
        });

    // play it!
    player.play();
  }
}
