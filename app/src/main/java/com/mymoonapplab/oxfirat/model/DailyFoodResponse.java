package com.mymoonapplab.oxfirat.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DailyFoodResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<DailyFood> data;

    @SerializedName("message")
    private String message;

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DailyFood> getData() {
        return data;
    }

    public void setData(List<DailyFood> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
