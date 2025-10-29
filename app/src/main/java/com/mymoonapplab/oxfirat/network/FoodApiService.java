package com.mymoonapplab.oxfirat.network;

import com.mymoonapplab.oxfirat.model.DailyFoodResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface FoodApiService {

    @GET("api/get-dailyFood")
    Call<DailyFoodResponse> getDailyFood(@Header("Authorization") String authorization);
}
