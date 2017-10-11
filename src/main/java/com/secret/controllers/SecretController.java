package com.secret.controllers;

import com.secret.domain.Secret;
import com.secret.services.RepositoryService;
import com.secret.services.SecretsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

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


    @RequestMapping(value = "repos/{owner}/{name:.+}", method = RequestMethod.GET, produces = "application/json")
    public String getSecrets(@PathVariable String owner, @PathVariable String name, Model model) {
        String[] e = {"Push", "Tag", "Deployment", "Pull Request"};
        secretsService.cleanSecret();
        String url = "https://drone.ts-t.ru/api/repos/".concat(owner).concat("/").concat(name).concat("/secrets");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        List<Secret> result = Arrays.asList(restTemplate.exchange(url, GET, entity, Secret[].class).getBody());
        result.forEach(i -> {
            Secret secret = new Secret();
            secret.setId(i.getId());
            secret.setName(i.getName());
            secret.setEvent(i.getEvent());
            secretsService.saveSecret(secret);
        });
        model.addAttribute("secrets", secretsService.listAllSecrets());
        model.addAttribute("repos", name);
        model.addAttribute("event", e);
        return "secrets";
    }

    @RequestMapping("repos/{owner}/{name:.+}/{secretName}")
    public String deleteSecret(@PathVariable String owner,@PathVariable String name,@PathVariable String secretName) {
        String url = "https://drone.ts-t.ru/api/repos/".concat(owner).concat("/").concat(name).concat("/secrets/".concat(secretName));
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        restTemplate.exchange(url, DELETE, entity, String.class);
        return "redirect:/repos/".concat(owner).concat("/").concat(name);
    }

//    @RequestMapping()
//    public String createSecret(String owner, String name){
//        return "redirect:/repos/".concat(owner).concat("/").concat(name);
//    }

}
