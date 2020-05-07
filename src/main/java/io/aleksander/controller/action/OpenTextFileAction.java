package io.aleksander.controller.action;

import io.aleksander.utils.FileHandler;
import io.aleksander.utils.StringResource;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import static io.aleksander.utils.StringResource.ERROR;
import static io.aleksander.utils.StringResource.ERROR_READING_FILE;
import static io.aleksander.utils.StringResource.NO_SUCH_FILE;
import static io.aleksander.utils.StringResource.OVERWRITE_FILE;
import static io.aleksander.utils.StringResource.WARNING;

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
      } catch (NoSuchFileException exception) {
        JOptionPane.showMessageDialog(
            parent,
            StringResource.getString(NO_SUCH_FILE),
            StringResource.getString(ERROR),
            JOptionPane.ERROR_MESSAGE);
      } catch (IOException exception) {
        JOptionPane.showMessageDialog(
            parent,
            StringResource.getString(ERROR_READING_FILE),
            StringResource.getString(ERROR),
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
