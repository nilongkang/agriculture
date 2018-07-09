package com.nlk.agriculture.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SysBank {
    @Id
    @GeneratedValue

    private long id;
    private String username;
    private String question;
    private String answer;
    private String add_time;

    protected SysBank(){}

    public SysBank(String username, String question, String answer, String add_time){
        this.username = username;
        this.answer=answer;
        this.question=question;
        this.add_time=add_time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
