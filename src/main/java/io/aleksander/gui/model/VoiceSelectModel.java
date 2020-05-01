package io.aleksander.gui.model;

import com.amazonaws.services.polly.model.Voice;

public class VoiceSelectModel {
  private final Voice voice;

  public VoiceSelectModel(Voice voice) {
    this.voice = voice;
  }

  public String getId() {
    return voice.getId();
  }

  @Override
  public String toString() {
    return voice.getName() + ", " + voice.getLanguageName();
  }
}
