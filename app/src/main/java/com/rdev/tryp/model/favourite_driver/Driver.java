package com.rdev.tryp.model.favourite_driver;

import com.google.gson.annotations.SerializedName;

public class Driver {

    @SerializedName("dialing_code")
    private String dialingCode;

    @SerializedName("image")
    private String image;

    @SerializedName("rating")
    private int rating;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("first_name")
    private String firstName;

    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }

    public String getDialingCode() {
        return dialingCode;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public String toString() {
        return
                "Driver{" +
                        "dialing_code = '" + dialingCode + '\'' +
                        ",image = '" + image + '\'' +
                        ",rating = '" + rating + '\'' +
                        ",last_name = '" + lastName + '\'' +
                        ",phone_number = '" + phoneNumber + '\'' +
                        ",first_name = '" + firstName + '\'' +
                        "}";
    }
}