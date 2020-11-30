package com.example.nastoyshiishashlik.model;

public class Menu {
    private final int poster;
    private final String name;

    public Menu(int poster, String name) {
        this.poster = poster;
        this.name = name;
    }

    public int getPoster() {
        return poster;
    }

    public String getName() {
        return name;
    }
}
