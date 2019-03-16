package com.rdev.tryp.model.login_response;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("message")
    private String message;

    @SerializedName("users")
    private Users users;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Users getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return
                "Data{" +
                        "message = '" + message + '\'' +
                        ",users = '" + users + '\'' +
                        "}";
    }
}
