package com.secret.controllers;

import com.secret.bootstrap.Consumer;
import com.secret.domain.GlobalSecret;
import com.secret.domain.Repository;
import com.secret.domain.Secret;
import com.secret.services.GlobalSecretService;
import com.secret.services.RepositoryService;
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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;

/**
 * Created by nicola on 06.10.17.
 */
@Controller
public class GlobalController {

    @Value("${token}")
    private String token;

    @Value("${url.repos}")
    private String URL_;

    private GlobalSecretService globalSecretsService;

    private RepositoryService repositoryService;

    @Autowired
    public void setGlobalSecretsService(GlobalSecretService globalSecretsService) {
        this.globalSecretsService = globalSecretsService;
    }

    @Autowired
    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
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

    @RequestMapping(value = "global", method = RequestMethod.POST)
    public String saveGlobalSecret(GlobalSecret globalSecret) {
        List<Repository> repToCreate = new ArrayList<Repository>((Collection<? extends Repository>) repositoryService.listAllRepos());
        ConcurrentLinkedQueue<GlobalSecret> queue = new ConcurrentLinkedQueue<>();
        queue.add(globalSecret);
        Thread consumer = new Thread(new Consumer(queue));
        for (int i = 0; i < queue.size(); i++) {
            GlobalSecret secretFromQueue = queue.peek();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(secretFromQueue.toString(), headers);
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            for (Repository list : repositoryService.listAllRepos()){
                for (Secret l : Arrays.asList(restTemplate
                        .exchange(URL_.concat(list.getOwner()).concat("/").concat(list.getName().concat("/secrets/")), GET, entity, Secret[].class)
                        .getBody())){
                    if (l.getName().equals(secretFromQueue.getName())){
                        repToCreate.remove(list);
                        break;
                    }
                }
            }
            try {
                repToCreate.forEach(
                        k ->  globalSecret.setStatus(restTemplate
                                .postForEntity(URL_.concat(k.getOwner()).concat("/").concat(k.getName()).concat("/secrets"), request, String.class)
                                .getStatusCode()
                                .value())
                );
                if (globalSecret.getStatus()==200){
                    globalSecretsService.saveGlobalSecret(globalSecret);
                    consumer.start();
                } else {
                    System.out.println("ERROR");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/globals";
    }

    @RequestMapping("globals/{id}/{secretName}")
    public String deleteGlobalSecret(@PathVariable Integer id, @PathVariable String secretName){
        List<Repository> repToDelete = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        for (Repository list : repositoryService.listAllRepos()){
          for (Secret l : Arrays.asList(restTemplate
                  .exchange(URL_.concat(list.getOwner()).concat("/").concat(list.getName().concat("/secrets/")), GET, entity, Secret[].class)
                  .getBody())){
              if (l.getName().equals(secretName)){
                  repToDelete.add(list);
                  System.out.println("Repo added to list for deleting: " + list);
              }
          }
        }
        repToDelete.forEach(
                    k ->  restTemplate
                            .exchange(URL_.concat(k.getOwner()).concat("/").concat(k.getName()).concat("/secrets/").concat(secretName),
                                    DELETE,
                                    entity,
                                    String.class)
            );
        globalSecretsService.deleteGlobalSecretById(id);
        return "redirect:/globals";
    }
}
