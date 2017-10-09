package com.secret.controllers;

import com.secret.domain.GlobalSecret;
import com.secret.services.GlobalSecretsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by nicola on 06.10.17.
 */
@Controller
public class GlobalController {

    private GlobalSecretsService globalSecretsService;

    @Autowired
    public void setGlobalSecretsService(GlobalSecretsService globalSecretsService) {
        this.globalSecretsService = globalSecretsService;
    }

    @RequestMapping(value = "globals", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("globals", globalSecretsService.listAllSecrets());
        List<GlobalSecret> list = (List<GlobalSecret>) globalSecretsService.listAllSecrets();
        list.forEach(i -> {
            System.out.println("List: " + i.toString());
        });
        return "globals";
    }

    @RequestMapping("global/{id}")
    public String showGlobalSecret(@PathVariable Integer id, Model model) {
        model.addAttribute("global", globalSecretsService.getSecretById(id));
        return "globalshow";
    }

    @RequestMapping("global/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("global", globalSecretsService.getSecretById(id));
        return "globalform";
    }

    @RequestMapping("global/new")
    public String newProduct(Model model) {
        model.addAttribute("global", new GlobalSecret());
        return "globalform";
    }

    @RequestMapping(value = "global", method = RequestMethod.POST)
    public String saveGlobalSecret(GlobalSecret globalSecret) {

        globalSecretsService.saveSecret(globalSecret);

        return "redirect:/global/" + globalSecret.getId();
    }
}
