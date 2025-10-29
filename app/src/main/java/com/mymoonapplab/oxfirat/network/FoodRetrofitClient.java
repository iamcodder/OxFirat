package com.mymoonapplab.oxfirat.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class FoodRetrofitClient {
    private static final String BASE_URL = "https://ddyo.firat.edu.tr/";
    public static final String BEARER_TOKEN = "Bearer O4w2RmYQxJ9Ehb7VcZ1zGk8aLt5Fd3TzXqA2rPjW6Vu0KcDyB1uM5N4gKnQhLv9Sj3Cp8oFwZt7Yi0p6HcXbTkR2o3U7nPzB4w1yLkV9f0JdQ5mWl8VsNzA6tCqE2rYx1gFo0uZ7S3pD6yTjQ4Z5bL8KqHnX2c8F7YpKJ9LzDqW1mXv8bNz0P0C6fA3RtG9QWk5h7oYyZ4bLw2tM9Jn1H8e3u6oVpF7X0NqW4sT8rL2C9mGj5Z0YnB3hLpK7R4F2T9";

    private static FoodRetrofitClient instance;
    private final FoodApiService foodApiService;

    private FoodRetrofitClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        foodApiService = retrofit.create(FoodApiService.class);
    }

    public static synchronized FoodRetrofitClient getInstance() {
        if (instance == null) {
            instance = new FoodRetrofitClient();
        }
        return instance;
    }

    public FoodApiService getFoodApiService() {
        return foodApiService;
    }
}
