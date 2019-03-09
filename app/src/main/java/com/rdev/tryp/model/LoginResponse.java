package com.rdev.tryp.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("data")
    private Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    @Override
    public String toString() {
        return
                "LoginResponse{" +
                        "data = '" + data + '\'' +
                        "}";
    }
}