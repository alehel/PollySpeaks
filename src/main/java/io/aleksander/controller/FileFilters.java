package io.aleksander.controller;

import com.amazonaws.services.polly.model.OutputFormat;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileFilters {
  public static FileFilter getFileFilterFor(OutputFormat type) {
    String fileExtension;
    switch (type) {
      case Mp3:
        return new FileNameExtensionFilter("MP3 (*.mp3)", "mp3");
      case Pcm:
        return new FileNameExtensionFilter("PCM (*.pcm)", "pcm");
      default:
        return new FileNameExtensionFilter("Ogg Vorbis (*.ogg)", "ogg");
    }
  }

  public static FileFilter getFileFilterForText() {
    return new FileNameExtensionFilter("Text Documents (*.txt)", "txt");
  }
}
