package io.aleksander.gui.viewmodel;

public class VoiceSelectModel {
  private final String name;
  private final String id;

  public VoiceSelectModel(String name, String id) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return name;
  }
}
