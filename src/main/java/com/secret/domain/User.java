package com.secret.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by nicola on 09.10.17.
 */
//@JsonIgnoreProperties()
@Entity
public class User {
    @Id
//    @JsonProperty("id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @JsonProperty("login")
    private String login;
//    @JsonProperty("email")
    private String email;
//    @JsonProperty("avatar_url")
    private String avatar_url;
//    @JsonProperty("active")
    private String active;

    private String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", login='" + login+ '\'' +
                ", email='" + email+ '\'' +
                ", active='" + active+ '\'' +
                '}';
    }

}
