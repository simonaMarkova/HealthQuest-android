package com.example.simona.healthquest.util;

import com.example.simona.healthquest.model.User;

import java.util.Comparator;

/**
 * Created by user on 06.8.2017.
 */

public class PointsComparator implements Comparator<User>
{
    public int compare(User o1, User o2)
    {
        return o1.getPoints().compareTo(o2.getPoints());
    }
}