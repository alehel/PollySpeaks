package io.aleksander.gui.event;

import java.util.EventObject;

public class SpeakEvent extends EventObject {
  String voiceId;

  public SpeakEvent(Object source) {
    super(source);
  }

  public SpeakEvent(Object source, String voiceId) {
    super(source);
    this.voiceId = voiceId;
  }

  public String getVoiceId() {
    return voiceId;
  }

  public void setVoiceId(String voiceId) {
    this.voiceId = voiceId;
  }
}
