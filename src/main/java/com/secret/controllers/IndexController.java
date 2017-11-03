package com.secret.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by nicola on 06.10.17.
 */
@Controller
public class IndexController {

    @Value("${token}")
    private String token;

    @RequestMapping("/")
    String index(Model model){
        model.addAttribute("token", token);
//        System.out.println("token: " + token);
        return "index";
    }
}