package com.example.simona.healthquest.model;

/**
 * Created by Simona on 7/17/2017.
 */

public class Answer extends BaseEntity {
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
