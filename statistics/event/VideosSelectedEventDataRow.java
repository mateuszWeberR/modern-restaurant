package com.codegym.task.task27.task2712.statistics.event;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class VideosSelectedEventDataRow implements EventDataRow {
    private List optimalVideoSet;
    private long amount;
    private int totalDuration;
    private Date currentDate;

    public VideosSelectedEventDataRow(List optimalVideoSet, long amount, int totalDuration) {
        this.optimalVideoSet = optimalVideoSet;
        this.amount = amount;
        this.totalDuration = totalDuration;
        this.currentDate = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.VIDEOS_SELECTED;
    }

    @Override
    public Date getDate() {
        return currentDate;
    }

    @Override
    public int getTime() {
        return totalDuration;
    }


    public long getAmount() {
        return amount;
    }

    public List getOptimalVideoSet() {
        return optimalVideoSet;
    }

}
