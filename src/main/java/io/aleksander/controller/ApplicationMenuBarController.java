package io.aleksander.controller;

import io.aleksander.controller.actions.ExitAction;
import io.aleksander.controller.actions.ExportToAudioAction;
import io.aleksander.controller.actions.NewFileAction;
import io.aleksander.controller.actions.OpenTextFileAction;
import io.aleksander.controller.actions.SaveAsTextFileAction;
import io.aleksander.controller.actions.SaveTextFileAction;
import io.aleksander.controller.actions.WordWrapAction;
import io.aleksander.gui.ApplicationMenuBar;
import io.aleksander.gui.MainFrame;
import io.aleksander.model.DocumentMetadata;
import io.aleksander.model.TextToSpeechEngine;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTextArea;

public class ApplicationMenuBarController {
  private final MainFrame parent;
  private final ApplicationMenuBar view;
  private final TextToSpeechEngine textToSpeechEngine;

  public ApplicationMenuBarController(
      MainFrame parent, JTextArea textArea, DocumentMetadata documentMetadata, TextToSpeechEngine textToSpeechEngine) {
    this.textToSpeechEngine = textToSpeechEngine;
    this.parent = parent;
    view = new ApplicationMenuBar();
    setActions(textArea, documentMetadata);
    parent.setJMenuBar(view);
  }

  private void setActions(JTextArea textArea, DocumentMetadata documentMetadata) {
    view.getNewItem()
        .addActionListener(
            e -> (new NewFileAction(parent, textArea, documentMetadata)).performAction());

    view.getOpenItem()
        .addActionListener(
            e -> (new OpenTextFileAction(parent, textArea, documentMetadata)).performAction());

    view.getSaveItem()
        .addActionListener(
            e -> (new SaveTextFileAction(parent, textArea, documentMetadata)).performAction());

    view.getSaveAsItem()
        .addActionListener(
            e -> (new SaveAsTextFileAction(parent, textArea, documentMetadata)).performAction());

    view.getExportToAudioFile()
        .addActionListener(
            e -> (new ExportToAudioAction(parent, textToSpeechEngine, textArea.getText())).performAction());

    view.getWordWrapItem()
        .addActionListener(
            e -> (new WordWrapAction((JCheckBoxMenuItem) e.getSource(), textArea)).performAction());
    view.getWordWrapItem().doClick();

    view.getExitItem()
        .addActionListener(e -> (new ExitAction(parent, documentMetadata)).performAction());

    parent.setJMenuBar(new ApplicationMenuBar());
  }
}
