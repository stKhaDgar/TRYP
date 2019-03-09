package com.rdev.tryp.model;

public class LoginModel {
    private String verification_code;
    private String dialing_code;
    private String phone_number;
    private String type = "LOGIN";

    public LoginModel(UserPhoneNumber number) {
        this.dialing_code = number.getDialing_code();
        this.phone_number = number.getPhone_number();

    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        this.verification_code = verification_code;
    }

    public String getDialing_code() {
        return dialing_code;
    }

    public void setDialing_code(String dialing_code) {
        this.dialing_code = dialing_code;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
