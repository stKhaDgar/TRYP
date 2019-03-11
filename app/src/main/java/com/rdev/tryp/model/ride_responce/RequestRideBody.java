package com.rdev.tryp.model.ride_responce;

import android.location.Address;

import com.rdev.tryp.model.DriversItem;
import com.rdev.tryp.network.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RequestRideBody {
    private String to_lat;
    private String driver_id;
    private String from_lng;
    private String user_id;
    private String from;
    private String to_lng;
    private String to;
    private String pickup_time;
    private String ip_address;
    private String asap;
    private String from_lat;

    private int max_luggage;
    private int max_passenger;
    private String type;
    private String category;
    private double fare;

    public void setTo_lat(String to_lat){
        this.to_lat = to_lat;
    }

    public RequestRideBody(Address from, Address destination, boolean isAsap, DriversItem driver, String userId) {
        from_lng = formatLocation(from.getLongitude());
        from_lat = formatLocation(from.getLatitude());
        to_lng = formatLocation(destination.getLongitude());
        to_lat = formatLocation(destination.getLatitude());
        asap = isAsap ? "1" : "0";
        this.from = getFullAddress(from);
        to = getFullAddress(destination);
        max_luggage = driver.getMaxLuggage();
        max_passenger = driver.getMaxPassenger();
        type = driver.getType();
        category = driver.getCategory();
        fare = driver.getFare();
        pickup_time = getCurrentDate();
        user_id = userId;
        ip_address = Utils.getIPAddress();
    }

    public String getTo_lat(){
        return to_lat;
    }

    public void setDriver_id(String driver_id){
        this.driver_id = driver_id;
    }

    public String getDriver_id(){
        return driver_id;
    }

    public void setFrom_lng(String from_lng){
        this.from_lng = from_lng;
    }

    public String getFrom_lng(){
        return from_lng;
    }

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }

    public String getUser_id(){
        return user_id;
    }

    public void setFrom(String from){
        this.from = from;
    }

    public String getFrom(){
        return from;
    }

    public void setTo_lng(String to_lng){
        this.to_lng = to_lng;
    }

    public String getTo_lng(){
        return to_lng;
    }

    public void setTo(String to){
        this.to = to;
    }

    public String getTo(){
        return to;
    }

    public void setPickup_time(String pickup_time){
        this.pickup_time = pickup_time;
    }

    public String getPickup_time(){
        return pickup_time;
    }

    public void setIp_address(String ip_address){
        this.ip_address = ip_address;
    }

    public String getIp_address(){
        return ip_address;
    }

    public void setAsap(String asap){
        this.asap = asap;
    }

    public String getAsap(){
        return asap;
    }

    public void setFrom_lat(String from_lat){
        this.from_lat = from_lat;
    }

    public String getFrom_lat(){
        return from_lat;
    }

    @Override
    public String toString(){
        return
                "RequideRestBody{" +
                        "to_lat = '" + to_lat + '\'' +
                        ",driver_id = '" + driver_id + '\'' +
                        ",from_lng = '" + from_lng + '\'' +
                        ",user_id = '" + user_id + '\'' +
                        ",from = '" + from + '\'' +
                        ",to_lng = '" + to_lng + '\'' +
                        ",to = '" + to + '\'' +
                        ",pickup_time = '" + pickup_time + '\'' +
                        ",ip_address = '" + ip_address + '\'' +
                        ",asap = '" + asap + '\'' +
                        ",from_lat = '" + from_lat + '\'' +
                        "}";
    }

    private String getFullAddress(Address address) {
        StringBuilder builder = new StringBuilder();

        if (address.getThoroughfare() != null) {
            if (address.getSubThoroughfare() != null)
                builder.append(address.getSubThoroughfare()).append(" ");
            builder.append(address.getThoroughfare());
        } else if (address.getFeatureName() != null) {
            builder.append(address.getFeatureName());
        }

        builder.append(", ").append(address.getLocality());
        builder.append(", ").append(address.getAdminArea());
        builder.append(", ").append(address.getCountryName());

        return builder.toString();
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();

        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(c);
    }

    private String formatLocation(double location) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("0.0###");
        return df.format(location);
    }
}
