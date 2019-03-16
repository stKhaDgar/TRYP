package com.rdev.tryp.model.login_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifySmsResponse {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return
                "VerifySmsResponse{" +
                        "data = '" + data + '\'' +
                        "}";
    }
}