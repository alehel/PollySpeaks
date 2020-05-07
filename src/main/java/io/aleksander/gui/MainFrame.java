package io.aleksander.gui;

import io.aleksander.controller.ApplicationMenuBarController;
import io.aleksander.utils.StringResource;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;


import static io.aleksander.utils.StringResource.APPLICATION_TITLE;


public class MainFrame extends JFrame {
  private final SettingsPanel settingsPanel;
  private final JTextArea textArea;

  public MainFrame() {
    // configure frame
    setTitle(StringResource.getString(APPLICATION_TITLE));
    setMinimumSize(new Dimension(600, 500));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // create components
    settingsPanel = createSettingsPanel();
    textArea = createTextArea();

    add(settingsPanel, BorderLayout.WEST);
    add(new JScrollPane(textArea), BorderLayout.CENTER);
  }

  private SettingsPanel createSettingsPanel() {
    SettingsPanel settingsPanel = new SettingsPanel();
    settingsPanel.setPreferredSize(new Dimension(300, 200));
    return settingsPanel;
  }

  private JTextArea createTextArea() {
    JTextArea textArea = new JTextArea();
    Border textAreaBorder = BorderFactory.createLineBorder(Color.BLACK);
    textAreaBorder =
        BorderFactory.createCompoundBorder(
            textAreaBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10));
    textArea.setBorder(textAreaBorder);
    return textArea;
  }

  public SettingsPanel getSettingsPanel() {
    return settingsPanel;
  }

  public JTextArea getTextArea() {
    return textArea;
  }
}
