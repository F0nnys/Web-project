package com.roy.async;

import java.util.List;

/**
 * Created by roy on 2018/3/25.
 */
public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType> getSupportEventTypes();
}
