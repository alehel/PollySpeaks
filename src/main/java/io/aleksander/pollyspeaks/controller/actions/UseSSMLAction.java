package io.aleksander.pollyspeaks.controller.actions;

import io.aleksander.pollyspeaks.model.DocumentMetadata;
import io.aleksander.pollyspeaks.model.TextToSpeechEngine;
import java.util.Objects;

public class UseSSMLAction implements Action {

  private final DocumentMetadata documentMetadata;
  private final TextToSpeechEngine textToSpeechEngine;
  private final boolean useSSML;

  public UseSSMLAction(
      DocumentMetadata documentMetadata, TextToSpeechEngine textToSpeechEngine, boolean useSSML) {
    Objects.requireNonNull(documentMetadata);
    Objects.requireNonNull(textToSpeechEngine);

    this.documentMetadata = documentMetadata;
    this.textToSpeechEngine = textToSpeechEngine;
    this.useSSML = useSSML;
  }

  @Override
  public void performAction() {
    documentMetadata.setSsmlMarkup(useSSML);
    textToSpeechEngine.setUseSSML(useSSML);
  }
}
