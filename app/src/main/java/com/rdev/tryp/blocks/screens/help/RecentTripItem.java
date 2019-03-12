package com.rdev.tryp.blocks.screens.help;

import androidx.annotation.DrawableRes;

/**
 * Created by Alexey Matrosov on 04.03.2019.
 */
public class RecentTripItem {
    private @DrawableRes int userIcon;
    private boolean isActive;
    private String date;
    private String message;
    private String amount;
    private String distance;

    public RecentTripItem(int userIcon, boolean isActive, String date, String message, String amount, String distance) {
        this.userIcon = userIcon;
        this.isActive = isActive;
        this.date = date;
        this.message = message;
        this.amount = amount;
        this.distance = distance;
    }

    public int getUserIcon() {
        return userIcon;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public String getAmount() {
        return amount;
    }

    public String getDistance() {
        return distance;
    }
}
