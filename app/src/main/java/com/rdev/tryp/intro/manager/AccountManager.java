package com.rdev.tryp.intro.manager;

import com.rdev.tryp.utils.PreferenceManager;

/**
 * Created by Alexey Matrosov on 06.03.2019.
 */
public class AccountManager {
    private static AccountManager instance;
    private final static String USER_ID_KEY = "USER_ID_KEY";

    private String userId = "20"; // TODO: replace to rea;

    public static AccountManager getInstance() {
        if (instance == null)
            instance = new AccountManager();
        instance.userId = PreferenceManager.getString("USER_ID_KEY");
        return instance;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isUserSignIn(){
        if(userId != null){
            return true;
        } else{
            return false;
        }
    }

    public void signIn(String userId){
        this.userId = "20";
        //TODO: replace with real
        PreferenceManager.setString(USER_ID_KEY, userId);
    }

    public void signOut(){
        userId = null;
        PreferenceManager.setString(USER_ID_KEY, userId);
    }
}
