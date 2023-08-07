package com.codegym.task.task27.task2712.kitchen;

import com.codegym.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
        initDishes();
    }

    @Override
    protected void initDishes() {
        this.dishes = new ArrayList<>();
        int randomAmountOfDishes = (int) (Math.random() * Dish.values().length + 1);
        List<Dish> temp = Arrays.asList(Dish.values());
        Collections.shuffle(temp);
        for (int i = 0; i < randomAmountOfDishes; i++) {
            dishes.add(temp.get(i));
        }
    }
}
