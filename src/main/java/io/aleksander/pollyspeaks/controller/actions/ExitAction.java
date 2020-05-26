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
      Object[] options = {"Save", "Don't Save", "Cancel"};

      int userChoice =
          JOptionPane.showOptionDialog(
              parent,
              StringResource.getString(FILE_NOT_SAVED),
              StringResource.getString(WARNING),
              JOptionPane.YES_NO_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              options,
              options[2]);

      if (userChoice == JOptionPane.OK_OPTION) {
        new SaveTextFileAction(parent, documentMetadata).performAction();
        if(!documentMetadata.isTextIsAltered()) {
          System.exit(0);
        }
      } else if (userChoice == JOptionPane.NO_OPTION) {
        System.exit(0);
      }
    } else {
      System.exit(0);
    }
  }
}
