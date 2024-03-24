package com.oldfr.gym_tracker.ui.trainingDays;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.oldfr.gym_tracker.databinding.FragmentDayBinding;
import com.oldfr.gym_tracker.entities.Exercise;
import com.oldfr.gym_tracker.entities.TrainingDay;
import com.oldfr.gym_tracker.servises.HttpService;

import java.util.ArrayList;
import java.util.List;

public class TrainingDayFragment extends Fragment {
    private FragmentDayBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TrainingDay day = getArguments() != null ? getArguments().getParcelable("DayExercises") : getMockDay();
        String userId = getArguments() != null ? getArguments().getString("UserId") : "0";
        displayExercises(day, userId);
        return root;
    }
    private void displayExercises(TrainingDay trainingDay, String userId) {
        LinearLayout container = binding.exercisesContainer;
        container.removeAllViews();

        for (Exercise exercise : trainingDay.getExercises()) {
            // Создание горизонтального LinearLayout для каждого упражнения
            LinearLayout exerciseLayout = new LinearLayout(getContext());
            exerciseLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            exerciseLayout.setOrientation(LinearLayout.HORIZONTAL);

            // TextView для названия упражнения
            TextView exerciseTitle = new TextView(getContext());
            exerciseTitle.setText(exercise.getTitle());
            exerciseTitle.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            ));

            // EditText для количества подходов
            EditText setsInput = new EditText(getContext());
            setsInput.setInputType(InputType.TYPE_CLASS_NUMBER);
            setsInput.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            setsInput.setHint("Подходы");

            // EditText для максимального веса
            EditText weightInput = new EditText(getContext());
            weightInput.setInputType(InputType.TYPE_CLASS_NUMBER);
            weightInput.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            weightInput.setHint("Вес");

            EditText repsInput = new EditText(getContext());
            repsInput.setInputType(InputType.TYPE_CLASS_NUMBER);
            repsInput.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            repsInput.setHint("Повторения");

            // Добавление компонентов в горизонтальный LinearLayout
            exerciseLayout.addView(exerciseTitle);
            exerciseLayout.addView(setsInput);
            exerciseLayout.addView(weightInput);
            exerciseLayout.addView(repsInput);
            // Добавление горизонтального LinearLayout в контейнер
            container.addView(exerciseLayout);
        }
        Button sendButton = new Button(getContext());
        sendButton.setText("Отправить на сервер");
        sendButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        sendButton.setOnClickListener(v -> sendTrainingDayToServer(trainingDay, userId));
        container.addView(sendButton);
    }
    private void sendTrainingDayToServer(TrainingDay trainingDay, String userId) {
        trainingDay = updateTrainingDayFromUI(trainingDay);
        HttpService.sendTrainingDay(trainingDay, userId);
    }
    private TrainingDay updateTrainingDayFromUI(TrainingDay trainingDay) {
        LinearLayout container = binding.exercisesContainer;
        TrainingDay updatedTrainingDay = new TrainingDay();
        updatedTrainingDay.setTitle(trainingDay.getTitle());
        int childCount = container.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View view = container.getChildAt(i);
            if (view instanceof LinearLayout) {
                LinearLayout exerciseLayout = (LinearLayout) view;
                EditText setsInput = (EditText) exerciseLayout.getChildAt(1);
                EditText weightInput = (EditText) exerciseLayout.getChildAt(2);
                EditText repsInput = (EditText) exerciseLayout.getChildAt(3);
                try {
                    int sets = Integer.parseInt(setsInput.getText().toString());
                    int weight = Integer.parseInt(weightInput.getText().toString());
                    int reps = Integer.parseInt(repsInput.getText().toString());
                    Exercise exercise = trainingDay.getExercises().get(i); // Получаем текущее упражнение
                    exercise.setSets(sets);
                    exercise.setMaxWeight(weight);
                    exercise.setReps(reps);
                    updatedTrainingDay.addExercise(exercise);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    showNumberFormatExceptionToast();
                }
            }
        }
        return updatedTrainingDay;
    }
    private void showNumberFormatExceptionToast() {
        getActivity().runOnUiThread(() ->
                Toast.makeText(getContext(), "Ошибка ввода.  Проверь введенные данные.", Toast.LENGTH_LONG).show()
        );
    }
    private TrainingDay getMockDay(){
        TrainingDay day = new TrainingDay();
        day.setTitle("Title of Day");
     for (int i = 0; i < 2; i++) {
         Exercise exercise = new Exercise("Title of Ex № "+i, i, 100, 6);
         day.addExercise(exercise);
     }
        return  day;
 }
}
