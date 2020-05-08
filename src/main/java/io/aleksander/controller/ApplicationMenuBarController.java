package io.aleksander.controller;

import io.aleksander.controller.actions.ExitAction;
import io.aleksander.controller.actions.NewFileAction;
import io.aleksander.controller.actions.OpenTextFileAction;
import io.aleksander.controller.actions.SaveAsTextFileAction;
import io.aleksander.controller.actions.SaveTextFileAction;
import io.aleksander.controller.actions.WordWrapAction;
import io.aleksander.gui.ApplicationMenuBar;
import io.aleksander.gui.Component;
import io.aleksander.model.DocumentMetadata;

import javax.swing.JTextArea;

public class ApplicationMenuBarController {
  private final Component parent;
  private final ApplicationMenuBar view;

  public ApplicationMenuBarController(
      Component parent, JTextArea textArea, DocumentMetadata documentMetadata) {
    this.parent = parent;
    view = new ApplicationMenuBar();
    attachActionListeners(textArea, documentMetadata);
    parent.setJMenuBar(view);
  }

  private void attachActionListeners(JTextArea textArea, DocumentMetadata documentMetadata) {
    view.getNewItem().addActionListener(new NewFileAction(parent, textArea, documentMetadata));
    view.getOpenItem().addActionListener(new OpenTextFileAction(parent, textArea));
    view.getSaveItem()
        .addActionListener(new SaveTextFileAction(parent, textArea, documentMetadata));
    view.getSaveAsItem()
        .addActionListener(new SaveAsTextFileAction(parent, textArea, documentMetadata));
    view.getWordWrapItem().addActionListener(new WordWrapAction(textArea));
    view.getWordWrapItem().doClick();
    view.getExitItem().addActionListener(new ExitAction(view));
    parent.setJMenuBar(new ApplicationMenuBar());
  }
}
