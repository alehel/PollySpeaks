package io.aleksander.controller.action;

import io.aleksander.utils.FileHandler;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OpenTextFileAction implements ActionListener {
  private final Component parent;
  private final JTextArea textArea;

  public OpenTextFileAction(Component parent, JTextArea textArea) {
    this.parent = parent;
    this.textArea = textArea;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JFileChooser fileChooser = new JFileChooser();
    if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
      try {
        String textFromFile =
            FileHandler.readTextFromFile(fileChooser.getSelectedFile().getAbsolutePath());
        textArea.setText(textFromFile);
      } catch (IOException exception) {
        System.out.println(exception);
      }
    }
  }
}
