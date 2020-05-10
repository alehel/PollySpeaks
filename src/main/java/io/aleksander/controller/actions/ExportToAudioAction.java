package io.aleksander.controller.actions;

import io.aleksander.controller.ExportAudioController;
import io.aleksander.model.TextToSpeechEngine;

public class ExportToAudioAction implements Action {
  TextToSpeechEngine textToSpeechEngine;
  String text;

  public ExportToAudioAction(TextToSpeechEngine textToSpeechEngine, String text) {
    this.textToSpeechEngine = textToSpeechEngine;
    this.text = text;
  }

  @Override
  public void performAction() {
    new ExportAudioController(textToSpeechEngine, text);
  }
}
