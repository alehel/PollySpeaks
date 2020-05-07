package io.aleksander.controller;

import io.aleksander.controller.actions.ExitAction;
import io.aleksander.controller.actions.NewFileAction;
import io.aleksander.controller.actions.OpenTextFileAction;
import io.aleksander.controller.actions.SaveAsTextFileAction;
import io.aleksander.controller.actions.SaveTextFileAction;
import io.aleksander.controller.actions.WordWrapAction;
import io.aleksander.gui.ApplicationMenuBar;
import io.aleksander.gui.MainFrame;
import io.aleksander.model.DocumentMetadata;

import javax.swing.JTextArea;

public class ApplicationMenuBarController {
  private final MainFrame parent;
  private final JTextArea textArea;
  private final DocumentMetadata documentMetadata;

  public ApplicationMenuBarController(
      MainFrame parent, JTextArea textArea, DocumentMetadata documentMetadata) {
    this.parent = parent;
    this.textArea = textArea;
    this.documentMetadata = documentMetadata;
    ApplicationMenuBar view = new ApplicationMenuBar();
    attachActionListeners(view);
    parent.setJMenuBar(view);
  }

  private void attachActionListeners(ApplicationMenuBar view) {
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
