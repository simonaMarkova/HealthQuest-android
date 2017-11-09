package com.example.simona.healthquest.persistance;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

/**
 * Created by Simona on 8/2/2017.
 */

public class SharedPreferencesPersistence implements IPersistence {

    private SharedPreferences sharedPreferences;

    public SharedPreferencesPersistence(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public boolean setString(String key, String value) {
        return sharedPreferences.edit().putString(key, value).commit();
    }

    @Override
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    @Override
    public boolean removeValueForKey(String key) {
        return sharedPreferences.edit().remove(key).commit();
    }

    @Override
    public boolean doesKeyExist(String key) {
        return sharedPreferences.contains(key);
    }
}
