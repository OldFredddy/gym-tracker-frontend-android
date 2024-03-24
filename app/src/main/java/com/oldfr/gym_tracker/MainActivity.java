package com.oldfr.gym_tracker;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.oldfr.gym_tracker.databinding.ActivityMainBinding;
import com.oldfr.gym_tracker.entities.Exercise;
import com.oldfr.gym_tracker.entities.TrainingDay;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        // Инициализация AppBarConfiguration с использованием navController
        mAppBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setOpenableLayout(drawer)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Здесь может быть логика для динамического создания и добавления элементов меню
        setupDynamicMenu(navigationView, navController);
    }

    private void setupDynamicMenu(NavigationView navigationView, NavController navController) {
        // Имитация получения данных с сервера
        List<TrainingDay> trainingDays = getMockedTrainingDays();

        Menu menu = navigationView.getMenu();
        menu.clear(); // Очистка существующих элементов меню

        for (int i = 0; i < trainingDays.size(); i++) {
            final int dayIndex = i + 1;  // Создаем финальную переменную для использования в лямбда-выражении
            TrainingDay day = trainingDays.get(i);
            String dayTitle = "Day " + dayIndex + " - " + getExercisesSummary(day.getExercises());

            menu.add(Menu.NONE, Menu.NONE, i, dayTitle)
                    .setIcon(R.drawable.day1_icon)
                    .setOnMenuItemClickListener(item -> {
                        Bundle bundle = new Bundle();
                        bundle.putInt("DayNumber", dayIndex);  // Передайте номер дня напрямую
                        navController.navigate(R.id.trainingDayFragment, bundle);
                        return true;
                    });

        }
    }
    private List<TrainingDay> getMockedTrainingDays() {
        List<TrainingDay> days = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TrainingDay day = new TrainingDay();
            day.addExercise(new Exercise("Exercise " + i, 3, 10));
            days.add(day);
        }
        return days;
    }

    private String getExercisesSummary(List<Exercise> exercises) {
        // Это пример, на практике вы можете хотеть более детально описать упражнения
        return exercises.size() + " exercises";
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
