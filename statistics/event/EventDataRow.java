package com.codegym.task.task27.task2712.statistics.event;

import java.util.Date;

public interface EventDataRow {
    EventType getType();
    Date getDate(); // returns the row's creation date
    int getTime(); //  returns the duration
}
