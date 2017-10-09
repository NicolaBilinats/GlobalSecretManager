package com.secret.controllers;

import com.secret.bootstrap.UsersLoader;
import com.secret.domain.User;
import com.secret.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by nicola on 09.10.17.
 */
@Controller
public class UsersController {

//    UsersLoader usersLoader = new UsersLoader();

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "users", method = RequestMethod.GET)
    public String list(Model model) {
//        usersLoader.getReq();
        model.addAttribute("users", userService.listAllUsers());
        List<User> list = (List<User>) userService.listAllUsers();
        list.forEach(i -> {
            System.out.println("List: " + i.toString());
        });
        return "users";
    }


}
