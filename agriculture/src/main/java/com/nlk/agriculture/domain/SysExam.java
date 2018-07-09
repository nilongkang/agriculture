package com.nlk.agriculture.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SysExam {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String score;
    private String exam_time;

    protected SysExam(){}

    public SysExam(String username, String score, String exam_time){
        this.username = username;
        this.score = score;
        this.exam_time = exam_time;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getExam_time() {
        return exam_time;
    }

    public void setExam_time(String exam_time) {
        this.exam_time = exam_time;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
