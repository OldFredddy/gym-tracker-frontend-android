package com.oldfr.gym_tracker.entities;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class TrainingDay implements Parcelable {
    private List<Exercise> exercises;
    private String title;

    public TrainingDay(List<Exercise> exercises, String title) {
        this.exercises = exercises;
        this.title = title;
    }

    public TrainingDay() {
        exercises = new ArrayList<>();
    }

    protected TrainingDay(Parcel in) {
        title = in.readString();
        exercises = new ArrayList<>();
        in.readList(exercises, Exercise.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeList(exercises);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TrainingDay> CREATOR = new Creator<TrainingDay>() {
        @Override
        public TrainingDay createFromParcel(Parcel in) {
            return new TrainingDay(in);
        }

        @Override
        public TrainingDay[] newArray(int size) {
            return new TrainingDay[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addExercise(Exercise exercise) {
        if (exercises == null) {
            exercises = new ArrayList<>();
        }
        exercises.add(exercise);
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
