package com.example.simona.healthquest.persistance;

import java.util.Set;

/**
 * Created by Simona on 8/2/2017.
 */

public interface IPersistence {

    public boolean setBoolean(String key, boolean value);

    public boolean setInteger(String key, int value);

    public boolean setFloat(String key, float value);

    public boolean setLong(String key, long value);

    public boolean setString(String key, String value);

    public boolean setStringSet(String key, Set<String> value);

    public boolean getBoolean(String key, boolean defaultValue);

    public int getInteger(String key, int defaultValue);

    public float getFloat(String key, float defaultValue);

    public long getLong(String key, long defaultValue);

    public String getString(String key, String defaultValue);

    public Set<String> getStringSet(String key, Set<String> defaultValue);

    public boolean removeValueForKey(String key);

    public boolean doesKeyExist(String key);
}
