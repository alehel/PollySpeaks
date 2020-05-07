package io.aleksander;

import io.aleksander.controller.MainController;

import javax.swing.SwingUtilities;

public class App {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(MainController::new);
  }
}
