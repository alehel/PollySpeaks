package io.aleksander.controller.action;

import io.aleksander.gui.MainFrame;
import io.aleksander.model.DocumentMetadata;
import io.aleksander.utils.StringResource;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static io.aleksander.utils.StringResource.OVERWRITE_FILE;
import static io.aleksander.utils.StringResource.WARNING;

public class NewFileAction implements ActionListener {
  private final DocumentMetadata documentMetadata;
  private final MainFrame view;
  private final JTextArea textArea;

  public NewFileAction(MainFrame view, JTextArea textArea, DocumentMetadata documentMetadata) {
    this.view = view;
    this.textArea = textArea;
    this.documentMetadata = documentMetadata;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (documentMetadata.isTextIsAltered()) {
      int userChoice =
          JOptionPane.showConfirmDialog(
              view,
              StringResource.getString(OVERWRITE_FILE),
              StringResource.getString(WARNING),
              JOptionPane.YES_NO_OPTION,
              JOptionPane.WARNING_MESSAGE);

      if (userChoice != JOptionPane.NO_OPTION) {
        return;
      }
    }

    textArea.setText("");
    documentMetadata.setDocumentName(null);
    documentMetadata.setDocumentPath(null);
  }
}