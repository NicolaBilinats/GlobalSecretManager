package com.secret.domain;

import javax.persistence.*;
import java.util.Arrays;

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
    private String value;
    private String[] event;
    private Integer status;

    public GlobalSecret () {
    }

    public GlobalSecret (Integer id) {
        this.id = id;
    }

    public GlobalSecret(Integer id, String name, String[] event) {
        this.id = id;
        this.name = name;
        this.event = event;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public static class Builder {
        private Integer id;
        private String name;
        private String[] event;

        public Builder(){

        }

        public Builder setId(Integer id){
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEvent(String[] event) {
            this.event = event;
            return this;
        }

        public GlobalSecret build(){
            return new GlobalSecret(id, name, event);
        }
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
