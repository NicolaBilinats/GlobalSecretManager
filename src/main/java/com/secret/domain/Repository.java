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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Repository that = (Repository) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return timeout != null ? timeout.equals(that.timeout) : that.timeout == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (timeout != null ? timeout.hashCode() : 0);
        return result;
    }
}
