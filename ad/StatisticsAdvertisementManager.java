package com.codegym.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.List;

public class StatisticsAdvertisementManager { // singleton
    private static StatisticsAdvertisementManager INSTANCE;
    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticsAdvertisementManager() {

    }

    public static StatisticsAdvertisementManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StatisticsAdvertisementManager();
        }
        return INSTANCE;
    }

    public List<Advertisement> getListOfActiveAdvertisements() {
        List<Advertisement> allAdvertisements = advertisementStorage.list();
        List<Advertisement> activeAdvertisements = new ArrayList<>();

        for (Advertisement ad : allAdvertisements) {
            if (ad.isActive())
                activeAdvertisements.add(ad);
        }
        return activeAdvertisements;
    }

    public List<Advertisement> getListOfInactiveAdvertisements() {
        List<Advertisement> allAdvertisements = advertisementStorage.list();
        List<Advertisement> inactiveAdvertisements = new ArrayList<>();

        for (Advertisement ad : allAdvertisements) {
            if (!ad.isActive())
                inactiveAdvertisements.add(ad);
        }
        return inactiveAdvertisements;
    }


}
