package com.secret.repositories;

import com.secret.domain.Secret;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by nicola on 06.10.17.
 */

public interface SecretRepo extends CrudRepository<Secret, Integer> {
}

