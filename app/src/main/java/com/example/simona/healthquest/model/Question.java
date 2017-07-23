package com.example.simona.healthquest.model;

import com.example.simona.healthquest.enumeration.QuestionType;

/**
 * Created by Simona on 7/17/2017.
 */

public class Question extends BaseEntity {
    private String question;
    private Level level;
    private Disease disease;
    private QuestionType questionType;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }
}
