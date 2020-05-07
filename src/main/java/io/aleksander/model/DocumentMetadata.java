package io.aleksander.model;

public class DocumentMetadata extends AbstractModel {
  private boolean textIsAltered;
  private String documentPath;
  private String documentName;

  public DocumentMetadata() {
    textIsAltered = false;
    documentName = "Unknown";
  }

  public String getDocumentName() {
    if(documentName == null) {
      return "Unknown";
    } else {
      return documentName;
    }
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
