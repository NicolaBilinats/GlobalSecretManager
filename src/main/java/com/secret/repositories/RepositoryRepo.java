package com.secret.repositories;

import com.secret.domain.Repository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by nicola on 09.10.17.
 */
public interface RepositoryRepo extends CrudRepository<Repository, Integer> {
}
