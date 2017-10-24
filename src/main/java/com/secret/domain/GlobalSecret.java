package com.secret.domain;

import javax.persistence.*;

/**
 * Created by nicola on 23.10.17.
 */
@Entity
@Table(name = "global_secret")
public class GlobalSecret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String[] event;

    public GlobalSecret () {
    }

    public GlobalSecret (Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getEvent() {
        return event;
    }

    public void setEvent(String[] event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", name='" + name+ '\'' +
                ", event='" + event+ '\'' +
                '}';
    }
}
