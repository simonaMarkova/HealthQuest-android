package com.example.simona.healthquest.persistance;

import java.util.Set;

/**
 * Created by Simona on 8/2/2017.
 */

public class Persistence {

    private boolean init = false;

    public static final String KEY_USER = "user";

    private static Persistence instance = null;

    private IPersistence persistence = null;

    private Persistence() {
    }

    public void init(IPersistence persistence) {
        if(!init) {
            this.persistence = persistence;
            init = true;
        }
    }

    public static Persistence getInstance() {
        if (instance == null) {
            instance = new Persistence();
        }
        return instance;
    }

    public IPersistence getPersistence() {
        return persistence;
    }

    public static boolean setString(String key, String value) {
        return getInstance().getPersistence().setString(key, value);
    }

    public static String getString(String key, String defaultValue) {
        return getInstance().getPersistence().getString(key, defaultValue);
    }

    public static boolean removeValueForKey(String key){
        return getInstance().getPersistence().removeValueForKey(key);
    }

    public static boolean doesKeyExist(String key){
        return getInstance().getPersistence().doesKeyExist(key);
    }
}
