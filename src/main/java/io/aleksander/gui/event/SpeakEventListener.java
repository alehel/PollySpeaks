package io.aleksander.gui.event;

import java.util.EventListener;

public interface SpeakEventListener extends EventListener {
  void speakEventOccured(SpeakEvent event);
}
