package com.oldfr.gym_tracker.entities;

public class Exercise {
    private String title;
    private int sets;
    private int maxWeight;

    public Exercise(String title, int sets, int maxWeight) {
        this.title = title;
        this.sets = sets;
        this.maxWeight = maxWeight;
    }

    // Геттеры и сеттеры
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }
}