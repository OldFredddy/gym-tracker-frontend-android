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
import com.oldfr.gym_tracker.servises.HttpService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private final String url = "http://"+HttpService.IP+":"+HttpService.PORT+"/get_days/";
    private final String userId = "0";
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
        List<TrainingDay> trainingDays = HttpService.getTrainingDays(url+userId);
        Menu menu = navigationView.getMenu();
        menu.clear();

        for (int i = 0; i < trainingDays.size(); i++) {
            final int dayIndex = i + 1;
            TrainingDay day = trainingDays.get(i);
            String dayTitle = "День " + dayIndex + " - " + day.getTitle();
            menu.add(Menu.NONE, Menu.NONE, i, dayTitle)
                    .setIcon(R.drawable.day1_icon)
                    .setOnMenuItemClickListener(item -> {
                        Bundle bundle = new Bundle();
                        bundle.putString("UserId", userId);
                        bundle.putParcelable("DayExercises", day);
                        navController.navigate(R.id.trainingDayFragment, bundle);
                        return true;
                    });

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
