package com.rdev.tryp.intro.manager;

import com.rdev.tryp.utils.PreferenceManager;

/**
 * Created by Alexey Matrosov on 06.03.2019.
 */
public class AccountManager {
    private static AccountManager instance;
    private final static String USER_ID_KEY = "USER_ID_KEY";

    private int userId;

    public static AccountManager getInstance() {
        if (instance == null)
            instance = new AccountManager();
        instance.userId = PreferenceManager.getInt("USER_ID_KEY");
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isUserSignIn(){
        if(userId != -1){
            return true;
        } else{
            return false;
        }
    }

    public void signIn(int userId){
        this.userId = userId;
        PreferenceManager.setInt(USER_ID_KEY, userId);
    }

    public void signOut(){
        userId = -1;
        PreferenceManager.setInt(USER_ID_KEY, userId);
    }
}
