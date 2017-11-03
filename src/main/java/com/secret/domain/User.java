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
public class User {

    private @Id Integer id;
    private String login;
    private String email;
    private String avatar_url;
    private String active;

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
