package com.example.simona.healthquest.model;

import java.util.Date;

/**
 * Created by Simona on 7/17/2017.
 */

public class UserQuestion extends BaseEntity {
    private User user;
    private Question question;
    private QuestionAnswer questionAnswer;
    private AnswerImage answerImage;
    private Date openedAt;
    private Date answeredAt;
    private boolean win;
    private int points;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public QuestionAnswer getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(QuestionAnswer questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public Date getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(Date openedAt) {
        this.openedAt = openedAt;
    }

    public Date getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(Date answeredAt) {
        this.answeredAt = answeredAt;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public AnswerImage getAnswerImage() {
        return answerImage;
    }

    public void setAnswerImage(AnswerImage answerImage) {
        this.answerImage = answerImage;
    }
}
