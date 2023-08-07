package com.codegym.task.task27.task2712;

import com.codegym.task.task27.task2712.ad.Advertisement;
import com.codegym.task.task27.task2712.ad.StatisticsAdvertisementManager;
import com.codegym.task.task27.task2712.statistics.StatisticsManager;
import com.codegym.task.task27.task2712.statistics.event.EventDataRow;
import com.codegym.task.task27.task2712.statistics.event.EventType;
import com.codegym.task.task27.task2712.statistics.event.OrderReadyEventDataRow;
import com.codegym.task.task27.task2712.statistics.event.VideosSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ManagerTablet {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

    public void printAdRevenue() {
        Map<EventType, List<EventDataRow>> storage = StatisticsManager.getInstance().getStorage();
        List<EventDataRow> listOfVideosSelected = getListFromEvent(storage, EventType.VIDEOS_SELECTED);

        // Sorted per each day (reverse order by Date)
        Map<Date, Long> amountPerEachDay = new TreeMap<>(Collections.reverseOrder());

        for (EventDataRow eventDataRow : listOfVideosSelected) {
            List list = ((VideosSelectedEventDataRow) eventDataRow).getOptimalVideoSet();
            long sum = 0;
            for (Object ad : list) {
                long amountPerImpression = ((Advertisement) ad).getAmountPerImpression();
                sum += amountPerImpression;
            }

            // Date truncated to days only, and put sum for each day separately
            Date dateWithoutTime = Date.from(eventDataRow.getDate().toInstant().truncatedTo(ChronoUnit.DAYS));

            if (!amountPerEachDay.containsKey(dateWithoutTime)) {
                amountPerEachDay.putIfAbsent(dateWithoutTime, sum);
            } else {
                long currentAmount = amountPerEachDay.get(dateWithoutTime);
                currentAmount += sum;
                amountPerEachDay.put(dateWithoutTime, currentAmount);
            }
        }

        long total = calculateTotalRevenueHelper(amountPerEachDay);

        printRevenueHelper(amountPerEachDay, total);

    }

    private long calculateTotalRevenueHelper(Map<Date, Long> amountPerEachDay) {
        long total = 0;
        for (Date date : amountPerEachDay.keySet()) {
            total += amountPerEachDay.get(date);
        }
        return total;
    }

    private void printRevenueHelper(Map<Date, Long> amountPerEachDay, long total) {
        for (Date date : amountPerEachDay.keySet()) {
            ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "%s - %.2f",
                    formatter.format(date),  ((double) amountPerEachDay.get(date) / 100)));
        }

        ConsoleHelper.writeMessage(String.format(Locale.ENGLISH, "Total - %.2f", ((double) total) / 100));
    }

    private List<EventDataRow> getListFromEvent
            (Map<EventType, List<EventDataRow>> storage, EventType eventType) {
        // Getting storage with VIDEOS SELECTED only
        List<EventDataRow> listOfVideosSelected = new ArrayList<>();
        for (EventType event : storage.keySet()) {
            if (event == eventType) {
                listOfVideosSelected = storage.get(eventType);
            }
        }
        return listOfVideosSelected;
    }

    public void printCookUtilization() {
        Map<EventType, List<EventDataRow>> storage = StatisticsManager.getInstance().getStorage();
        List<EventDataRow> listOfOrderReady = getListFromEvent(storage, EventType.ORDER_READY);

        Map<Date, Map<String, Integer>> cookingTimePerEachDay = new TreeMap<>(Collections.reverseOrder());

        for (EventDataRow event : listOfOrderReady) {

            Date dateWithoutTime = Date.from(event.getDate().toInstant().truncatedTo(ChronoUnit.DAYS));

            if (!cookingTimePerEachDay.containsKey(dateWithoutTime)) {
                Map<String, Integer> cooks = new TreeMap<>();
                String cookName = ((OrderReadyEventDataRow) event).getCookName();
                int cookingTimeInMinutes = event.getTime();
                cooks.putIfAbsent(cookName, cookingTimeInMinutes);
                cookingTimePerEachDay.putIfAbsent(dateWithoutTime, cooks);
            } else {
                TreeMap<String, Integer> cooks = (TreeMap<String, Integer>) cookingTimePerEachDay.get(dateWithoutTime);
                String cookName = ((OrderReadyEventDataRow) event).getCookName();
                int cookingTimeInMinutes = event.getTime();
                if (!cooks.containsKey(cookName)) {
                    cooks.putIfAbsent(cookName, cookingTimeInMinutes);
                } else {
                    int currentTime = cooks.get(cookName);
                    currentTime += cookingTimeInMinutes;
                    cooks.put(cookName, currentTime);
                }
            }
        }

        printCookUtilHelper(cookingTimePerEachDay);
    }

    private void printCookUtilHelper(Map<Date, Map<String, Integer>> cookingTimePerEachDay) {
        for (Date date : cookingTimePerEachDay.keySet()) {
            ConsoleHelper.writeMessage(formatter.format(date));

            for (String cook : cookingTimePerEachDay.get(date).keySet()) {
                int time = cookingTimePerEachDay.get(date).get(cook);
                ConsoleHelper.writeMessage(cook + " - " + time + " min");
            }
        }
    }

    public void printActiveVideoSet() {
        StatisticsAdvertisementManager statisticsAdvMan = StatisticsAdvertisementManager.getInstance();
        List<Advertisement> activeAdvertisements = statisticsAdvMan.getListOfActiveAdvertisements();

        activeAdvertisements.sort(Comparator.comparing(Advertisement::getName));

        for (Advertisement ad : activeAdvertisements) {
            ConsoleHelper.writeMessage(ad.getName() + " - " + ad.getImpressionsRemaining());
        }
        System.out.println();
    }

    public void printArchivedVideoSet() {
        StatisticsAdvertisementManager statisticsAdvMan = StatisticsAdvertisementManager.getInstance();
        List<Advertisement> inactiveAdvertisements = statisticsAdvMan.getListOfInactiveAdvertisements();

        inactiveAdvertisements.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));

        for (Advertisement ad : inactiveAdvertisements) {
            ConsoleHelper.writeMessage(ad.getName());
        }
        System.out.println();
    }

}
