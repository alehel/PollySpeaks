package io.aleksander.controller.action;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordWrapAction implements ActionListener {

  private final JTextArea textArea;

  public WordWrapAction(JTextArea textArea) {
    this.textArea = textArea;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();
    boolean shouldWordWrap = item.getState();
    setWordWrap(shouldWordWrap);
  }

  public void setWordWrap(boolean shouldWrap) {
    textArea.setLineWrap(shouldWrap);
    textArea.setWrapStyleWord(shouldWrap);
  }
}
