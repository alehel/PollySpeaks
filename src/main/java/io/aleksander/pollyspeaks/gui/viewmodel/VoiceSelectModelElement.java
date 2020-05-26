package io.aleksander.pollyspeaks.gui.viewmodel;

public class VoiceSelectModelElement {
  private final String displayString;
  private final String id;

  public VoiceSelectModelElement(String displayString, String id) {
    this.displayString = displayString;
    this.id = id;
  }

  public String getDisplayString() {
    return displayString;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return displayString;
  }
}
