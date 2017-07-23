package com.example.simona.healthquest.model;

/**
 * Created by Simona on 7/17/2017.
 */

public class AnswerImage extends BaseEntity{
    private Question question;
    private boolean status;
    private String imageUrl;
    private int number;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
