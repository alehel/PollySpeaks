package io.aleksander.pollyspeaks.controller.actions;

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
import java.nio.file.NoSuchFileException;

import static io.aleksander.pollyspeaks.utils.StringResource.ERROR;
import static io.aleksander.pollyspeaks.utils.StringResource.ERROR_READING_FILE;
import static io.aleksander.pollyspeaks.utils.StringResource.NO_SUCH_FILE;

public class OpenTextFileAction implements Action {
  private final Component parent;
  private final JTextComponent textArea;
  private final DocumentMetadata documentMetadata;

  public OpenTextFileAction(
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
    if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
      try {
        File selectedFile = fileChooser.getSelectedFile();
        String textFromFile = FileHandler.readTextFromFile(selectedFile.getAbsolutePath());
        textArea.setText(textFromFile);
        documentMetadata.setTextIsAltered(false);
        documentMetadata.setDocumentName(selectedFile.getName());
        documentMetadata.setDocumentPath(selectedFile.getAbsolutePath());
      } catch (NoSuchFileException exception) {
        JOptionPane.showMessageDialog(
            parent,
            StringResource.getString(NO_SUCH_FILE),
            StringResource.getString(ERROR),
            JOptionPane.ERROR_MESSAGE);
      } catch (IOException exception) {
        JOptionPane.showMessageDialog(
            parent,
            StringResource.getString(ERROR_READING_FILE),
            StringResource.getString(ERROR),
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
