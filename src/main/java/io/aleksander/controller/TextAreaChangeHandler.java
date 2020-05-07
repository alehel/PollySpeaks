package io.aleksander.controller;

import io.aleksander.model.DocumentMetadata;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Responsible for keeping the Document objects values up to date
 * with changes to the JTextArea.
 */
public class TextAreaChangeHandler implements DocumentListener {
  private final DocumentMetadata documentMetadata;

  public TextAreaChangeHandler(DocumentMetadata documentMetadata) {
    this.documentMetadata = documentMetadata;
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    documentMetadata.setTextIsAltered(true);
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    documentMetadata.setTextIsAltered(true);
  }

  @Override
  public void changedUpdate(DocumentEvent e) {
    documentMetadata.setTextIsAltered(true);
  }
}
