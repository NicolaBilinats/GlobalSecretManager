package com.secret.DAO;

import com.secret.domain.ApiRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by nicola on 23.10.17.
 */
@Repository
public class ApiRequestDao {
    @PersistenceContext
    private EntityManager entityManager;


    public void create(ApiRequest apiRequest) {
        entityManager.persist(apiRequest);
    }


    public void update(ApiRequest apiRequest) {
        entityManager.merge(apiRequest);
    }


    public ApiRequest getApiRequestById(long id) {
        return entityManager.find(ApiRequest.class, id);
    }


    public void delete(long id) {
        ApiRequest apiRequest = getApiRequestById(id);
        if (apiRequest != null) {
            entityManager.remove(apiRequest);
        }
    }
}
