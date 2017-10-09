package com.secret.services;

import com.secret.domain.User;
import com.secret.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by nicola on 09.10.17.
 */

@Service
public class UserServiceImpl implements UserService {

    private UsersRepo usersRepo;

    @Autowired
    public void setUsersRepo(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public Iterable<User> listAllUsers() {
        return usersRepo.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return usersRepo.findOne(id);
    }
}
