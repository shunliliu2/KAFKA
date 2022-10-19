package com.hxocr.idfpserver._31_oberver;

public enum IdfpType {

    SUNNY("Sunny"),
    RAINY("Rainy"),
    WINDY("Windy"),
    COLD("Cold");

    private final String description;

    IdfpType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
