package com.example.simona.healthquest.util;

import java.util.regex.Pattern;

/**
 * Created by Simona on 7/17/2017.
 */

public class Validate {

    private static Validate instance = null;
    private static Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}.,:;/\"\'\\[\\]~-]");
    private static Pattern digit = Pattern.compile("[0-9]");
    private static Pattern letters = Pattern.compile("[A-Za-z]");

    public static Validate getInstance(){
        if(instance == null){
            instance = new Validate();
        }
        return instance;
    }

    public Boolean validateEmail(String email){
        //return !email.equals("") && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        return true;
    }

    public Boolean validatePassword(String password){
        //return !password.equals("");
        return true;
    }
}
