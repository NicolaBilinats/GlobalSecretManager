package com.secret.repositories;

import com.secret.domain.GlobalSecret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by nicola on 23.10.17.
 */
public interface GlobalSercretRepo extends JpaRepository<GlobalSecret, Integer> {
}
