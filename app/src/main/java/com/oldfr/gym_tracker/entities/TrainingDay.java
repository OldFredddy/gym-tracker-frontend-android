package com.oldfr.gym_tracker.entities;

import java.util.ArrayList;
import java.util.List;

public class TrainingDay {
    private List<Exercise> exercises;

    public TrainingDay() {
        this.exercises = new ArrayList<>();
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
