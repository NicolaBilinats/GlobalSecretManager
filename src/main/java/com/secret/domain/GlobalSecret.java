package com.secret.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Arrays;

/**
 * Created by nicola on 23.10.17.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalSecret {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String value;
    private String[] event;
    private Integer status;


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
