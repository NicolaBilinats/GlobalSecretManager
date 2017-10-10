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
    private String owner;
    private String name;
    private Integer timeout;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", timeout='" + timeout + '\'' +
                '}';
    }
}
