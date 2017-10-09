package com.secret.repositories;

import com.secret.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by nicola on 09.10.17.
 */
public interface UsersRepo extends CrudRepository<User,Integer> {
}
