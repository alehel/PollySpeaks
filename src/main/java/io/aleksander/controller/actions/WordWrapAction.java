package io.aleksander.controller.actions;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTextArea;

public class WordWrapAction implements Action {

  private final JTextArea textArea;
  private final JCheckBoxMenuItem item;

  public WordWrapAction(JCheckBoxMenuItem item, JTextArea textArea) {
    this.textArea = textArea;
    this.item = item;
  }

  @Override
  public void performAction() {
    boolean shouldWordWrap = item.getState();
    setWordWrap(shouldWordWrap);
  }

  public void setWordWrap(boolean shouldWrap) {
    textArea.setLineWrap(shouldWrap);
    textArea.setWrapStyleWord(shouldWrap);
  }
}
