package io.aleksander.pollyspeaks.controller.actions;

import io.aleksander.pollyspeaks.controller.ExportAudioController;
import io.aleksander.pollyspeaks.model.TextToSpeechEngine;

import java.awt.Component;
import java.util.Objects;

public class ExportToAudioAction implements Action {
  Component parent;
  TextToSpeechEngine textToSpeechEngine;
  String text;

  public ExportToAudioAction(Component parent, TextToSpeechEngine textToSpeechEngine, String text) {
    Objects.requireNonNull(parent);
    Objects.requireNonNull(textToSpeechEngine);
    Objects.requireNonNull(text);

    this.parent = parent;
    this.textToSpeechEngine = textToSpeechEngine;
    this.text = text;
  }

  @Override
  public void performAction() {
    new ExportAudioController(parent, textToSpeechEngine, text);
  }
}
