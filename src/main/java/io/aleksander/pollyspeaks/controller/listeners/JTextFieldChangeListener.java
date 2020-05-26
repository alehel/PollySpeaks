package io.aleksander.pollyspeaks.controller.listeners;

import io.aleksander.pollyspeaks.model.DocumentMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class JTextFieldChangeListener implements DocumentListener {
  private static final Logger logger = LogManager.getLogger(JTextFieldChangeListener.class);
  private final DocumentMetadata documentMetadata;

  public JTextFieldChangeListener(DocumentMetadata documentMetadata) {
    this.documentMetadata = documentMetadata;
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    int length = e.getLength();
    int offset = e.getOffset();
    String documentText = documentMetadata.getDocumentText();
    try {
      String change = e.getDocument().getText(offset, length);
      String newDocumentText =
          documentText.substring(0, offset) + change + documentText.substring(offset);
      documentMetadata.setDocumentText(newDocumentText);
      documentMetadata.setTextIsAltered(true);
    } catch (BadLocationException | StringIndexOutOfBoundsException exception) {
      logger.error(
          "Failed to correctly update content of " + DocumentMetadata.class.getName(), exception);
    }
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    int length = e.getLength();
    int offset = e.getOffset();
    String documentText = documentMetadata.getDocumentText();
    try {
      String newDocumenttext =
          documentText.substring(0, offset) + documentText.substring(offset + length);
      documentMetadata.setDocumentText(newDocumenttext);
      documentMetadata.setTextIsAltered(true);
    } catch (StringIndexOutOfBoundsException exception) {
      logger.error(
          "Failed to correctly update content of " + DocumentMetadata.class.getName(), exception);
    }
  }

  @Override
  public void changedUpdate(DocumentEvent e) {
    logger.debug("changedUpdate(DocumentEvent e) was called.");
  }
}
