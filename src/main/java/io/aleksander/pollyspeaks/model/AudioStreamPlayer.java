package io.aleksander.pollyspeaks.model;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.InputStream;

public class AudioStreamPlayer extends AbstractModel {
  private boolean isPlaying = false;

  public void playStream(InputStream inputStream) throws JavaLayerException {
    AudioDevice audioDevice = FactoryRegistry.systemRegistry().createAudioDevice();
    AdvancedPlayer player = new AdvancedPlayer(inputStream, audioDevice);
    player.setPlayBackListener(
        new PlaybackListener() {
          @Override
          public void playbackStarted(PlaybackEvent evt) {
            super.playbackStarted(evt);
            AudioStreamPlayer.this.setIsPlaying(true);
          }

          @Override
          public void playbackFinished(PlaybackEvent evt) {
            super.playbackFinished(evt);
            AudioStreamPlayer.this.setIsPlaying(false);
          }
        });
    player.play();
  }

  private void setIsPlaying(boolean isPlaying) {
    boolean oldValue = this.isPlaying;
    this.isPlaying = isPlaying;
    firePropertyChange("isPlaying", oldValue, isPlaying);
  }

  public boolean isPlaying() {
    return isPlaying;
  }
}
