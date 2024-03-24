package com.oldfr.gym_tracker.ui.trainingDays;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.oldfr.gym_tracker.databinding.FragmentDayBinding;
import com.oldfr.gym_tracker.entities.Exercise;
import com.oldfr.gym_tracker.entities.TrainingDay;

public class TrainingDayFragment extends Fragment {
    private FragmentDayBinding binding;
    private TrainingDay trainingDay;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDayBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Получение номера дня из аргументов
        int dayNumber = getArguments() != null ? getArguments().getInt("DayNumber", 1) : 1;

        // Используйте dayNumber для загрузки данных
        initializeTrainingDay(dayNumber);

        displayExercises();

        return root;
    }

    private void initializeTrainingDay(int dayNumber) {
        trainingDay = new TrainingDay();

        // Пример: выбор упражнений на основе номера дня
        switch (dayNumber) {
            case 1:
                trainingDay.addExercise(new Exercise("Подтягивания", 5, 50));
                trainingDay.addExercise(new Exercise("Отжимания", 4, 30));
                break;
            case 2:
                trainingDay.addExercise(new Exercise("Приседания", 4, 40));
                trainingDay.addExercise(new Exercise("Жим ногами", 3, 60));
                break;
            case 3:
                trainingDay.addExercise(new Exercise("Плиометрические прыжки", 5, 20));
                trainingDay.addExercise(new Exercise("Скручивания", 5, 25));
                break;
            // Добавьте дополнительные случаи для других дней
            default:
                trainingDay.addExercise(new Exercise("Отдых", 0, 0));
                break;
        }
    }


    private void displayExercises() {
        LinearLayout container = binding.exercisesContainer;
        container.removeAllViews(); // Удалить все представления, если они уже есть

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

            // Добавление компонентов в горизонтальный LinearLayout
            exerciseLayout.addView(exerciseTitle);
            exerciseLayout.addView(setsInput);
            exerciseLayout.addView(weightInput);

            // Добавление горизонтального LinearLayout в контейнер
            container.addView(exerciseLayout);
        }
    }

}
