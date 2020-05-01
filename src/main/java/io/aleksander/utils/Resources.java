package io.aleksander.utils;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Resources {
  public static final String APPLICATION_TITLE = "applicationTitle";
  public static final String LANGUAGE = "language";
  public static final String VOICE = "voice";
  public static final String SPEAK = "speak";
  public static final String VOICE_SETTINGS = "voiceSettings";
  public static final String EXIT = "exit";
  public static final String FILE = "file";

  public static String getString(String propertyKey) {
    Objects.requireNonNull(propertyKey);
    Locale systemLocale = Locale.getDefault();
    return ResourceBundle.getBundle("strings", systemLocale).getString(propertyKey);
  }
}
