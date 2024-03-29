package com.azamovhudstc.quizapp.model;

public class QuestionModel {
    private Integer txtQuestion;
    private String answer;
    private String variant;

    public QuestionModel(int txtQuestion, String answer, String variant) {
        this.txtQuestion = txtQuestion;
        this.answer = answer;
        this.variant = variant;
    }

    public Integer getTxtQuestion() {
        return txtQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public String getVariant() {
        return variant;
    }
}
