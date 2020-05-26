package io.aleksander.pollyspeaks.controller.actions;

import java.util.Objects;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTextArea;

public class WordWrapAction implements Action {

  private final JTextArea textArea;
  private final JCheckBoxMenuItem item;

  public WordWrapAction(JCheckBoxMenuItem item, JTextArea textArea) {
    Objects.requireNonNull(item);
    Objects.requireNonNull(textArea);

    this.textArea = textArea;
    this.item = item;
  }

  @Override
  public void performAction() {
    boolean shouldWordWrap = item.getState();
    textArea.setLineWrap(shouldWordWrap);
    textArea.setWrapStyleWord(shouldWordWrap);
  }
}
