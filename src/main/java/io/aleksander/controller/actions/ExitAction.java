package io.aleksander.controller.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitAction implements ActionListener {

  private final Component parent;

  public ExitAction(Component parent) {
    this.parent = parent;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.exit(0);
  }
}
