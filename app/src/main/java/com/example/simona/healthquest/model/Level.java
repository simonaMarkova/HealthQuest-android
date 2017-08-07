package com.example.simona.healthquest.model;

/**
 * Created by Simona on 7/17/2017.
 */

public class Level extends BaseEntity{
    private int level;
    private int xp;
    private int maxPoints;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }
}
