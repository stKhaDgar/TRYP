package com.rdev.tryp.model.sign_up_response;

import com.google.gson.annotations.SerializedName;

public class Users {

    @SerializedName("dialing_code")
    private String dialingCode;

    @SerializedName("assigned_referral_code")
    private String assignedReferralCode;

    @SerializedName("image")
    private String image;

    @SerializedName("password")
    private String password;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("ref_code")
    private String refCode;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("email")
    private String email;

    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }

    public String getDialingCode() {
        return dialingCode;
    }

    public void setAssignedReferralCode(String assignedReferralCode) {
        this.assignedReferralCode = assignedReferralCode;
    }

    public String getAssignedReferralCode() {
        return assignedReferralCode;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return
                "Users{" +
                        "dialing_code = '" + dialingCode + '\'' +
                        ",assigned_referral_code = '" + assignedReferralCode + '\'' +
                        ",image = '" + image + '\'' +
                        ",password = '" + password + '\'' +
                        ",last_name = '" + lastName + '\'' +
                        ",phone_number = '" + phoneNumber + '\'' +
                        ",ref_code = '" + refCode + '\'' +
                        ",first_name = '" + firstName + '\'' +
                        ",email = '" + email + '\'' +
                        "}";
    }
}