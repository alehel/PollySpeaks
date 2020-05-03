package io.aleksander.gui;

import io.aleksander.utils.StringResource;

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

import static io.aleksander.utils.StringResource.APPLICATION_TITLE;
import static io.aleksander.utils.StringResource.EXIT;
import static io.aleksander.utils.StringResource.FILE;

public class MainFrame extends JFrame {
  private final SettingsPanel settingsPanel;
  private final JTextArea textArea;
  private final JMenuBar menuBar;

  public MainFrame() {
    // configure frame
    setTitle(StringResource.getString(APPLICATION_TITLE));
    setMinimumSize(new Dimension(600, 500));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // create components
    settingsPanel = createSettingsPanel();
    textArea = createTextArea();
    menuBar = createMenuBar();

    // layout components
    setJMenuBar(menuBar);
    add(settingsPanel, BorderLayout.WEST);
    add(new JScrollPane(textArea), BorderLayout.CENTER);
  }

  public SettingsPanel getSettingsPanel() {
    return settingsPanel;
  }

  public JTextArea getTextArea() {
    return textArea;
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

  private SettingsPanel createSettingsPanel() {
    SettingsPanel settingsPanel = new SettingsPanel();
    settingsPanel.setPreferredSize(new Dimension(300, 200));
    return settingsPanel;
  }

  private JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    // File menu
    JMenu fileMenu = new JMenu(StringResource.getString(FILE));
    fileMenu.setMnemonic(KeyEvent.VK_F);

    JMenuItem exitItem = new JMenuItem(StringResource.getString(EXIT));
    exitItem.setMnemonic(KeyEvent.VK_E);
    exitItem.addActionListener(event -> System.exit(0));
    fileMenu.add(exitItem);

    menuBar.add(fileMenu);
    return menuBar;
  }
}
