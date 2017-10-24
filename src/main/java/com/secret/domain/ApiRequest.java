package com.secret.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by nicola on 23.10.17.
 */
@Entity
@Table(name = "api_request")
public class ApiRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="request_time")
    private Date requestTime;

    public ApiRequest() {
    }

    public ApiRequest(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }
}
