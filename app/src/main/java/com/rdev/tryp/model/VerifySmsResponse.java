package com.rdev.tryp.model;

import com.google.gson.annotations.SerializedName;

public class VerifySmsResponse {

    @SerializedName("errors")
    private Errors errors;

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return
                "VerifySmsResponse{" +
                        "errors = '" + errors + '\'' +
                        "}";
    }
}