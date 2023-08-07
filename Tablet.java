package com.codegym.task.task27.task2712;

import com.codegym.task.task27.task2712.ad.AdvertisementManager;
import com.codegym.task.task27.task2712.ad.NoVideoAvailableException;
import com.codegym.task.task27.task2712.kitchen.Order;
import com.codegym.task.task27.task2712.kitchen.TestOrder;
import com.codegym.task.task27.task2712.statistics.StatisticsManager;
import com.codegym.task.task27.task2712.statistics.event.NoVideosAvailableEventDataRow;

import java.io.IOException;
import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {
    private final int number;
    private static Logger logger = Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue;

    public Tablet(int number) {
        this.number = number;
    }

    public void createOrder() {
        Order order = null;
        try {
            order = new Order(this);
            extracted(order);
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO,
                    "No video is available for the following order: " + order);
            assert order != null;
            StatisticsManager.getInstance().record(new NoVideosAvailableEventDataRow(
                    order.getTotalCookingTime()));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "The console is unavailable.");
        }
    }

    private void extracted(Order order) {
        if (!order.isEmpty()) {
            ConsoleHelper.writeMessage(order.toString()); //
            AdvertisementManager manager =
                    new AdvertisementManager(order.getTotalCookingTime() * 60);
            manager.processVideos();
            queue.add(order);
        }
    }

    public void createTestOrder() {
        TestOrder order = null;
        try {
            order = new TestOrder(this);
            extracted(order);
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO,
                    "No video is available for the following order: " + order);
            assert order != null;
            StatisticsManager.getInstance().record(new NoVideosAvailableEventDataRow(
                    order.getTotalCookingTime()));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "The console is unavailable.");
        }
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }
}
