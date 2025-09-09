package com.example.listycity;

import androidx.annotation.NonNull;

public class City {
    private final String name;

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
