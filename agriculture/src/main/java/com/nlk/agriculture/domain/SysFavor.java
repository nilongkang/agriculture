package com.nlk.agriculture.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class SysFavor {
    @Id
    @GeneratedValue

    private Long id;
    private String username;

    protected SysFavor(){}

    public SysFavor(String username){
        this.username = username;
    }

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="sys_favor_video",
            joinColumns={@JoinColumn(name="favor_id")},
            inverseJoinColumns={@JoinColumn(name="video_id")}
    )

    private List<SysVideo> videos;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="sys_favor_topic",
            joinColumns={@JoinColumn(name="favor_id")},
            inverseJoinColumns={@JoinColumn(name="topic_id")}
    )

    private List<SysTopic> Topics;

    public List<SysTopic> getTopics() {
        return Topics;
    }

    public void setTopics(List<SysTopic> Topics) {
        this.Topics = Topics;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setVideos(List<SysVideo> videos) {
        this.videos = videos;
    }

    public List<SysVideo> getVideos() {
        return videos;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
