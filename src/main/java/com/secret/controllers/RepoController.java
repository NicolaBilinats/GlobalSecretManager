package com.secret.controllers;

import com.secret.services.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by nicola on 10.10.17.
 */

@Controller
public class RepoController {


    private RepositoryService repositoryService;

    @Autowired
    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getRepos(Model model){
        model.addAttribute("repos", repositoryService.listAllRepos());
        return "repos";
    }


}
