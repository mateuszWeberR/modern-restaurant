package com.codegym.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementStorage {
    private static AdvertisementStorage INSTANCE;
    private final List<Advertisement> videos = new ArrayList<>();

    private AdvertisementStorage() {
        Object someContent = new Object();
        videos.add(new Advertisement(someContent, "First Video",
                5000, 100, 3 * 60));
        videos.add(new Advertisement(someContent, "Second Video",
                100, 10, 15 * 60));
        videos.add(new Advertisement(someContent, "Third Video",
                400, 2, 10 * 60));
        videos.add(new Advertisement(someContent, "Fourth Video",
                200, 5, 10 * 5));
    }

    public static AdvertisementStorage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AdvertisementStorage();
        }
        return INSTANCE;
    }

    public List<Advertisement> list() {
        return videos;
    }

    public void add(Advertisement advertisement) {
        videos.add(advertisement);
    }

}
