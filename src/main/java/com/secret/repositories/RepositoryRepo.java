package com.secret.repositories;

import com.secret.domain.GlobalSecret;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by nicola on 09.10.17.
 */
public interface RepositoryRepo extends CrudRepository<GlobalSecret, Integer> {
}
