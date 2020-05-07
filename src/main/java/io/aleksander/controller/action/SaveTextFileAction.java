package io.aleksander.controller.action;

import io.aleksander.gui.MainFrame;
import io.aleksander.model.DocumentMetadata;
import io.aleksander.utils.FileHandler;

import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SaveTextFileAction implements ActionListener {
  private final JTextArea textArea;
  private final MainFrame view;
  private final DocumentMetadata documentMetadata;

  public SaveTextFileAction(MainFrame view, JTextArea textArea, DocumentMetadata documentMetadata) {
    this.view = view;
    this.textArea = textArea;
    this.documentMetadata = documentMetadata;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // if the file hasn't been saved before, show the "Save As..." dialog.
    if (documentMetadata.getDocumentPath() == null) {
      SaveAsTextFileAction saveAsTextFileAction =
          new SaveAsTextFileAction(view, textArea, documentMetadata);
      saveAsTextFileAction.actionPerformed(e);
    } else {
      String text = textArea.getText();
      File file = new File(documentMetadata.getDocumentPath());
      try {
        FileHandler.writeTextToFile(text, file.getAbsolutePath());
        documentMetadata.setTextIsAltered(false);
      } catch (IOException exception) {
        System.out.println(exception);
      }
    }
  }
}
