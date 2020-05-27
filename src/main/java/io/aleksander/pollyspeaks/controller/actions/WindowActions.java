package io.aleksander.pollyspeaks.controller.actions;

import io.aleksander.pollyspeaks.model.DocumentMetadata;
import io.aleksander.pollyspeaks.utils.StringResource;

import java.util.Objects;
import javax.swing.JOptionPane;
import java.awt.Component;

import static io.aleksander.pollyspeaks.utils.StringResource.CANCEL;
import static io.aleksander.pollyspeaks.utils.StringResource.DONT_SAVE;
import static io.aleksander.pollyspeaks.utils.StringResource.FILE_NOT_SAVED;
import static io.aleksander.pollyspeaks.utils.StringResource.SAVE;
import static io.aleksander.pollyspeaks.utils.StringResource.WARNING;
import static io.aleksander.pollyspeaks.utils.StringResource.getString;

public class WindowActions {

  public static void exitApplication(Component frame, DocumentMetadata documentMetadata) {
    Objects.requireNonNull(frame);
    Objects.requireNonNull(documentMetadata);

    if (documentMetadata.isTextIsAltered()) {
      Object[] options = {getString(SAVE), getString(DONT_SAVE), getString(CANCEL)};

      int userChoice =
          JOptionPane.showOptionDialog(
              frame,
              StringResource.getString(FILE_NOT_SAVED),
              StringResource.getString(WARNING),
              JOptionPane.YES_NO_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              options,
              options[2]);

      if (userChoice == JOptionPane.OK_OPTION) {
        FileActions.saveTextFile(frame, documentMetadata);
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
