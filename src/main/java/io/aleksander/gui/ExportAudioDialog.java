package io.aleksander.gui;

import com.amazonaws.services.polly.model.OutputFormat;
import io.aleksander.utils.StringResource;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import static io.aleksander.utils.StringResource.EXPORT;
import static io.aleksander.utils.StringResource.EXPORT_TO_AUDIO_FILE;
import static io.aleksander.utils.StringResource.OUTPUT_FORMAT;

public class ExportAudioDialog extends JDialog {
  private final Insets labelInsets = new Insets(0, 0, 0, 5);
  private final Insets emptyInsets = new Insets(0, 0, 0, 0);
  private final Insets buttonInsets = new Insets(10, 0, 0, 12);

  private final Dimension comboBoxPreferredSize = new Dimension(150, 20);
  private JLabel outputFormat;
  private JComboBox<OutputFormat> outputFormatSelector;
  private JButton exportButton;

  public ExportAudioDialog() {
    // configure dialog
    setTitle(StringResource.getString(EXPORT_TO_AUDIO_FILE));
    setModal(true);
    setMinimumSize(new Dimension(300, 120));
    setLayout(new GridBagLayout());
    ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    createComponents();
    layoutComponents();
  }

  private void createComponents() {
    outputFormat = new JLabel(StringResource.getString(OUTPUT_FORMAT));
    outputFormatSelector = new JComboBox<>();
    outputFormatSelector.setPreferredSize(comboBoxPreferredSize);
    exportButton = new JButton(StringResource.getString(EXPORT));
  }

  private void layoutComponents() {
    GridBagConstraints constraints = new GridBagConstraints();

    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.gridy = -1;
    constraints.fill = GridBagConstraints.NONE;

    ////////// NEW ROW ///////////////////////////////
    constraints.gridy++;
    constraints.gridx = 0;
    constraints.anchor = GridBagConstraints.LINE_END;
    constraints.insets = labelInsets;
    add(outputFormat, constraints);

    constraints.gridx = 1;
    constraints.anchor = GridBagConstraints.LINE_START;
    constraints.insets = emptyInsets;
    add(outputFormatSelector, constraints);

    ////////// NEW ROW ///////////////////////////////
    constraints.gridy++;
    constraints.gridx = 1;
    constraints.anchor = GridBagConstraints.FIRST_LINE_END;
    constraints.insets = buttonInsets;
    constraints.weighty = 99; // forces all components to the top of the view.
    add(exportButton, constraints);
  }

  public JComboBox<OutputFormat> getOutputFormatSelector() {
    return outputFormatSelector;
  }

  public void setOutputFormatSelector(JComboBox<OutputFormat> outputFormatSelector) {
    this.outputFormatSelector = outputFormatSelector;
  }

  public JButton getExportButton() {
    return exportButton;
  }

  public void setExportButton(JButton exportButton) {
    this.exportButton = exportButton;
  }
}
