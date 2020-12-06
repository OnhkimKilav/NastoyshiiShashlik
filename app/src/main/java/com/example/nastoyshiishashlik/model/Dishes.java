package com.example.nastoyshiishashlik.model;

public enum Dishes {
    STOCK(""),
    SETS(""),
    KEBAB("KEBAB"),
    LYULYAKEBAB(""),
    GRILLED_FISH("GRILLED_FISH"),
    BEVERAGES(""),
    COLD_SNACKS(""),
    GARNISH(""),
    GRILLED_VEGETABLES(""),
    HOT_SNACK(""),
    KHACHAPURI(""),
    PICKLED_MEAT(""),
    PITA(""),
    SALAD("");

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
