package io.aleksander.pollyspeaks.controller;

import io.aleksander.pollyspeaks.controller.actions.ExitAction;
import io.aleksander.pollyspeaks.controller.actions.FileActions;
import io.aleksander.pollyspeaks.controller.actions.WordWrapAction;
import io.aleksander.pollyspeaks.gui.ApplicationMenuBar;
import io.aleksander.pollyspeaks.gui.MainFrame;
import io.aleksander.pollyspeaks.model.DocumentMetadata;
import io.aleksander.pollyspeaks.model.TextToSpeechEngine;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTextArea;

public class ApplicationMenuBarController {
  private final MainFrame frame;
  private final ApplicationMenuBar view;
  private final TextToSpeechEngine textToSpeechEngine;

  public ApplicationMenuBarController(
      MainFrame frame, JTextArea textArea, DocumentMetadata documentMetadata, TextToSpeechEngine textToSpeechEngine) {
    this.textToSpeechEngine = textToSpeechEngine;
    this.frame = frame;
    view = new ApplicationMenuBar();
    setActions(textArea, documentMetadata);
    frame.setJMenuBar(view);
  }

  private void setActions(JTextArea textArea, DocumentMetadata documentMetadata) {
    view.getNewItem()
        .addActionListener(e -> FileActions.newFile(frame, textArea, documentMetadata));

    view.getOpenItem()
        .addActionListener(e -> FileActions.openTextFile(frame, textArea, documentMetadata));

    view.getSaveItem()
        .addActionListener(e -> FileActions.saveTextFile(frame, documentMetadata));

    view.getSaveAsItem()
        .addActionListener(e -> FileActions.saveAsTextFile(frame, documentMetadata));

    view.getExportToAudioFile()
        .addActionListener(
            e -> new ExportAudioController(frame, textToSpeechEngine, documentMetadata.getDocumentText()));

    view.getWordWrapItem()
        .addActionListener(
            e -> (new WordWrapAction((JCheckBoxMenuItem) e.getSource(), textArea)).performAction());
    view.getWordWrapItem().doClick();

    view.getExitItem()
        .addActionListener(e -> (new ExitAction(frame, documentMetadata)).performAction());

    frame.setJMenuBar(new ApplicationMenuBar());
  }
}
