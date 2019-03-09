package com.rdev.tryp.model;

import com.google.gson.annotations.SerializedName;

public class Errors {

    @SerializedName("code_not_found")
    private String codeNotFound;

    public void setCodeNotFound(String codeNotFound) {
        this.codeNotFound = codeNotFound;
    }

    public String getCodeNotFound() {
        return codeNotFound;
    }

    @Override
    public String toString() {
        return
                "Errors{" +
                        "code_not_found = '" + codeNotFound + '\'' +
                        "}";
    }
}