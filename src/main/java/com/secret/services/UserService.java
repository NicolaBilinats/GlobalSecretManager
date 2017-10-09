package com.secret.services;

import com.secret.domain.User;

/**
 * Created by nicola on 09.10.17.
 */
public interface UserService {
    Iterable<User> listAllUsers();
    User getUserById(Integer id);
}
