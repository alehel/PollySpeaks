package io.aleksander;

import io.aleksander.controller.Controller;
import io.aleksander.gui.MainFrame;

import javax.swing.SwingUtilities;

public class App {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(Controller::new);
  }
}
