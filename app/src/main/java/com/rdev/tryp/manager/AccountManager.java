package com.rdev.tryp.manager;

/**
 * Created by Alexey Matrosov on 06.03.2019.
 */
public class AccountManager {
    private static AccountManager instance;

    private String userId = "20"; // TODO: replace to rea;

    public static AccountManager getInstance() {
        if (instance == null)
            instance = new AccountManager();

        return instance;
    }

    public String getUserId() {
        return userId;
    }
}
