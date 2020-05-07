package io.aleksander.utils;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class StringResource {
  public static final String APPLICATION_TITLE = "applicationTitle";
  public static final String LANGUAGE = "language";
  public static final String VOICE = "voice";
  public static final String SPEAK = "speak";
  public static final String VOICE_SETTINGS = "voiceSettings";
  public static final String FILE = "file";
  public static final String NEW = "new";
  public static final String OPEN = "open";
  public static final String SAVE = "save";
  public static final String SAVE_AS = "saveAs";
  public static final String VIEW = "view";
  public static final String VIEW_WORD_WRAP = "viewWordWrap";
  public static final String EXIT = "exit";
  public static final String ERROR = "error";
  public static final String SOUND_PLAYBACK_ERROR = "soundPlaybackError";
  public static final String WARNING = "warning";
  public static final String OVERWRITE_FILE = "overwriteFile";
  public static final String NO_SUCH_FILE = "noSuchFile";
  public static final String ERROR_READING_FILE = "errorReadingFile";

  public static String getString(String propertyKey) {
    Objects.requireNonNull(propertyKey);
    Locale systemLocale = Locale.getDefault();
    return ResourceBundle.getBundle("strings", systemLocale).getString(propertyKey);
  }
}
