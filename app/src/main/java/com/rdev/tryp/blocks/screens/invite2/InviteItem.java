package com.rdev.tryp.blocks.screens.invite2;

import androidx.annotation.DrawableRes;

/**
 * Created by Alexey Matrosov on 02.03.2019.
 */
public class InviteItem {
    public enum Status { NotInvited, Invited, Followed }

    private @DrawableRes int icon; // Replace to url (in real code)
    private String name;
    private String number;
    private Status status;

    public InviteItem(int icon, String name, String number, Status status) {
        this.icon = icon;
        this.name = name;
        this.number = number;
        this.status = status;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public Status getStatus() {
        return status;
    }
}
