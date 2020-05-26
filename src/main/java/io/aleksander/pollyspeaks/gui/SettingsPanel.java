package io.aleksander.pollyspeaks.gui;

import io.aleksander.pollyspeaks.gui.viewmodel.VoiceSelectModelElement;
import io.aleksander.pollyspeaks.utils.StringResource;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import static io.aleksander.pollyspeaks.utils.StringResource.LANGUAGE;
import static io.aleksander.pollyspeaks.utils.StringResource.SPEAK;
import static io.aleksander.pollyspeaks.utils.StringResource.SSML;
import static io.aleksander.pollyspeaks.utils.StringResource.USE_SSML;
import static io.aleksander.pollyspeaks.utils.StringResource.VOICE;
import static io.aleksander.pollyspeaks.utils.StringResource.getString;

public class SettingsPanel extends JPanel {
  private final Dimension comboBoxPreferredSize = new Dimension(150, 20);
  private JPanel voiceSettingsGroup;
  private JLabel languageLabel;
  private JComboBox<String> languageSelector;
  private JLabel voiceLabel;
  private JComboBox<VoiceSelectModelElement> voiceSelector;
  private JPanel ssmlSettingsGroup;
  private JCheckBox ssmlCheckbox;
  private JButton speakButton;

  public SettingsPanel() {
    setLayout(new GridBagLayout());
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    createComponents();
    layoutComponents();
  }

  private void createComponents() {
    voiceSettingsGroup = createSettingsGroup(getString(VOICE));
    languageLabel = new JLabel(StringResource.getString(LANGUAGE) + ":");
    languageSelector = new JComboBox<>();
    languageSelector.setPreferredSize(comboBoxPreferredSize);

    voiceLabel = new JLabel(StringResource.getString(VOICE) + ":");
    voiceSelector = new JComboBox<>();
    voiceSelector.setPreferredSize(comboBoxPreferredSize);

    ssmlSettingsGroup = createSettingsGroup(getString(SSML));
    ssmlCheckbox = new JCheckBox(StringResource.getString(USE_SSML));

    speakButton = new JButton(StringResource.getString(SPEAK));
  }

  private void layoutComponents() {
    GridBagConstraints constraints = createStartingConstraint();

    layoutVoiceSettingsComponents();
    ////////// NEW ROW ///////////////////////////////
    constraints.gridy++;
    constraints.gridx = 0;
    constraints.anchor = GridBagConstraints.FIRST_LINE_START;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    constraints.insets = new Insets(0, 0, 0, 0);
    add(voiceSettingsGroup, constraints);

    layoutSSMLComponents();
    ////////// NEW ROW ///////////////////////////////
    constraints.gridy++;
    constraints.gridx = 0;
    constraints.anchor = GridBagConstraints.FIRST_LINE_START;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.gridwidth = GridBagConstraints.REMAINDER;
    constraints.insets = new Insets(5, 0, 0, 0);
    add(ssmlSettingsGroup, constraints);

    ////////// NEW ROW ///////////////////////////////
    constraints.gridy++;
    constraints.gridx = 1;
    constraints.anchor = GridBagConstraints.FIRST_LINE_END;
    constraints.weighty = 99; // forces all components to the top of the SettingsPanel.
    constraints.fill = GridBagConstraints.NONE;
    constraints.gridwidth = 1;
    constraints.insets = new Insets(5, 0, 0, 3);
    add(speakButton, constraints);
  }

  private JPanel createSettingsGroup(String groupName) {
    JPanel settingsGroup = new JPanel();

    // create the border
    Border outerBorder = BorderFactory.createTitledBorder(groupName);
    Border innerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    settingsGroup.setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

    // set the layout
    settingsGroup.setLayout(new GridBagLayout());

    return settingsGroup;
  }

  private GridBagConstraints createStartingConstraint() {
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.weightx = 1;
    constraints.weighty = 1;
    constraints.gridy = -1;
    constraints.fill = GridBagConstraints.NONE;
    return constraints;
  }

  private void layoutVoiceSettingsComponents() {
    GridBagConstraints constraints = createStartingConstraint();

    ////////// NEW ROW ///////////////////////////////
    constraints.gridy++;
    constraints.gridx = 0;
    constraints.anchor = GridBagConstraints.LINE_END;
    constraints.insets = new Insets(0, 0, 0, 5);
    voiceSettingsGroup.add(languageLabel, constraints);

    constraints.gridx = 1;
    constraints.anchor = GridBagConstraints.LINE_START;
    constraints.insets = new Insets(0, 0, 0, 0);
    voiceSettingsGroup.add(languageSelector, constraints);

    ////////// NEW ROW ///////////////////////////////
    constraints.gridy++;
    constraints.gridx = 0;
    constraints.anchor = GridBagConstraints.LINE_END;
    constraints.insets = new Insets(5, 0, 0, 5);
    voiceSettingsGroup.add(voiceLabel, constraints);

    constraints.gridx = 1;
    constraints.anchor = GridBagConstraints.LINE_START;
    constraints.insets = new Insets(5, 0, 0, 0);
    voiceSettingsGroup.add(voiceSelector, constraints);
  }

  private void layoutSSMLComponents() {
    GridBagConstraints constraints = createStartingConstraint();
    ////////// NEW ROW ///////////////////////////////
    constraints.gridy++;
    constraints.gridx = 0;
    constraints.anchor = GridBagConstraints.LINE_END;
    constraints.insets = new Insets(0, 0, 0, 5);
    ssmlSettingsGroup.add(ssmlCheckbox, constraints);
  }

  public JCheckBox getSsmlCheckbox() {
    return ssmlCheckbox;
  }

  public JButton getSpeakButton() {
    return speakButton;
  }

  public JComboBox<VoiceSelectModelElement> getVoiceSelector() {
    return voiceSelector;
  }

  public JComboBox<String> getLanguageSelector() {
    return languageSelector;
  }
}
