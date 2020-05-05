package io.aleksander.controller.action;

import io.aleksander.utils.FileHandler;
import io.aleksander.utils.StringResource;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static io.aleksander.utils.StringResource.OVERWRITE_FILE;
import static io.aleksander.utils.StringResource.WARNING;

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
        File selectedFile = fileChooser.getSelectedFile();

        if(selectedFile.exists()) {
          int userChoice = JOptionPane.showConfirmDialog(
              parent,
              StringResource.getString(OVERWRITE_FILE),
              StringResource.getString(WARNING),
              JOptionPane.YES_NO_OPTION,
              JOptionPane.WARNING_MESSAGE);

          if(userChoice != JOptionPane.YES_OPTION) {
            return;
          }
        }

        FileHandler.writeTextToFile(text, selectedFile.getAbsolutePath());
      } catch (IOException exception) {
        System.out.println(exception);
      }
    }
  }
}
