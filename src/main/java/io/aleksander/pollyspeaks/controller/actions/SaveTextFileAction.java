package io.aleksander.pollyspeaks.controller.actions;

import io.aleksander.pollyspeaks.model.DocumentMetadata;
import io.aleksander.pollyspeaks.utils.FileHandler;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.swing.text.JTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveTextFileAction implements Action {
  private static final Logger logger = LogManager.getLogger(SaveTextFileAction.class);

  private final JTextComponent textArea;
  private final Component view;
  private final DocumentMetadata documentMetadata;

  public SaveTextFileAction(
      Component parent, JTextComponent textArea, DocumentMetadata documentMetadata) {
    Objects.requireNonNull(parent);
    Objects.requireNonNull(textArea);
    Objects.requireNonNull(documentMetadata);

    this.view = parent;
    this.textArea = textArea;
    this.documentMetadata = documentMetadata;
  }

  @Override
  public void performAction() {
    // if the file hasn't been saved before, show the "Save As..." dialog.
    if (documentMetadata.getDocumentPath() == null) {
      SaveAsTextFileAction saveAsTextFileAction =
          new SaveAsTextFileAction(view, textArea, documentMetadata);
      saveAsTextFileAction.performAction();
    } else {
      String text = textArea.getText();
      File file = new File(documentMetadata.getDocumentPath());
      try {
        FileHandler.writeTextToFile(text, file.getAbsolutePath());
        documentMetadata.setTextIsAltered(false);
      } catch (IOException exception) {
        logger.error("Unable to write text file to disk.", exception);
      }
    }
  }
}
