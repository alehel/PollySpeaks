package io.aleksander.pollyspeaks.controller;

import com.amazonaws.services.polly.model.OutputFormat;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileFilters {
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
