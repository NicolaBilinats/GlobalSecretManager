package com.secret.services;

import com.secret.domain.GlobalSecret;
import com.secret.repositories.GlobalSecretRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nicola on 06.10.17.
 */

@Service
public class GlobalSecretsServiceImpl implements GlobalSecretsService{


    private GlobalSecretRepo globalSecretRepo;

    @Autowired
    public void setGlobalSecretRepo(GlobalSecretRepo globalSecretRepo){
        this.globalSecretRepo = globalSecretRepo;
    }

    @Override
    public Iterable<GlobalSecret> listAllSecrets() {
        return globalSecretRepo.findAll();
    }

    @Override
    public GlobalSecret getSecretById(Integer id) {
        return globalSecretRepo.findOne(id);
    }

    @Override
    public GlobalSecret saveSecret(GlobalSecret globalSecret) {
        return globalSecretRepo.save(globalSecret);
    }
}