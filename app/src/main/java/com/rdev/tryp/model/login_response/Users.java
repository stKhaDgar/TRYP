package com.rdev.tryp.model.login_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Users {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("dialing_code")
    @Expose
    private String dialingCode;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("assigned_referral_code")
    @Expose
    private String assignedReferralCode;
    @SerializedName("ref_code")
    @Expose
    private String refCode;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("favorite_addresses")
    @Expose
    private List<Object> favoriteAddresses = null;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDialingCode() {
        return dialingCode;
    }

    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAssignedReferralCode() {
        return assignedReferralCode;
    }

    public void setAssignedReferralCode(String assignedReferralCode) {
        this.assignedReferralCode = assignedReferralCode;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Object> getFavoriteAddresses() {
        return favoriteAddresses;
    }

    public void setFavoriteAddresses(List<Object> favoriteAddresses) {
        this.favoriteAddresses = favoriteAddresses;
    }

    @Override
    public String toString() {
        return
                "Users{" +
                        "dialing_code = '" + dialingCode + '\'' +
                        ",assigned_referral_code = '" + assignedReferralCode + '\'' +
                        ",image = '" + image + '\'' +
                        ",user_id = '" + userId + '\'' +
                        ",last_name = '" + lastName + '\'' +
                        ",phone_number = '" + phoneNumber + '\'' +
                        ",ref_code = '" + refCode + '\'' +
                        ",first_name = '" + firstName + '\'' +
                        ",email = '" + email + '\'' +
                        "}";
    }

}