package com.secret.services;

import com.secret.DAO.ApiRequestDao;
import com.secret.domain.ApiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by nicola on 23.10.17.
 */
@Service
@Transactional
public class ApiRequestService {

    @Autowired
    private ApiRequestDao apiRequestDao;

    public void create(ApiRequest apiRequest) {
        apiRequestDao.create(apiRequest);
    }
}
