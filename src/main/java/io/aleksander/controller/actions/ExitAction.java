package io.aleksander.controller.actions;

import io.aleksander.model.DocumentMetadata;
import io.aleksander.utils.StringResource;

import javax.swing.JOptionPane;
import java.awt.Component;

import static io.aleksander.utils.StringResource.FILE_NOT_SAVED;
import static io.aleksander.utils.StringResource.WARNING;

public class ExitAction {

  private final Component parent;
  private final DocumentMetadata documentMetadata;

  public ExitAction(Component parent, DocumentMetadata documentMetadata) {
    this.parent = parent;
    this.documentMetadata = documentMetadata;
  }

  public void performAction() {
    if (documentMetadata.isTextIsAltered()) {
      int userChoice =
          JOptionPane.showConfirmDialog(
              parent,
              StringResource.getString(FILE_NOT_SAVED),
              StringResource.getString(WARNING),
              JOptionPane.YES_NO_OPTION,
              JOptionPane.WARNING_MESSAGE);

      if (userChoice == JOptionPane.OK_OPTION) {
        System.exit(0);
      }
    } else {
      System.exit(0);
    }
  }
}
