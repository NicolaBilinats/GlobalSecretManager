package com.secret.services;

import com.secret.domain.Secret;

/**
 * Created by nicola on 06.10.17.
 */
public interface SecretsService {

    Iterable<Secret> listAllSecrets();

    Secret getSecretById(Integer id);

    Secret saveSecret(Secret secret);

    void cleanSecret();

//    long getLenght();
}

