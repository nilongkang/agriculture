package com.nlk.agriculture.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SysTopicSc {
    @Id
    @GeneratedValue

    private long id;
    private long comment_id;
    private String reply_name;
    private String reply_target;
    private long reply_type;
    private String comment_word;
    private String comment_image_url;
    private String add_time;

    protected SysTopicSc(){}

    public SysTopicSc(long comment_id, String reply_name,String reply_target, long reply_type, String comment_word, String comment_image_url, String add_time){
        this.comment_id = comment_id;
        this.reply_name = reply_name;
        this.reply_target = reply_target;
        this.reply_type = reply_type;
        this.comment_word = comment_word;
        this.comment_image_url = comment_image_url;
        this.add_time = add_time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getComment_id() {
        return comment_id;
    }

    public void setComment_id(long comment_id) {
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

    public long getReply_type() {
        return reply_type;
    }

    public void setReply_type(long reply_type) {
        this.reply_type = reply_type;
    }

    public String getComment_word() {
        return comment_word;
    }

    public void setComment_word(String comment_word) {
        this.comment_word = comment_word;
    }

    public String getComment_image_url() {
        return comment_image_url;
    }

    public void setComment_image_url(String comment_image_url) {
        this.comment_image_url = comment_image_url;
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
