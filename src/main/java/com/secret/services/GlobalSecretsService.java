package com.secret.services;

import com.secret.domain.GlobalSecret;

/**
 * Created by nicola on 06.10.17.
 */
public interface GlobalSecretsService {

    Iterable<GlobalSecret> listAllSecrets();

    GlobalSecret getSecretById(Integer id);

    GlobalSecret saveSecret(GlobalSecret globalSecret);
}

