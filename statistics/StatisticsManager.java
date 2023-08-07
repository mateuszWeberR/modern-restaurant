package com.codegym.task.task27.task2712.statistics;

import com.codegym.task.task27.task2712.kitchen.Cook;
import com.codegym.task.task27.task2712.statistics.event.EventDataRow;
import com.codegym.task.task27.task2712.statistics.event.EventType;

import java.util.*;

// singleton
public class StatisticsManager {
    private static StatisticsManager INSTANCE;
    private StatisticsStorage statisticsStorage = new StatisticsStorage();

    private StatisticsManager() {

    }

    private class StatisticsStorage {
        Map<EventType, List<EventDataRow>> storage;

        public StatisticsStorage() {
            this.storage = new HashMap<>();
            for (EventType event : EventType.values()) {
                storage.put(event, new ArrayList<EventDataRow>());
            }
        }

        private void put(EventDataRow data) {
            if (data.getType() == EventType.ORDER_READY
                    || data.getType() == EventType.VIDEOS_SELECTED
                    || data.getType() == EventType.NO_VIDEOS_AVAILABLE) {
                storage.get(data.getType()).add(data);
            }
        }

        public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }
    }

    public static StatisticsManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StatisticsManager();
        }
        return INSTANCE;
    }

    public void record(EventDataRow data) {
        statisticsStorage.put(data);
    }

    public Map<EventType, List<EventDataRow>> getStorage() {
        return statisticsStorage.storage;
    }

}
