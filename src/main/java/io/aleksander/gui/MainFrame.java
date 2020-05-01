package io.aleksander.gui;

import io.aleksander.controller.Controller;
import io.aleksander.gui.event.SpeakEvent;
import io.aleksander.gui.event.SpeakEventListener;
import io.aleksander.utils.Resources;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import static io.aleksander.utils.Resources.APPLICATION_TITLE;
import static io.aleksander.utils.Resources.EXIT;
import static io.aleksander.utils.Resources.FILE;

public class MainFrame extends JFrame implements SpeakEventListener {
  private final SettingsPanel settingsPanel;
  private final JTextArea textArea;
  private final Controller controller;
  private final JMenuBar menuBar;

  public MainFrame() {
    configureMainFrame();

    // create components
    settingsPanel = createSettingsPanel();
    textArea = createTextArea();
    menuBar = createMenuBar();

    // layout components
    setJMenuBar(menuBar);
    add(settingsPanel, BorderLayout.WEST);
    add(textArea, BorderLayout.CENTER);

    // set up controller
    controller = new Controller();

    // attach listeners
    settingsPanel.addSpeakEventListener(this);

    // display frame
    setVisible(true);
  }

  private JTextArea createTextArea() {
    JTextArea textArea = new JTextArea();
    Border textAreaBorder = BorderFactory.createLineBorder(Color.BLACK);
    textAreaBorder =
        BorderFactory.createCompoundBorder(
            textAreaBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10));
    textArea.setBorder(textAreaBorder);
    textArea.add(new JScrollPane(), BorderLayout.CENTER);
    return textArea;
  }

  private SettingsPanel createSettingsPanel() {
    SettingsPanel settingsPanel = new SettingsPanel();
    settingsPanel.setPreferredSize(new Dimension(200, 200));
    return settingsPanel;
  }

  private JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    // File menu
    JMenu fileMenu = new JMenu(Resources.getString(FILE));
    fileMenu.setMnemonic(KeyEvent.VK_F);

    JMenuItem exitItem = new JMenuItem(Resources.getString(EXIT));
    exitItem.setMnemonic(KeyEvent.VK_E);
    exitItem.addActionListener(event -> System.exit(0));
    fileMenu.add(exitItem);

    menuBar.add(fileMenu);
    return menuBar;
  }

  private void configureMainFrame() {
    setTitle(Resources.getString(APPLICATION_TITLE));
    setMinimumSize(new Dimension(600, 500));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
  }

  @Override
  public void speakEventOccured(SpeakEvent event) {
    controller.synthesizeText(event.getVoiceId(), textArea.getText());
  }
}
