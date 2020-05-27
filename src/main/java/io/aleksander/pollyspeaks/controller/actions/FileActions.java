package io.aleksander.pollyspeaks.controller.actions;

import static io.aleksander.pollyspeaks.utils.StringResource.CANCEL;
import static io.aleksander.pollyspeaks.utils.StringResource.CLOSE_UNSAVED_FILE;
import static io.aleksander.pollyspeaks.utils.StringResource.DONT_SAVE;
import static io.aleksander.pollyspeaks.utils.StringResource.ERROR;
import static io.aleksander.pollyspeaks.utils.StringResource.ERROR_READING_FILE;
import static io.aleksander.pollyspeaks.utils.StringResource.NO_SUCH_FILE;
import static io.aleksander.pollyspeaks.utils.StringResource.OVERWRITE_FILE;
import static io.aleksander.pollyspeaks.utils.StringResource.SAVE;
import static io.aleksander.pollyspeaks.utils.StringResource.UNTITLED;
import static io.aleksander.pollyspeaks.utils.StringResource.WARNING;
import static io.aleksander.pollyspeaks.utils.StringResource.getString;

import com.amazonaws.services.polly.model.OutputFormat;
import io.aleksander.pollyspeaks.model.DocumentMetadata;
import io.aleksander.pollyspeaks.model.TextToSpeechEngine;
import io.aleksander.pollyspeaks.utils.FileHandler;
import io.aleksander.pollyspeaks.utils.StringResource;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.Objects;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileActions {
  private static final Logger logger = LogManager.getLogger(FileActions.class);

  public static void newFile(
      Component frame, JTextComponent textArea, DocumentMetadata documentMetadata) {
    Objects.requireNonNull(frame);
    Objects.requireNonNull(textArea);
    Objects.requireNonNull(documentMetadata);

    // check if file has unsaved content
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
              getString(WARNING),
              JOptionPane.YES_NO_CANCEL_OPTION,
              JOptionPane.QUESTION_MESSAGE,
              null,
              options,
              options[2]);

      if (userChoice == JOptionPane.YES_OPTION) {
        // save the file
        boolean saveSuccessful = saveTextFile(frame, documentMetadata);
        if (saveSuccessful) {
          textArea.setText("");
          documentMetadata.clear();
        }
      } else if (userChoice == JOptionPane.NO_OPTION) {
        // discard changes
        textArea.setText("");
        documentMetadata.clear();
      }
    } else {
      // cancel
      textArea.setText("");
      documentMetadata.clear();
    }
  }

  public static boolean saveTextFile(Component frame, DocumentMetadata documentMetadata) {
    Objects.requireNonNull(frame);
    Objects.requireNonNull(documentMetadata);

    // if the file hasn't been saved before, show the "Save As..." dialog.
    if (documentMetadata.getDocumentPath() == null) {
      return saveAsTextFile(frame, documentMetadata);
    } else {
      String text = documentMetadata.getDocumentText();
      File file = new File(documentMetadata.getDocumentPath());
      try {
        FileHandler.writeTextToFile(text, file.getAbsolutePath());
        documentMetadata.setTextIsAltered(false);
        return true;
      } catch (IOException exception) {
        logger.error("Unable to write text file to disk.", exception);
        return false;
      }
    }
  }

  public static boolean saveAsTextFile(Component frame, DocumentMetadata documentMetadata) {
    Objects.requireNonNull(frame);
    Objects.requireNonNull(documentMetadata);

    JFileChooser fileChooser = new JFileChooser();

    // only allow text files.
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.addChoosableFileFilter(getFileFilterForText());

    if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
      // user specified a file to save to
      try {
        String text = documentMetadata.getDocumentText();
        File selectedFile = fileChooser.getSelectedFile();

        if (selectedFile.exists()) {
          // file already exists, warn user
          int userChoice =
              JOptionPane.showConfirmDialog(
                  frame,
                  StringResource.getString(OVERWRITE_FILE),
                  StringResource.getString(WARNING),
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.WARNING_MESSAGE);

          if (userChoice != JOptionPane.YES_OPTION) {
            // user doesn't wish to replace existing file
            return false;
          }
        }

        // save the text file
        FileHandler.writeTextToFile(text, selectedFile.getAbsolutePath());
        documentMetadata.setTextIsAltered(false);
        documentMetadata.setDocumentPath(selectedFile.getAbsolutePath());
        documentMetadata.setDocumentName(selectedFile.getName());
        return true;
      } catch (IOException exception) {
        logger.error("Unable to write text file to disk.", exception);
        return false;
      }
    } else {
      // user closed file chooser without selecting a file to save to
      return false;
    }
  }

  public static void openTextFile(
      Component frame, JTextComponent textArea, DocumentMetadata documentMetadata) {
    Objects.requireNonNull(frame);
    Objects.requireNonNull(textArea);
    Objects.requireNonNull(documentMetadata);

    JFileChooser fileChooser = new JFileChooser();

    // only allow text files.
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.addChoosableFileFilter(getFileFilterForText());

    if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
      // user specified a file to open
      try {
        File selectedFile = fileChooser.getSelectedFile();
        String textFromFile = FileHandler.readTextFromFile(selectedFile.getAbsolutePath());
        textArea.setText(textFromFile);
        documentMetadata.setTextIsAltered(false);
        documentMetadata.setDocumentName(selectedFile.getName());
        documentMetadata.setDocumentPath(selectedFile.getAbsolutePath());
      } catch (NoSuchFileException exception) {
        JOptionPane.showMessageDialog(
            frame,
            StringResource.getString(NO_SUCH_FILE),
            StringResource.getString(ERROR),
            JOptionPane.ERROR_MESSAGE);
      } catch (IOException exception) {
        JOptionPane.showMessageDialog(
            frame,
            StringResource.getString(ERROR_READING_FILE),
            StringResource.getString(ERROR),
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  public static void saveAsAudioFile(
      Component frame,
      String text,
      TextToSpeechEngine textToSpeechEngine,
      OutputFormat outputFormat) {
    Objects.requireNonNull(frame);
    Objects.requireNonNull(text);
    Objects.requireNonNull(textToSpeechEngine);
    Objects.requireNonNull(outputFormat);

    JFileChooser fileChooser = new JFileChooser();

    // only allow supported audio files
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.addChoosableFileFilter(getFileFilterFor(outputFormat));

    if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
      // user specified a file to save to
      try {
        File selectedFile = fileChooser.getSelectedFile();

        if (selectedFile.exists()) {
          // file already exists, warn user
          int userChoice =
              JOptionPane.showConfirmDialog(
                  frame,
                  StringResource.getString(OVERWRITE_FILE),
                  StringResource.getString(WARNING),
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.WARNING_MESSAGE);

          if (userChoice != JOptionPane.YES_OPTION) {
            // user doesn't wish to replace existing file
            return;
          }
        }

        InputStream audioStream = textToSpeechEngine.convertTextToSpeech(text, outputFormat);
        FileHandler.writeAudioToFile(audioStream, selectedFile.getAbsolutePath());
      } catch (IOException exception) {
        logger.error("Unable to write audio file to disk.", exception);
      }
    }
  }

  public static FileFilter getFileFilterFor(OutputFormat type) {
    return switch (type) {
      case Mp3 -> new FileNameExtensionFilter("MP3 (*.mp3)", "mp3");
      case Pcm -> new FileNameExtensionFilter("PCM (*.pcm)", "pcm");
      default -> new FileNameExtensionFilter("Ogg Vorbis (*.ogg)", "ogg");
    };
  }

  public static FileFilter getFileFilterForText() {
    return new FileNameExtensionFilter("Text Documents (*.txt)", "txt");
  }
}
