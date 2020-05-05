package io.aleksander.controller.action;

import io.aleksander.utils.FileHandler;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SaveTextFileAction implements ActionListener {

  private final Component parent;
  private final JTextArea textArea;

  public SaveTextFileAction(Component parent, JTextArea textArea) {
    this.parent = parent;
    this.textArea = textArea;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JFileChooser fileChooser = new JFileChooser();
    if(fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
      try {
        String text = textArea.getText();
        FileHandler.writeTextToFile(text, fileChooser.getSelectedFile().getAbsolutePath());
      } catch (IOException exception) {
        System.out.println(exception);
      }
    }
  }
}
