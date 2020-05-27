package io.aleksander.pollyspeaks.controller;

import com.amazonaws.services.polly.model.OutputFormat;
import io.aleksander.pollyspeaks.controller.actions.FileActions;
import io.aleksander.pollyspeaks.gui.ExportAudioDialog;
import io.aleksander.pollyspeaks.model.TextToSpeechEngine;

import javax.swing.DefaultComboBoxModel;
import java.awt.Component;
import java.awt.event.WindowEvent;

public class ExportAudioController {
  private final TextToSpeechEngine textToSpeechEngine;
  private final String text;
  private final ExportAudioDialog view;

  public ExportAudioController(Component parent, TextToSpeechEngine textToSpeechEngine, String text) {
    this.textToSpeechEngine = textToSpeechEngine;
    this.text = text;
    view = new ExportAudioDialog();

    setUpOutpuFormatSelector();
    setUpExportButton();

    view.setLocationRelativeTo(parent);
    view.setVisible(true);
  }

  private void setUpOutpuFormatSelector() {
    OutputFormat[] outputFormats = {OutputFormat.Mp3, OutputFormat.Ogg_vorbis, OutputFormat.Pcm};
    DefaultComboBoxModel<OutputFormat> outputFormatComboBoxModel =
        new DefaultComboBoxModel<>(outputFormats);
    view.getOutputFormatSelector().setModel(outputFormatComboBoxModel);
  }

  private void setUpExportButton() {
    view.getExportButton().addActionListener(e -> {
      OutputFormat outputFormat = (OutputFormat) view.getOutputFormatSelector().getSelectedItem();
      FileActions.saveAsAudioFile(view, text, textToSpeechEngine, outputFormat);
      view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
    });
  }
}
