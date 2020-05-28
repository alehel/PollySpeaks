package io.aleksander.pollyspeaks.controller.actions;

import static io.aleksander.pollyspeaks.utils.StringResource.CANCEL;
import static io.aleksander.pollyspeaks.utils.StringResource.CLOSE_UNSAVED_FILE;
import static io.aleksander.pollyspeaks.utils.StringResource.DONT_SAVE;
import static io.aleksander.pollyspeaks.utils.StringResource.SAVE;
import static io.aleksander.pollyspeaks.utils.StringResource.UNTITLED;
import static io.aleksander.pollyspeaks.utils.StringResource.WARNING;
import static io.aleksander.pollyspeaks.utils.StringResource.getString;

import io.aleksander.pollyspeaks.model.DocumentMetadata;
import io.aleksander.pollyspeaks.utils.StringResource;
import java.awt.Component;
import java.util.Objects;
import javax.swing.JOptionPane;

public class WindowActions {

  public static void exitApplication(Component frame, DocumentMetadata documentMetadata) {
    Objects.requireNonNull(frame);
    Objects.requireNonNull(documentMetadata);

    if (documentMetadata.isTextIsAltered()) {
      // get the file name
      String documentPath =
          documentMetadata.getDocumentPath() != null
              ? documentMetadata.getDocumentPath()
              : getString(UNTITLED);
      String userMessage = getString(CLOSE_UNSAVED_FILE) + " " + documentPath + "?";

      Object[] options = {getString(SAVE), getString(DONT_SAVE), getString(CANCEL)};

      int userChoice =
          JOptionPane.showOptionDialog(
              frame,
              userMessage,
              StringResource.getString(WARNING),
              JOptionPane.YES_NO_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              options,
              options[2]);

      if (userChoice == JOptionPane.OK_OPTION) {
        FileActions.saveTextFile(frame, documentMetadata);
        if (!documentMetadata.isTextIsAltered()) {
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
