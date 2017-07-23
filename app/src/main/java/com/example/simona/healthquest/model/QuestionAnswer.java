package com.example.simona.healthquest.model;

/**
 * Created by Simona on 7/17/2017.
 */

public class QuestionAnswer extends BaseEntity {
    private Question question;
    private Answer answer;
    private boolean status;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
