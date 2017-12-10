package com.example.simona.healthquest.helper;

import com.example.simona.healthquest.model.QuestionAnswer;

import java.util.List;

/**
 * Created by Simona on 12/10/2017.
 */

public class BonusQuestion {
    Long id;
    String question;
    List<QuestionAnswer> answerList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<QuestionAnswer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<QuestionAnswer> answerList) {
        this.answerList = answerList;
    }
}
