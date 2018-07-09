package com.nlk.agriculture.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SysFile {
    @Id
    @GeneratedValue

    private Long id;
    private String username;
    private String file_name;
    private String file_url;
    private Integer downloadnum;
    private String add_time;

    protected SysFile(){}

    public SysFile(String username, String file_name, String file_url, Integer downloadnum, String add_time){
        this.username = username;
        this.file_name = file_name;
        this.file_url = file_url;
        this.downloadnum = downloadnum;
        this.add_time = add_time;
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

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public Integer getDownloadnum() {
        return downloadnum;
    }

    public void setDownloadnum(Integer downloadnum) {
        this.downloadnum = downloadnum;
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
