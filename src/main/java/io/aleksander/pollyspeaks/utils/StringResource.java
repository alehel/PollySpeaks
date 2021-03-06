package io.aleksander.pollyspeaks.utils;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class StringResource {
  public static final String APPLICATION_TITLE = "applicationTitle";
  public static final String LANGUAGE = "language";
  public static final String VOICE = "voice";
  public static final String SPEAK = "speak";
  public static final String FILE = "file";
  public static final String NEW = "new";
  public static final String OPEN = "open";
  public static final String SAVE = "save";
  public static final String SAVE_AS = "saveAs";
  public static final String FORMAT = "format";
  public static final String FORMAT_WORD_WRAP = "formatWordWrap";
  public static final String EXIT = "exit";
  public static final String ERROR = "error";
  public static final String SOUND_PLAYBACK_ERROR = "soundPlaybackError";
  public static final String SSML_PARSE_ERROR = "ssmlParseError";
  public static final String WARNING = "warning";
  public static final String OVERWRITE_FILE = "overwriteFile";
  public static final String NO_SUCH_FILE = "noSuchFile";
  public static final String ERROR_READING_FILE = "errorReadingFile";
  public static final String AWS_SDK_ERROR = "awsSdkError";
  public static final String FAILED_TO_START_ERROR = "failedToStartError";
  public static final String EXPORT_TO_AUDIO_FILE = "exportToAudioFile";
  public static final String OUTPUT_FORMAT = "outputFormat";
  public static final String SSML = "ssml";
  public static final String USE_SSML = "useSSML";
  public static final String EXPORT = "export";
  public static final String DONT_SAVE = "dontSave";
  public static final String CANCEL = "cancel";
  public static final String CLOSE_UNSAVED_FILE = "replaceUnsavedFile";
  public static final String UNTITLED = "untitled";
  public static final String FONT = "font";

  public static String getString(String propertyKey) {
    Objects.requireNonNull(propertyKey);
    Locale systemLocale = Locale.getDefault();
    return ResourceBundle.getBundle("strings", systemLocale).getString(propertyKey);
  }
}
