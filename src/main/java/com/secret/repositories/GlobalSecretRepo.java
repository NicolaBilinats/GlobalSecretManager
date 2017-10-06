package com.secret.repositories;

import com.secret.domain.GlobalSecret;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by nicola on 06.10.17.
 */

public interface GlobalSecretRepo extends CrudRepository<GlobalSecret, Integer> {
}

