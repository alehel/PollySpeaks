package io.aleksander.pollyspeaks.gui;

import io.aleksander.pollyspeaks.controller.actions.SaveAsAudioFileAction;
import java.awt.Color;
import java.awt.Cursor;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JLabel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JWebLink extends JLabel {
  private static final Logger logger = LogManager.getLogger(SaveAsAudioFileAction.class);

  private final String text;
  private final String link;

  public JWebLink(String text, String link) {
    super(text);
    this.text = text;
    this.link = link;

    setForeground(Color.BLUE.darker());
    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    setUpMouseListener();
  }

  private void setUpMouseListener() {
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        try {
          Desktop.getDesktop().browse(new URI(link));
        } catch (IOException | URISyntaxException exception) {
          logger.error("Failed to open link " + link + " in browser.");
        }
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        setText("<html><a href='"+link+"'>" + text + "</a></html>");
      }

      @Override
      public void mouseExited(MouseEvent e) {
        setText(text);
      }
    });
  }


}
