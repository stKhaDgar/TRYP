package com.rdev.tryp.ui_only.screens.notifications;

import androidx.annotation.DrawableRes;

/**
 * Created by Alexey Matrosov on 03.03.2019.
 */
public class NotificationItem {
    enum Type { Normal, Promo }

    private Type type;
    private String time; // Replace to timestamp in real code

    // Normal fields
    private @DrawableRes int userIcon;
    private String name;
    private String message;

    // Promo fields
    private @DrawableRes int phomoImage;
    private String title;
    private String description;
    private String code;

    public static NotificationItem createNormalNotification(String time, @DrawableRes int userIcon, String name, String message) {
        NotificationItem item = new NotificationItem();
        item.type = Type.Normal;
        item.time = time;
        item.userIcon = userIcon;
        item.name = name;
        item.message = message;

        return item;
    }

    public static NotificationItem createPromoNotification(String time, @DrawableRes int promoIcon, String title, String description, String code) {
        NotificationItem item = new NotificationItem();
        item.type = Type.Promo;
        item.time = time;
        item.phomoImage = promoIcon;
        item.title = title;
        item.description = description;
        item.code = code;

        return item;
    }

    public Type getType() {
        return type;
    }

    public String getTime() {
        return time;
    }

    public int getUserIcon() {
        return userIcon;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public int getPhomoImage() {
        return phomoImage;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }
}
