package com.oldfr.gym_tracker.ui.trainingDays;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TrainingDayViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public TrainingDayViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is training day fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}