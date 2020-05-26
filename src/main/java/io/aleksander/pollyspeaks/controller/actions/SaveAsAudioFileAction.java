package io.aleksander.pollyspeaks.controller.actions;

import com.amazonaws.services.polly.model.OutputFormat;
import io.aleksander.pollyspeaks.controller.FileFilters;
import io.aleksander.pollyspeaks.model.TextToSpeechEngine;
import io.aleksander.pollyspeaks.utils.FileHandler;
import io.aleksander.pollyspeaks.utils.StringResource;

import java.util.Objects;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.aleksander.pollyspeaks.utils.StringResource.OVERWRITE_FILE;
import static io.aleksander.pollyspeaks.utils.StringResource.WARNING;

public class SaveAsAudioFileAction implements Action {
  private static final Logger logger = LogManager.getLogger(SaveAsAudioFileAction.class);

  private final Component parent;
  private final String text;
  private final TextToSpeechEngine textToSpeechEngine;
  private final OutputFormat outputFormat;

  public SaveAsAudioFileAction(
      Component parent,
      String text,
      TextToSpeechEngine textToSpeechEngine,
      OutputFormat outputFormat) {
    Objects.requireNonNull(parent);
    Objects.requireNonNull(text);
    Objects.requireNonNull(textToSpeechEngine);
    Objects.requireNonNull(outputFormat);

    this.parent = parent;
    this.text = text;
    this.textToSpeechEngine = textToSpeechEngine;
    this.outputFormat = outputFormat;
  }

  @Override
  public void performAction() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.addChoosableFileFilter(FileFilters.getFileFilterFor(outputFormat));
    if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
      try {
        File selectedFile = fileChooser.getSelectedFile();

        if (selectedFile.exists()) {
          int userChoice =
              JOptionPane.showConfirmDialog(
                  parent,
                  StringResource.getString(OVERWRITE_FILE),
                  StringResource.getString(WARNING),
                  JOptionPane.YES_NO_OPTION,
                  JOptionPane.WARNING_MESSAGE);

          if (userChoice != JOptionPane.YES_OPTION) {
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
}
