package com.secret.services_impl;

import com.secret.domain.GlobalSecret;
import com.secret.repositories.GlobalSercretRepo;
import com.secret.services.GlobalSecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by nicola on 23.10.17.
 */

@Service
public class GlobalSecretServiceImpl implements GlobalSecretService {

    private GlobalSercretRepo globalSercretRepo;

    @Autowired
    public void setGlobalSercretRepo(GlobalSercretRepo globalSercretRepo){
        this.globalSercretRepo = globalSercretRepo;
    }
    @Override
    public Iterable<GlobalSecret> listAllGlobalSecrets() {
        return globalSercretRepo.findAll();
    }

    @Override
    public GlobalSecret getGlobalSecretById(Integer id) {
        return globalSercretRepo.findOne(id);
    }

    @Override
    public GlobalSecret saveGlobalSecret(GlobalSecret secret) {
        return globalSercretRepo.save(secret);
    }

    @Override
    public void cleanGlobalSecret() {
        globalSercretRepo.deleteAll();
    }

    @Override
    public void deleteGlobalSecretById(Integer id) {
        globalSercretRepo.delete(id);
    }

    @Override
    public long getLenght() {
        return globalSercretRepo.count();
    }
}
