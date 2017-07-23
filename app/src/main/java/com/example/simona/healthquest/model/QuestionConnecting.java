package com.example.simona.healthquest.model;

/**
 * Created by Simona on 7/17/2017.
 */

public class QuestionConnecting extends BaseEntity{
    private Question question;
    private String phraseOne;
    private String phraseTwo;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getPhraseOne() {
        return phraseOne;
    }

    public void setPhraseOne(String phraseOne) {
        this.phraseOne = phraseOne;
    }

    public String getPhraseTwo() {
        return phraseTwo;
    }

    public void setPhraseTwo(String phraseTwo) {
        this.phraseTwo = phraseTwo;
    }
}
