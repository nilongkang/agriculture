package com.nlk.agriculture.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class SysUserInfo implements Serializable{
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String address;
    private String birth;
    private String sex;
    private String user_imageurl;

    protected SysUserInfo(){}

    public SysUserInfo(String username,String address, String birth, String sex, String user_imageurl){
        this.username = username;
        this.address = address;
        this.birth = birth;
        this.sex = sex;
        this.user_imageurl = user_imageurl;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUser_imageurl(String user_imageurl) {
        this.user_imageurl = user_imageurl;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUser_imageurl() {
        return user_imageurl;
    }

    public String getAddress() {
        return address;
    }

    public String getBirth() {
        return birth;
    }

    public String getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
