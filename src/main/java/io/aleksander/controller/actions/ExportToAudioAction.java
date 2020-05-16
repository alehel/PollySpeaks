package io.aleksander.controller.actions;

import io.aleksander.controller.ExportAudioController;
import io.aleksander.model.TextToSpeechEngine;

import java.awt.Component;

public class ExportToAudioAction implements Action {
  Component parent;
  TextToSpeechEngine textToSpeechEngine;
  String text;

  public ExportToAudioAction(Component parent, TextToSpeechEngine textToSpeechEngine, String text) {
    this.parent = parent;
    this.textToSpeechEngine = textToSpeechEngine;
    this.text = text;
  }

  @Override
  public void performAction() {
    new ExportAudioController(parent, textToSpeechEngine, text);
  }
}
