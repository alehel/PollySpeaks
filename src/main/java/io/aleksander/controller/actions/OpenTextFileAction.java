package io.aleksander.controller.actions;

import io.aleksander.controller.FileFilters;
import io.aleksander.model.DocumentMetadata;
import io.aleksander.utils.FileHandler;
import io.aleksander.utils.StringResource;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import static io.aleksander.utils.StringResource.ERROR;
import static io.aleksander.utils.StringResource.ERROR_READING_FILE;
import static io.aleksander.utils.StringResource.NO_SUCH_FILE;

public class OpenTextFileAction implements Action {
  private final Component parent;
  private final JTextArea textArea;
  private final DocumentMetadata documentMetadata;

  public OpenTextFileAction(
      Component parent, JTextArea textArea, DocumentMetadata documentMetadata) {
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
