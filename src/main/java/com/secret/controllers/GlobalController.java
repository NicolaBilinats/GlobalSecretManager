package com.secret.controllers;

import com.secret.domain.Secret;
import com.secret.services.SecretsService;
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

    private SecretsService globalSecretsService;

    @Autowired
    public void setGlobalSecretsService(SecretsService globalSecretsService) {
        this.globalSecretsService = globalSecretsService;
    }

    @RequestMapping(value = "globals", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("globals", globalSecretsService.listAllSecrets());
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
        model.addAttribute("global", new Secret());
        return "globalform";
    }

    @RequestMapping(value = "global", method = RequestMethod.POST)
    public String saveGlobalSecret(Secret globalSecret) {

        globalSecretsService.saveSecret(globalSecret);

        return "redirect:/global/" + globalSecret.getId();
    }
}
