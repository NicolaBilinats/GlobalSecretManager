package com.secret.controllers;

import com.secret.domain.Secret;
import com.secret.services.SecretsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;

/**
 * Created by nicola on 10.10.17.
 */

@Controller
public class SecretController {
    @Value("${token}")
    private String token;

    @Value("${url.repos}")
    private String URL_;

    private String url;

    private String apiUrl;

    private SecretsService secretsService;

    @Autowired
    public void setSecretsService(SecretsService secretsService) {
        this.secretsService = secretsService;
    }


    @RequestMapping(value = "repos/{owner}/{name:.+}", method = RequestMethod.GET, produces = "application/json")
    public String getSecrets(@PathVariable String owner, @PathVariable String name, Model model) {
        List<Secret> result;
        secretsService.cleanSecret();
        url = URL_.concat(owner).concat("/").concat(name).concat("/secrets");
        apiUrl = "/repos/".concat(owner).concat("/").concat(name);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        result = Arrays.asList(restTemplate.exchange(url, GET, entity, Secret[].class).getBody());
        result.forEach(i -> {
            Secret secret = new Secret.Builder().setId(i.getId()).setName(i.getName()).setEvent(i.getEvent()).build();
            secretsService.saveSecret(secret);
        });
        model.addAttribute("secrets", secretsService.listAllSecrets());
        model.addAttribute("repos", name);
        model.addAttribute("url", url);
        model.addAttribute("secret", new Secret());
        return "secrets";
    }

    @RequestMapping("repos/{owner}/{name:.+}/{secretName}")
    public String deleteSecret(@PathVariable String owner, @PathVariable String name, @PathVariable String secretName) {
        String url = URL_.concat(owner).concat("/").concat(name).concat("/secrets/".concat(secretName));
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        restTemplate.exchange(url, DELETE, entity, String.class);
        return "redirect:/repos/".concat(owner).concat("/").concat(name);
    }


    @RequestMapping(value = "secret", method = RequestMethod.POST)
    public String postSecret(Secret secret) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(secret.toString(), headers);
        try {
            restTemplate.postForEntity(url, request, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:".concat(apiUrl);
    }

}
