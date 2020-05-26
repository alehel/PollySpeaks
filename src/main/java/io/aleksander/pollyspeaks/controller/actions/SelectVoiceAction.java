package io.aleksander.pollyspeaks.controller.actions;

import io.aleksander.pollyspeaks.model.TextToSpeechEngine;
import java.util.Objects;

public class SelectVoiceAction implements Action {

  private final TextToSpeechEngine textToSpeechEngine;
  private final String voiceId;

  public SelectVoiceAction(TextToSpeechEngine textToSpeechEngine, String voiceId) {
    Objects.requireNonNull(textToSpeechEngine);
    Objects.requireNonNull(voiceId);

    this.textToSpeechEngine = textToSpeechEngine;
    this.voiceId = voiceId;
  }

  @Override
  public void performAction() {
    textToSpeechEngine.setVoiceId(voiceId);
  }
}
