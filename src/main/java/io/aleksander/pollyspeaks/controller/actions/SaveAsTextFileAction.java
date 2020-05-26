package io.aleksander.pollyspeaks.controller.actions;

import io.aleksander.pollyspeaks.App;
import io.aleksander.pollyspeaks.controller.FileFilters;
import io.aleksander.pollyspeaks.model.DocumentMetadata;
import io.aleksander.pollyspeaks.utils.FileHandler;
import io.aleksander.pollyspeaks.utils.StringResource;

import java.util.Objects;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.aleksander.pollyspeaks.utils.StringResource.OVERWRITE_FILE;
import static io.aleksander.pollyspeaks.utils.StringResource.WARNING;

public class SaveAsTextFileAction implements Action {
  private static final Logger logger = LogManager.getLogger(SaveAsTextFileAction.class);

  private final Component parent;
  private final JTextComponent textArea;
  private final DocumentMetadata documentMetadata;

  public SaveAsTextFileAction(
      Component parent, JTextComponent textArea, DocumentMetadata documentMetadata) {
    Objects.requireNonNull(parent);
    Objects.requireNonNull(textArea);
    Objects.requireNonNull(documentMetadata);

    this.parent = parent;
    this.textArea = textArea;
    this.documentMetadata = documentMetadata;
  }

  @Override
  public void performAction() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.addChoosableFileFilter(FileFilters.getFileFilterForText());
    if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
      try {
        String text = textArea.getText();
        File selectedFile = fileChooser.getSelectedFile();

        if (selectedFile.exists()) {
          int userChoice =
              JOptionPane.showConfirmDialog(
                  parent,
                  StringResource.getString(OVERWRITE_FILE),
                  StringResource.getString(WARNING),
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.WARNING_MESSAGE);

          if (userChoice != JOptionPane.YES_OPTION) {
            return;
          }
        }

        FileHandler.writeTextToFile(text, selectedFile.getAbsolutePath());
        documentMetadata.setTextIsAltered(false);
        documentMetadata.setDocumentPath(selectedFile.getAbsolutePath());
        documentMetadata.setDocumentName(selectedFile.getName());
      } catch (IOException exception) {
        logger.error("Unable to write text file to disk.", exception);
      }
    }
  }
}
