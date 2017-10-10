package com.secret.controllers;

import com.secret.domain.Secret;
import com.secret.services.SecretsService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by nicola on 10.10.17.
 */

@Controller
public class SecretController {
    @Value("${token}")
    private String token;

    private Logger log = Logger.getLogger(String.valueOf(RepoController.class));

    private SecretsService secretsService;

    @Autowired
    public void setSecretsService(SecretsService secretsService) {
        this.secretsService = secretsService;
    }


    @RequestMapping(value = "repos/{owner}/{name:.+}", method = RequestMethod.GET)
    public String getSecrets(@PathVariable String owner, @PathVariable String name, Model model) {
        secretsService.cleanSecret();
        List<Secret> response = null;
        String url = "https://drone.ts-t.ru/api/repos/".concat(owner).concat("/").concat(name).concat("/secrets");
        log.info("Message: " + owner + " " + name);
        log.info("URL: " + url);
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(url);
            response = Arrays.asList(webResource.accept("application/json")
                    .header("Authorization", token)
                    .get(Secret[].class));
            log.info("response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.forEach(i -> {
            Secret secret = new Secret();
            secret.setId(i.getId());
            secret.setName(i.getName());
            secret.setEvent(i.getEvent());
            secretsService.saveSecret(secret);
        });


        model.addAttribute("secrets", secretsService.listAllSecrets());
        return "secrets";
    }

    @RequestMapping(value = "repos/{owner}/{name:.+}/{secretName}", method = RequestMethod.DELETE)
    public String deleteSecret(@PathVariable String owner,@PathVariable String name,@PathVariable String secretName) {
        String url = "https://drone.ts-t.ru/api/repos/".concat(owner).concat("/").concat(name).concat("/secrets/".concat(secretName));
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(url);
            ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
                    .header("Authorization", token)
                    .delete(ClientResponse.class);
            log.info("response2: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/repos/";
    }

//    @RequestMapping("global/edit/{id}")
//    public String edit(@PathVariable Integer id, Model model) {
//        model.addAttribute("global", SecretsService.getSecretById(id));
//        return "globalform";
//    }

//    @RequestMapping("global/new")
//    public String newProduct(Model model) {
//        model.addAttribute("global", new Secret());
//        return "globalform";
//    }

//    @RequestMapping(value = "global", method = RequestMethod.POST)
//    public String saveGlobalSecret(Secret globalSecret) {
//
//        SecretsService.saveSecret(globalSecret);
//
//        return "redirect:/global/" + globalSecret.getId();
//    }
}
