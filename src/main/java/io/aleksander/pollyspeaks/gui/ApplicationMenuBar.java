package io.aleksander.pollyspeaks.gui;

import static io.aleksander.pollyspeaks.utils.StringResource.EXIT;
import static io.aleksander.pollyspeaks.utils.StringResource.EXPORT_TO_AUDIO_FILE;
import static io.aleksander.pollyspeaks.utils.StringResource.FILE;
import static io.aleksander.pollyspeaks.utils.StringResource.FORMAT;
import static io.aleksander.pollyspeaks.utils.StringResource.FORMAT_WORD_WRAP;
import static io.aleksander.pollyspeaks.utils.StringResource.NEW;
import static io.aleksander.pollyspeaks.utils.StringResource.OPEN;
import static io.aleksander.pollyspeaks.utils.StringResource.SAVE;
import static io.aleksander.pollyspeaks.utils.StringResource.SAVE_AS;

import io.aleksander.pollyspeaks.utils.StringResource;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

public class ApplicationMenuBar extends JMenuBar {

  private JMenuItem fontItem;
  private JMenuItem newItem;
  private JMenuItem openItem;
  private JMenuItem saveItem;
  private JMenuItem saveAsItem;
  private JMenuItem exportToAudioFile;
  private JMenuItem exitItem;
  private JCheckBoxMenuItem wordWrapItem;

  public ApplicationMenuBar() {
    // File menu
    JMenu fileMenu = new JMenu(StringResource.getString(FILE));
    fileMenu.setMnemonic(KeyEvent.VK_F);

    newItem = new JMenuItem(StringResource.getString(NEW));
    newItem.setMnemonic(KeyEvent.VK_N);
    newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
    ImageIcon newIcon = getImageIcon("general/New16.gif");
    newItem.setIcon(newIcon);
    fileMenu.add(newItem);

    openItem = new JMenuItem(StringResource.getString(OPEN));
    openItem.setMnemonic(KeyEvent.VK_O);
    openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
    ImageIcon openIcon = getImageIcon("general/Open16.gif");
    openItem.setIcon(openIcon);
    fileMenu.add(openItem);

    saveItem = new JMenuItem(StringResource.getString(SAVE));
    saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
    ImageIcon saveIcon = getImageIcon("general/Save16.gif");
    saveItem.setIcon(saveIcon);
    fileMenu.add(saveItem);

    saveAsItem = new JMenuItem(StringResource.getString(SAVE_AS));
    saveAsItem.setMnemonic(KeyEvent.VK_S);
    saveAsItem.setAccelerator(
        KeyStroke.getKeyStroke(
            KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
    ImageIcon saveAsIcon = getImageIcon("general/SaveAs16.gif");
    saveAsItem.setIcon(saveAsIcon);
    fileMenu.add(saveAsItem);

    exportToAudioFile = new JMenuItem(StringResource.getString(EXPORT_TO_AUDIO_FILE));
    ImageIcon exportIcon = getImageIcon("general/Export16.gif");
    exportToAudioFile.setIcon(exportIcon);
    fileMenu.add(exportToAudioFile);

    fileMenu.add(new JSeparator());

    exitItem = new JMenuItem(StringResource.getString(EXIT));
    exitItem.setMnemonic(KeyEvent.VK_E);
    fileMenu.add(exitItem);

    // Format menu
    JMenu viewMenu = new JMenu(StringResource.getString(FORMAT));
    viewMenu.setMnemonic(KeyEvent.VK_V);

    wordWrapItem = new JCheckBoxMenuItem(StringResource.getString(FORMAT_WORD_WRAP));
    wordWrapItem.setMnemonic(KeyEvent.VK_W);
    wordWrapItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.ALT_DOWN_MASK));
    viewMenu.add(wordWrapItem);

    fontItem = new JMenuItem(StringResource.getString(StringResource.FONT));
    viewMenu.add(fontItem);

    add(fileMenu);
    add(viewMenu);
  }

  public JMenuItem getFontItem() {
    return fontItem;
  }

  public void setFontItem(JMenuItem fontItem) {
    this.fontItem = fontItem;
  }

  private ImageIcon getImageIcon(String url) {
    URL fullUrl = getClass().getResource("/toolbarButtonGraphics/" + url);
    return new ImageIcon(fullUrl);
  }

  public JMenuItem getExportToAudioFile() {
    return exportToAudioFile;
  }

  public void setExportToAudioFile(JMenuItem exportToAudioFile) {
    this.exportToAudioFile = exportToAudioFile;
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
