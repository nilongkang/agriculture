package com.nlk.agriculture.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class SysTopicFc {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String comment_word;
    private String comment_image_url;
    private String add_time;

    protected SysTopicFc(){}

    public SysTopicFc(String username, String comment_word, String comment_image_url, String add_time){
        this.username = username;
        this.comment_word = comment_word;
        this.comment_image_url = comment_image_url;
        this.add_time = add_time;
    }

    @OneToMany(cascade={CascadeType.ALL})
    @JoinTable(name="sys_topic_fc_sc",
            joinColumns={@JoinColumn(name="sys_topic_fc_id")},
            inverseJoinColumns={@JoinColumn(name="sys_topic_sc_id")}
    )
    private List<SysTopicSc> Scs;

    public List<SysTopicSc> Scs() {
        return Scs;
    }

    public void setSysTopicScs(List<SysTopicSc> Scs) {
        this.Scs = Scs;
    }

    public List<SysTopicSc> getScs() {
        return Scs;
    }

    public void setScs(List<SysTopicSc> scs) {
        Scs = scs;
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
