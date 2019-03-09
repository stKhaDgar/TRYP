package com.rdev.tryp.model.sign_up_response;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {

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
                "SignUpResponse{" +
                        "data = '" + data + '\'' +
                        "}";
    }
}