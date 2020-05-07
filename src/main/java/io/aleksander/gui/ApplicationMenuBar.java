package io.aleksander.gui;

import io.aleksander.utils.StringResource;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static io.aleksander.utils.StringResource.EXIT;
import static io.aleksander.utils.StringResource.FILE;
import static io.aleksander.utils.StringResource.NEW;
import static io.aleksander.utils.StringResource.OPEN;
import static io.aleksander.utils.StringResource.SAVE;
import static io.aleksander.utils.StringResource.SAVE_AS;
import static io.aleksander.utils.StringResource.VIEW;
import static io.aleksander.utils.StringResource.VIEW_WORD_WRAP;

public class ApplicationMenuBar extends JMenuBar {
  private JMenuItem newItem;
  private JMenuItem openItem;
  private JMenuItem saveItem;
  private JMenuItem saveAsItem;
  private JMenuItem exitItem;
  private JCheckBoxMenuItem wordWrapItem;

  public ApplicationMenuBar() {
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

    add(fileMenu);
    add(viewMenu);
  }

  public JMenuItem getNewItem() {
    return newItem;
  }

  public void setNewItem(JMenuItem newItem) {
    this.newItem = newItem;
  }

  public JMenuItem getOpenItem() {
    return openItem;
  }

  public void setOpenItem(JMenuItem openItem) {
    this.openItem = openItem;
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

  public JMenuItem getExitItem() {
    return exitItem;
  }

  public void setExitItem(JMenuItem exitItem) {
    this.exitItem = exitItem;
  }

  public JCheckBoxMenuItem getWordWrapItem() {
    return wordWrapItem;
  }

  public void setWordWrapItem(JCheckBoxMenuItem wordWrapItem) {
    this.wordWrapItem = wordWrapItem;
  }
}
