package io.aleksander.gui;

import io.aleksander.gui.viewmodel.VoiceSelectModel;
import io.aleksander.utils.StringResource;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static io.aleksander.utils.StringResource.LANGUAGE;
import static io.aleksander.utils.StringResource.SPEAK;
import static io.aleksander.utils.StringResource.VOICE;
import static io.aleksander.utils.StringResource.VOICE_SETTINGS;

public class SettingsPanel extends JPanel implements PropertyChangeListener {
  private JLabel languageLabel;
  private JComboBox<String> languageSelector;
  private JLabel voiceLabel;
  private JComboBox<VoiceSelectModel> voiceSelector;
  private JButton speakButton;
  private final Insets labelInsets = new Insets(0, 0, 0, 5);
  private final Insets emptyInsets = new Insets(0, 0, 0, 0);
  private final Dimension comboBoxPreferredSize = new Dimension(150, 25);

  public SettingsPanel() {
    setLayout(new GridBagLayout());
    createBorder();
    createComponents();
    layoutComponents();
  }

  public JButton getSpeakButton() {
    return speakButton;
  }

  public JComboBox<VoiceSelectModel> getVoiceSelector() {
    return voiceSelector;
  }

  public JComboBox<String> getLanguageSelector() {
    return languageSelector;
  }

  private void createBorder() {
    Border innerBorder = BorderFactory.createTitledBorder(StringResource.getString(VOICE_SETTINGS));
    Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
  }

  private void createComponents() {
    languageLabel = new JLabel(StringResource.getString(LANGUAGE) + ":");
    languageSelector = new JComboBox<>();
    languageSelector.setPreferredSize(comboBoxPreferredSize);

    voiceLabel = new JLabel(StringResource.getString(VOICE) + ":");
    voiceSelector = new JComboBox<>();
    voiceSelector.setPreferredSize(comboBoxPreferredSize);

    speakButton = new JButton(StringResource.getString(SPEAK));
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
    add(languageLabel, constraints);

    constraints.gridx = 1;
    constraints.anchor = GridBagConstraints.LINE_START;
    constraints.insets = emptyInsets;
    add(languageSelector, constraints);

    ////////// NEW ROW ///////////////////////////////
    constraints.gridy++;
    constraints.gridx = 0;
    constraints.anchor = GridBagConstraints.LINE_END;
    constraints.insets = labelInsets;
    add(voiceLabel, constraints);

    constraints.gridx = 1;
    constraints.anchor = GridBagConstraints.LINE_START;
    constraints.insets = emptyInsets;
    add(voiceSelector, constraints);

    ////////// NEW ROW ///////////////////////////////
    constraints.gridy++;
    constraints.gridx = 1;
    constraints.anchor = GridBagConstraints.FIRST_LINE_START;
    constraints.insets = emptyInsets;
    constraints.weighty = 99; // forces all components to the top of the SettingsPanel.
    add(speakButton, constraints);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if(evt.getPropertyName().equals("isPlaying")) {
      boolean isPlaying = (boolean) evt.getNewValue();
      speakButton.setEnabled(!isPlaying);
    }
  }
}
