package com.example.simona.healthquest.model;

/**
 * Created by Simona on 7/17/2017.
 */

public class QuestionImage extends BaseEntity {
    private Question question;
    private String imageUrl;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
