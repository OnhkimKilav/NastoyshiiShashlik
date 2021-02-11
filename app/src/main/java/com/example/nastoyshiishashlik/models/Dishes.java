package com.example.nastoyshiishashlik.models;

public enum Dishes {
    STOCK("STOCK"),
    SETS("SETS"),
    KEBAB("KEBAB"),
    LYULYAKEBAB("LYULYAKEBAB"),
    GRILLED_FISH("GRILLED_FISH"),
    BEVERAGES("BEVERAGES"),
    COLD_SNACKS("COLD_SNACKS"),
    GARNISH("GARNISH"),
    GRILLED_VEGETABLES("GRILLED_VEGETABLES"),
    HOT_SNACK("HOT_SNACK"),
    KHACHAPURI("KHACHAPURI"),
    PICKLED_MEAT("PICKLED_MEAT"),
    PITA("PITA"),
    SALAD("SALAD"),
    SAUCES("SAUCES"),
    FIRST_MEAL("FIRST_MEAL");

    private String title;

    Dishes(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Dishes{" +
                "title='" + title + '\'' +
                '}';
    }
}
