package com.nlk.agriculture.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class SysTopic {
    @Id
    @GeneratedValue

    private long id;
    private String username;
    private String content_title;
    private String content_word;
    private String content_image_url;
    private String add_time;
    private Integer click_num;
    private Integer fav_num;
    private Integer agree_num;

    protected SysTopic(){}

    public SysTopic(String username, String content_title,String content_word, String content_image_url, String add_time, Integer click_num, Integer fav_num, Integer agree_num){
        this.username = username;
        this.content_title = content_title;
        this.content_word = content_word;
        this.content_image_url = content_image_url;
        this.add_time = add_time;
        this.click_num = click_num;
        this.fav_num = fav_num;
        this.agree_num = agree_num;
    }

    @OneToMany(cascade={CascadeType.ALL})

    private List<SysTopicFc> Fcs;

    public List<SysTopicFc> getFcs() {
        return Fcs;
    }

    public void setFcs(List<SysTopicFc> Fcs) {
        Fcs = Fcs;
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

    public Integer getClick_num() {
        return click_num;
    }

    public void setClick_num(Integer click_num) {
        this.click_num = click_num;
    }

    public Integer getFav_num() {
        return fav_num;
    }

    public void setFav_num(Integer fav_num) {
        this.fav_num = fav_num;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent_word() {
        return content_word;
    }

    public void setContent_word(String content_word) {
        this.content_word = content_word;
    }

    public String getContent_image_url() {
        return content_image_url;
    }

    public void setContent_image_url(String content_image_url) {
        this.content_image_url = content_image_url;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public Integer getAgree_num() {
        return agree_num;
    }

    public void setAgree_num(Integer agree_num) {
        this.agree_num = agree_num;
    }

    public String getContent_title() {
        return content_title;
    }

    public void setContent_title(String content_title) {
        this.content_title = content_title;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
