package io.aleksander.pollyspeaks.controller.actions;

import io.aleksander.pollyspeaks.model.TextToSpeechEngine;
import java.util.Objects;

public class SelectLanguageAction implements Action {

  private final TextToSpeechEngine textToSpeechEngine;
  private final String language;

  public SelectLanguageAction(TextToSpeechEngine textToSpeechEngine, String language) {
    Objects.requireNonNull(textToSpeechEngine);
    Objects.requireNonNull(language);

    this.textToSpeechEngine = textToSpeechEngine;
    this.language = language;
  }

  @Override
  public void performAction() {
    textToSpeechEngine.setLanguage(language);
  }
}
