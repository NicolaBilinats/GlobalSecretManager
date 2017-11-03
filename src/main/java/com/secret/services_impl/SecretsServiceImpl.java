package com.secret.services_impl;

import com.secret.domain.Secret;
import com.secret.repositories.SecretRepo;
import com.secret.services.SecretsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nicola on 06.10.17.
 */

@Service
public class SecretsServiceImpl implements SecretsService {


    private SecretRepo secretRepo;

    @Autowired
    public void setSecretRepo(SecretRepo secretRepo){
        this.secretRepo = secretRepo;
    }

    @Override
    public Iterable<Secret> listAllSecrets() {
        return secretRepo.findAll();
    }

    @Override
    public Secret getSecretById(Integer id) {
        return secretRepo.findOne(id);
    }

    @Override
    public Secret saveSecret(Secret secret) {
        return secretRepo.save(secret);
    }

    @Override
    public void cleanSecret() {
        secretRepo.deleteAll();
    }

//    @Override
//    public long getLenght() {
//        return secretRepo.count();
//    }
}