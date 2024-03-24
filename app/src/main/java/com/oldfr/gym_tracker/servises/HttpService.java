package com.oldfr.gym_tracker.servises;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oldfr.gym_tracker.entities.Exercise;
import com.oldfr.gym_tracker.entities.TrainingDay;
import okhttp3.*;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HttpService {
    private static final OkHttpClient client = new OkHttpClient();
    static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final Gson gson = new Gson();
    public static final String IP = "10.0.2.2";//85.175.232.186  95.142.45.133 - 95.xxx will be
    public static final String PORT = "26745";

    public static void sendTrainingDay(TrainingDay trainingDay, String userId) {
        String json = gson.toJson(trainingDay);
        sendPostRequest("http://"+IP+":"+PORT+"/add_day/"+userId, json);
    }

    private static void sendPostRequest(String url, String json) {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<TrainingDay> getTrainingDays(String... urls){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urls[0])
                .build();
        boolean isFetchSuccessful = false;
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                isFetchSuccessful = true;
                String jsonResponse = response.body().string();
                Gson gson = new Gson();
                Type listType = new TypeToken<List<TrainingDay>>() {
                }.getType();
                return gson.fromJson(jsonResponse, listType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!isFetchSuccessful){
            return  getMockedTrainingDays();
        }
        return  getMockedTrainingDays();
    }
    private static List<TrainingDay> getMockedTrainingDays() {
        List<TrainingDay> days = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TrainingDay day = new TrainingDay();
            day.addExercise(new Exercise("Exercise " + i, 3, 10, 6));
            days.add(day);
        }
        return days;
    }
}
