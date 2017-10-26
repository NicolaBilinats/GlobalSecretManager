package com.secret.services;

import com.secret.domain.GlobalSecret;

/**
 * Created by nicola on 23.10.17.
 */
public interface GlobalSecretService {

    Iterable<GlobalSecret> listAllGlobalSecrets();

    GlobalSecret getGlobalSecretById(Integer id);

    GlobalSecret saveGlobalSecret(GlobalSecret secret);

    void cleanGlobalSecret();

    void deleteGlobalSecretById(Integer id);

    long getLenght();
}
