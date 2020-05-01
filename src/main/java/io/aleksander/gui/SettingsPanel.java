package io.aleksander.gui;

import io.aleksander.controller.Controller;
import io.aleksander.gui.event.SpeakEvent;
import io.aleksander.gui.event.SpeakEventListener;
import io.aleksander.gui.model.VoiceSelectModel;
import io.aleksander.utils.Resources;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import static io.aleksander.utils.Resources.SPEAK;
import static io.aleksander.utils.Resources.VOICE;
import static io.aleksander.utils.Resources.VOICE_SETTINGS;

public class SettingsPanel extends JPanel {
  private final Controller controller;
  private final List<SpeakEventListener> speakEventListeners = new ArrayList<>();
  private JLabel voiceLabel;
  private JComboBox<VoiceSelectModel> voiceSelector;
  private JButton speakButton;
  private Insets labelInsets;
  private Insets emptyInsets;

  public SettingsPanel() {
    setLayout(new GridBagLayout());
    controller = new Controller();
    createBorder();
    createComponents();
    layoutComponents();
  }

  private void createBorder() {
    Border innerBorder = BorderFactory.createTitledBorder(Resources.getString(VOICE_SETTINGS));
    Border outerBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
  }

  private void createComponents() {
    voiceLabel = new JLabel(Resources.getString(VOICE) + ":");
    voiceSelector = new JComboBox<>();
    DefaultComboBoxModel<VoiceSelectModel> comboBoxModel = new DefaultComboBoxModel<>();
    comboBoxModel.addAll(controller.getVoices());
    voiceSelector.setModel(comboBoxModel);

    speakButton = new JButton(Resources.getString(SPEAK));
    speakButton.addActionListener(
        event -> {
          VoiceSelectModel voice = (VoiceSelectModel) voiceSelector.getSelectedItem();
          SpeakEvent speakEvent = new SpeakEvent(this, voice.getId());
          speakEventListeners.forEach(
              speakEventListener -> speakEventListener.speakEventOccured(speakEvent));
        });

    labelInsets = new Insets(0, 0, 0, 5);
    emptyInsets = new Insets(0, 0, 0, 0);
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

  public void addSpeakEventListener(SpeakEventListener speakEventListener) {
    this.speakEventListeners.add(speakEventListener);
  }

  public void removeSpeakEventListener(SpeakEventListener speakEventListener) {
    this.speakEventListeners.remove(speakEventListener);
  }
}
