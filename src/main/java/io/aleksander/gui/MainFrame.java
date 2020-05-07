package io.aleksander.gui;

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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.security.Key;
import java.security.KeyStore;

import static io.aleksander.utils.StringResource.APPLICATION_TITLE;
import static io.aleksander.utils.StringResource.EXIT;
import static io.aleksander.utils.StringResource.FILE;
import static io.aleksander.utils.StringResource.NEW;
import static io.aleksander.utils.StringResource.OPEN;
import static io.aleksander.utils.StringResource.SAVE;
import static io.aleksander.utils.StringResource.SAVE_AS;
import static io.aleksander.utils.StringResource.VIEW;
import static io.aleksander.utils.StringResource.VIEW_WORD_WRAP;

public class MainFrame extends JFrame {
  private final SettingsPanel settingsPanel;
  private final JTextArea textArea;
  private final JMenuBar menuBar;
  private JMenuItem exitItem;
  private JMenuItem openItem;
  private JCheckBoxMenuItem wordWrapItem;
  private JMenuItem saveAsItem;
  private JMenuItem saveItem;
  private JMenuItem newItem;

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

  private JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    // File menu
    JMenu fileMenu = new JMenu(StringResource.getString(FILE));
    fileMenu.setMnemonic(KeyEvent.VK_F);

    newItem = new JMenuItem(StringResource.getString(NEW));
    newItem.setMnemonic(KeyEvent.VK_N);
    newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
    fileMenu.add(newItem);

    openItem = new JMenuItem(StringResource.getString(OPEN));
    openItem.setMnemonic(KeyEvent.VK_O);
    openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
    fileMenu.add(openItem);

    saveItem = new JMenuItem(StringResource.getString(SAVE));
    saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
    fileMenu.add(saveItem);

    saveAsItem = new JMenuItem(StringResource.getString(SAVE_AS));
    saveAsItem.setMnemonic(KeyEvent.VK_S);
    saveAsItem.setAccelerator(
        KeyStroke.getKeyStroke(
            KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
    fileMenu.add(saveAsItem);

    fileMenu.add(new JSeparator());

    exitItem = new JMenuItem(StringResource.getString(EXIT));
    exitItem.setMnemonic(KeyEvent.VK_E);
    fileMenu.add(exitItem);

    // View menu
    JMenu viewMenu = new JMenu(StringResource.getString(VIEW));
    viewMenu.setMnemonic(KeyEvent.VK_V);

    wordWrapItem = new JCheckBoxMenuItem(StringResource.getString(VIEW_WORD_WRAP));
    wordWrapItem.setMnemonic(KeyEvent.VK_W);
    wordWrapItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.ALT_DOWN_MASK));
    viewMenu.add(wordWrapItem);

    menuBar.add(fileMenu);
    menuBar.add(viewMenu);
    return menuBar;
  }

  public JMenuItem getNewItem() {
    return newItem;
  }

  public void setNewItem(JMenuItem newItem) {
    this.newItem = newItem;
  }

  public JMenuItem getSaveItem() {
    return saveItem;
  }

  public void setSaveItem(JMenuItem saveItem) {
    this.saveItem = saveItem;
  }

  public JMenuItem getSaveAsItem() {
    return saveAsItem;
  }

  public void setSaveAsItem(JMenuItem saveAsItem) {
    this.saveAsItem = saveAsItem;
  }

  public JCheckBoxMenuItem getWordWrapItem() {
    return wordWrapItem;
  }

  public void setWordWrapItem(JCheckBoxMenuItem wordWrapItem) {
    this.wordWrapItem = wordWrapItem;
  }

  public JMenuItem getOpenItem() {
    return openItem;
  }

  public void setOpenItem(JMenuItem openItem) {
    this.openItem = openItem;
  }

  public JMenuItem getExitItem() {
    return exitItem;
  }

  public void setExitItem(JMenuItem exitItem) {
    this.exitItem = exitItem;
  }

  public SettingsPanel getSettingsPanel() {
    return settingsPanel;
  }

  public JTextArea getTextArea() {
    return textArea;
  }
}
