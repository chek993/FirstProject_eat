package com.example.firstproject_eat;

import android.text.TextUtils;
import android.util.Patterns;

public class Utilities {

    private final static int LEN_PASSWORD = 6;
    private static final int USERNNAME_LENGTH = 4;

    public static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static boolean isValidPassword(String password){
        if(password.length() < LEN_PASSWORD){
            return false;
        }else{
            if(password.equals(password.toLowerCase()) ||
                    password.equals(password.toUpperCase())){
                return false;
            }else{
                return true;
            }
        }
    }

    public static boolean isValidPhone(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return Patterns.PHONE.matcher(email).matches();
        }
    }

    public static boolean isValidUsername(String username) {
        if (TextUtils.isEmpty(username)) {
            return false;
        } else {
            return username.length() > USERNNAME_LENGTH;
        }
    }
}
