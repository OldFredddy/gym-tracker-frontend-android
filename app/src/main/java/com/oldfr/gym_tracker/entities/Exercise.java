package com.oldfr.gym_tracker.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
    private String title;
    private int sets;
    private int maxWeight;
    private int reps;

    public Exercise(String title, int sets, int maxWeight, int reps) {
        this.title = title;
        this.sets = sets;
        this.maxWeight = maxWeight;
        this.reps = reps;
    }

    protected Exercise(Parcel in) {
        title = in.readString();
        sets = in.readInt();
        maxWeight = in.readInt();
        reps = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(sets);
        dest.writeInt(maxWeight);
        dest.writeInt(reps);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

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

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
}
