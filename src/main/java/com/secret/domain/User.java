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
@Entity
public class User {
    @Id
    private Integer id;
    private String login;
    private String email;
    private String avatar_url;
    private String active;

    public User(){

    }

    public User(Integer id, String login, String email, String avatar_url, String active) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.avatar_url = avatar_url;
        this.active = active;
    }

    public String getAvatar_url() {
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



    public static class Builder {
        private Integer id;
        private String login;
        private String email;
        private String avatar_url;
        private String active;

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
            return this;
        }

        public Builder setActive(String active) {
            this.active = active;
            return this;
        }

        public User build(){
            return new User(id,login,email,avatar_url,active);
        }
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
