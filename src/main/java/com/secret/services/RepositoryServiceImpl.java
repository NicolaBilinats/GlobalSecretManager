package com.secret.services;

import com.secret.domain.Repository;
import com.secret.repositories.RepositoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nicola on 10.10.17.
 */

@Service
public class RepositoryServiceImpl implements RepositoryService{

    private RepositoryRepo repositoryRepo;

    @Autowired
    public void setRepositoryRepo(RepositoryRepo repositoryRepo) {
        this.repositoryRepo = repositoryRepo;
    }

    @Override
    public Iterable<Repository> listAllRepos() {
        return  repositoryRepo.findAll();
    }

    @Override
    public Repository getRepoById(Integer id) {
        return repositoryRepo.findOne(id);
    }
}
