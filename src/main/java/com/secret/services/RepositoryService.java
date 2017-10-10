package com.secret.services;

import com.secret.domain.Repository;
import com.secret.domain.Secret;

/**
 * Created by nicola on 10.10.17.
 */
public interface RepositoryService {
    Iterable<Repository> listAllRepos();
    Repository getRepoById(Integer id);
}
