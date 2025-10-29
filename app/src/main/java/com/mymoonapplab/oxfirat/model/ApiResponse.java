package com.mymoonapplab.oxfirat.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse<T> {
    @SerializedName("Success")
    private List<T> success;

    public List<T> getSuccess() {
        return success;
    }

    public void setSuccess(List<T> success) {
        this.success = success;
    }
}
