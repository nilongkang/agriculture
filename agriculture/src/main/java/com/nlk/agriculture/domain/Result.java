package com.nlk.agriculture.domain;

public class Result {
    private long id;
    private String answer;

    protected Result(){}

    public Result(long id, String answer){
        this.id =id;
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
