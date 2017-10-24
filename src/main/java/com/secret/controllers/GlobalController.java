package com.secret.controllers;

import com.secret.domain.GlobalSecret;
import com.secret.services.GlobalSecretService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by nicola on 06.10.17.
 */
@Controller
public class GlobalController {

    private GlobalSecretService globalSecretsService;

    @Autowired
    public void setGlobalSecretsService(GlobalSecretService globalSecretsService) {
        this.globalSecretsService = globalSecretsService;
    }

    @RequestMapping(value = "globals", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("globals", globalSecretsService.listAllGlobalSecrets());
        model.addAttribute("global", new GlobalSecret());
        return "globals";
    }

    @RequestMapping("global/{id}")
    public String showGlobalSecret(@PathVariable Integer id, Model model) {
        model.addAttribute("global", globalSecretsService.getGlobalSecretById(id));
        return "globalshow";
    }

    @RequestMapping("global/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("global", globalSecretsService.getGlobalSecretById(id));
        return "globalform";
    }

//    @RequestMapping(value = "globals", method = RequestMethod.POST)
//    public String newProduct(Model model) {
//        model.addAttribute("global", new GlobalSecret());
//        return "globals";
//    }

    @RequestMapping(value = "global", method = RequestMethod.POST)
    public String saveGlobalSecret(GlobalSecret globalSecret) {

        globalSecretsService.saveGlobalSecret(globalSecret);

        return "redirect:/globals";
    }

    @RequestMapping("globals/{id}")
    public String deleteGlobalSecret(@PathVariable Integer id){
        globalSecretsService.deleteGlobalSecretById(id);
        return "redirect:/globals";
    }
}
