package com.secret.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by nicola on 09.10.17.
 */

@Entity
public class Repository {
    @Id
    private Integer id;
    private String fullname;
    private Integer timeout;
    private String link_url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", fullname='" + fullname + '\'' +
                ", timeout='" + timeout + '\'' +
                ", link_url='" + link_url + '\'' +
                '}';
    }
}
