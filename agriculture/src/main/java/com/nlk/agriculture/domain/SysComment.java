package com.nlk.agriculture.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class SysComment implements Serializable{
    @Id
    @GeneratedValue

    private Long id;
    private String username;
    private String comment;

    protected SysComment(){}

    public SysComment(String username, String comment){
        this.username = username;
        this.comment = comment;
    }

    @OneToMany(cascade={CascadeType.ALL})
    List<SysSecondComment> SecondComments;

    public List<SysSecondComment> getSysSecondComments() {
        return SecondComments;
    }

    public void setSysSecondComments(List<SysSecondComment> SecondComments) {
        this.SecondComments = SecondComments;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
