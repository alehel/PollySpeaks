package io.aleksander.pollyspeaks.controller.actions;

import io.aleksander.pollyspeaks.model.DocumentMetadata;
import io.aleksander.pollyspeaks.utils.StringResource;

import java.util.Objects;
import javax.swing.JOptionPane;
import java.awt.Component;

import static io.aleksander.pollyspeaks.utils.StringResource.FILE_NOT_SAVED;
import static io.aleksander.pollyspeaks.utils.StringResource.WARNING;

public class ExitAction implements Action {

  private final Component parent;
  private final DocumentMetadata documentMetadata;

  public ExitAction(Component parent, DocumentMetadata documentMetadata) {
    Objects.requireNonNull(parent);
    Objects.requireNonNull(documentMetadata);

    this.parent = parent;
    this.documentMetadata = documentMetadata;
  }

  @Override
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
