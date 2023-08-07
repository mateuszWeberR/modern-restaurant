package com.codegym.task.task27.task2712;

import com.codegym.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        String string = "";
        try {
            string = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> listChosenDishes = new ArrayList<>();

        writeMessage(Dish.allDishesToString());
        writeMessage("Choose dish from above and hit \"Enter\". Write \"exit\" if you done.");

        while (true) {
            String choice = readString().trim();
            if (choice.equals("exit"))
                break;

            Dish newDish = checkValidDish(choice);
            if (newDish != null) {
                listChosenDishes.add(newDish);
            } else {
                writeMessage("Invalid dish name. Please try again.");
            }
        }
        return listChosenDishes;
    }

    private static Dish checkValidDish(String choice) {
        for (Dish dish : Dish.values()) {
            if (dish.name().equals(choice)) {
                return dish;
            }
        }
        return null;
    }
}
