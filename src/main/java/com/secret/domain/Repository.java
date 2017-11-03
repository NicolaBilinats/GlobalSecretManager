package com.secret.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by nicola on 09.10.17.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Repository {
    @Id
    private Integer id;
    private String owner;
    private String name;
    private Integer timeout;

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
