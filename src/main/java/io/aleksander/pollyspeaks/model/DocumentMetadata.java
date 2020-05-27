package io.aleksander.pollyspeaks.model;

import static io.aleksander.pollyspeaks.utils.StringResource.UNTITLED;
import static io.aleksander.pollyspeaks.utils.StringResource.getString;

import java.util.Objects;

public class DocumentMetadata extends AbstractModel {
  private boolean textIsAltered;
  private String documentPath;
  private String documentName;
  private String documentText;
  private boolean ssmlMarkup = false;

  public DocumentMetadata() {
    textIsAltered = false;
    documentPath = null;
    documentName = null;
    documentText = "";
  }

  public void clear() {
    setTextIsAltered(false);
    setDocumentName(null);
    setDocumentPath(null);
    setDocumentText("");
  }

  public boolean isSsmlMarkup() {
    return ssmlMarkup;
  }

  public void setSsmlMarkup(boolean ssmlMarkup) {
    boolean oldValue = this.ssmlMarkup;
    this.ssmlMarkup = ssmlMarkup;
    firePropertyChange("ssmlMarkup", oldValue, ssmlMarkup);
  }

  public String getDocumentText() {
    return documentText;
  }

  public void setDocumentText(String documentText) {
    String oldValue = this.documentText;
    this.documentText = documentText;
    firePropertyChange("documentText", oldValue, documentText);
  }

  public String getDocumentName() {
    return Objects.requireNonNullElse(documentName, getString(UNTITLED));
  }

  public void setDocumentName(String documentName) {
    String oldValue = this.documentName;
    this.documentName = documentName;
    firePropertyChange("documentName", oldValue, documentName);
  }

  public boolean isTextIsAltered() {
    return textIsAltered;
  }

  public void setTextIsAltered(boolean textIsAltered) {
    boolean oldValue = this.textIsAltered;
    this.textIsAltered = textIsAltered;
    firePropertyChange("textIsAltered", oldValue, textIsAltered);
  }

  public String getDocumentPath() {
    return documentPath;
  }

  public void setDocumentPath(String documentPath) {
    String oldValue = this.documentPath;
    this.documentPath = documentPath;
    firePropertyChange("documentPath", oldValue, documentPath);
  }
}
