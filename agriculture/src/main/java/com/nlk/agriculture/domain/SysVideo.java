package com.nlk.agriculture.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class SysVideo implements Serializable{
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String video_name;
    private String description;
    private String video_img_url;
    private Integer click_num;
    private Integer fav_num;
    private String video_kind;
    private String video_url;
    private String add_time;
    private Integer agree_num;

    protected SysVideo(){}

    public SysVideo(String username, String video_name, String description, String video_img_url, Integer click_num, Integer fav_num, String video_kind, String video_url, String add_time, Integer agree_num) {

        this.username = username;
        this.video_name = video_name;
        this.description = description;
        this.video_img_url = video_img_url;
        this.click_num = click_num;
        this.fav_num = fav_num;
        this.video_kind = video_kind;
        this.video_url = video_url;
        this.add_time = add_time;
        this.agree_num = agree_num;

    }


    @OneToMany(cascade={CascadeType.ALL})
    private List<SysComment> comments;

    public void setComments(List<SysComment> comments) {
        this.comments = comments;
    }

    public List<SysComment> getComments() {
        return comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) { this.username = username; }

    public String getUsername() { return username; }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo_img_url() {
        return video_img_url;
    }

    public void setVideo_img_url(String video_img_url) {
        this.video_img_url = video_img_url;
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

    public String getVideo_kind() {
        return video_kind;
    }

    public void setVideo_kind(String video_kind) {
        this.video_kind = video_kind;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
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

    @Override
    public String toString() {
        return super.toString();
    }
}



