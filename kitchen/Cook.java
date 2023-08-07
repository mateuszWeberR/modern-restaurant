package com.codegym.task.task27.task2712.kitchen;

import com.codegym.task.task27.task2712.ConsoleHelper;
import com.codegym.task.task27.task2712.Tablet;
import com.codegym.task.task27.task2712.statistics.StatisticsManager;
import com.codegym.task.task27.task2712.statistics.event.OrderReadyEventDataRow;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

// consumer
public class Cook extends Observable implements Runnable {
    private final String name;
    private boolean busy;
    private LinkedBlockingQueue<Order> queue;
    private final Set<Cook> cooks = new HashSet<>();

    public Cook(String name) {
        this.name = name;
        cooks.add(this);
    }

    public boolean isBusy() {
        return busy;
    }

    public void startCookingOrder(Order order) {
        this.busy = true;

        Tablet tablet = order.getTablet();
        ConsoleHelper.writeMessage(name + " Start cooking - " + order);
        int cookingTime = order.getTotalCookingTime();

        OrderReadyEventDataRow orderEvent = new OrderReadyEventDataRow(order.getTablet().toString(),
                name, cookingTime * 60, order.getDishes());
        StatisticsManager.getInstance().record(orderEvent);
        try {
            Thread.sleep(10L * cookingTime);
        } catch (InterruptedException ignored) {

        }
        setChanged();
        notifyObservers(order);
        this.busy = false;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        try {
            while (true) {
                Thread.sleep(10);
                if (!queue.isEmpty()) {
                    for (Cook cook : cooks) {
                        if (!cook.isBusy()) {
                            cook.startCookingOrder(queue.take());
                        }
                    }
                }
            }
        } catch (InterruptedException e) {

        }
    }
}
