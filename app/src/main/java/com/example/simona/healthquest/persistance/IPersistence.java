package com.example.simona.healthquest.persistance;

import java.util.Set;

/**
 * Created by Simona on 8/2/2017.
 */

public interface IPersistence {


    public boolean setString(String key, String value);

    public String getString(String key, String defaultValue);

    public boolean removeValueForKey(String key);

    public boolean doesKeyExist(String key);
}
