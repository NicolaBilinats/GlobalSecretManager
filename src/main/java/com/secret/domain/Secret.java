package com.secret.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by nicola on 06.10.17.
 */

@Entity
public class Secret {

    @Id
    private Integer id;
    private String name;
    private String value;
    private String[] event;

    public Secret(Integer id, String name, String[] event) {
        this.id = id;
        this.name = name;
        this.event = event;
    }

    public Secret() {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String[] getEvent() {
        return event;
    }

    public void setEvent(String[] event) {
        this.event = event;
    }
    public String transformEvent(String[] event){
        return Arrays.toString(event);
    }


    public String transformStr(String str){
        StringBuilder builder = new StringBuilder();
        String[] a = str.replaceAll("\\[|\\]", "").split("[,]");
        for (int i = 0; i < a.length; i++) {
            if (i < a.length-1){
                builder.append("\"".concat(a[i]).concat("\"").concat(","));
            }else {
                builder.append("\"".concat(a[i]).concat("\""));
            }
        }
        return "[".concat(String.valueOf(builder)).concat("]");
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":"+'\"' + name+ '\"' +
                ",\"value\":"+'\"' + value+ '\"' +
                ",\"event\":" + transformStr(Arrays.toString(event))+
                '}';
    }

}


