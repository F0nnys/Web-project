package com.roy.wenda.async;

import java.awt.*;
import java.util.List;

public interface EventHandler {
    void doHandle(EventModel eventModel);
    List<EventType> getSupportEventTypes();
}
