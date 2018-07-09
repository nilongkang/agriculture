package com.nlk.agriculture.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SysSecondComment {
    @Id
    @GeneratedValue

    private Long id;
    private Long comment_id;
    private String reply_name;
    private String reply_target;
    private Integer reply_type;
    private String comment;
    private String add_time;

    protected SysSecondComment(){}

    public SysSecondComment(Long comment_id, String reply_name, String reply_target, Integer reply_type, String comment, String add_time){
        this.comment_id = comment_id;
        this.reply_name = reply_name;
        this.reply_target = reply_target;
        this.reply_type = reply_type;
        this.comment = comment;
        this.add_time = add_time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }

    public String getReply_name() {
        return reply_name;
    }

    public void setReply_name(String reply_name) {
        this.reply_name = reply_name;
    }

    public String getReply_target() {
        return reply_target;
    }

    public void setReply_target(String reply_target) {
        this.reply_target = reply_target;
    }

    public Integer getReply_type() {
        return reply_type;
    }

    public void setReply_type(Integer reply_type) {
        this.reply_type = reply_type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
