package com.rdev.tryp.model;

public class CreateUser {
    private String first_name;
    private String last_name;
    private String email;
    private String country_code;
    private String dialing_code;
    private String phone_number;
    private String ref_code;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
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

    public String getRef_code() {
        return ref_code;
    }

    public void setRef_code(String ref_code) {
        this.ref_code = ref_code;
    }

    @Override
    public String toString() {
        return "first name " + first_name + "\n"
                + "last name " + last_name + "\n"
                + "email " + email + "\n"
                + "country_code " + country_code + "\n"
                + "dialing_code " + dialing_code + "\n"
                + "phone_number " + phone_number + "\n"
                + "ref_code " + ref_code + "\n";

    }
}
