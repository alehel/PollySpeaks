package io.aleksander.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileHandler {

  public static String readTextFromFile(String uri) throws IOException {
    File file = new File(uri);
    return Files.readString(file.toPath());
  }

  public static void writeTextToFile(String text, String uri) throws IOException {
    File file = new File(uri);
    byte[] textAsBytes = text.getBytes();
    Files.write(file.toPath(), textAsBytes);
  }
}
