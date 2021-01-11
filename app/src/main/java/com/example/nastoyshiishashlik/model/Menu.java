package com.example.nastoyshiishashlik.model;

public class Menu {
    private final int poster;
    private final String name;
    private Dishes dishes;

    public Menu(int poster, String name, Dishes dishes) {
        this.poster = poster;
        this.name = name;
        this.dishes = dishes;
    }

    public int getPoster() {
        return poster;
    }

    public String getName() {
        return name;
    }

    public Dishes getDishes() {
        return dishes;
    }
}
