package com.secret.controllers;

import com.secret.bootstrap.Consumer;
import com.secret.bootstrap.Producer;
import com.secret.domain.GlobalSecret;
import com.secret.services.GlobalSecretService;
import com.secret.services.RepositoryService;
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
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.springframework.http.HttpMethod.DELETE;

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

//    @RequestMapping(value = "globals", method = RequestMethod.POST)
//    public String newProduct(Model model) {
//        model.addAttribute("global", new GlobalSecret());
//        return "globals";
//    }

    @RequestMapping(value = "global", method = RequestMethod.POST)
    public String saveGlobalSecret(GlobalSecret globalSecret) {
        System.out.println("Global Secret to query: " + globalSecret);
        ConcurrentLinkedQueue<GlobalSecret> queue = new ConcurrentLinkedQueue<>();
        queue.add(globalSecret);
        Thread consumer = new Thread(new Consumer(queue));
        for (int i = 0; i < queue.size(); i++) {

            System.out.println("QUEUE start: " + queue);
            GlobalSecret secretFromQueue = queue.peek();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(secretFromQueue.toString(), headers);
            try {
                repositoryService.listAllRepos().forEach(
                        k ->  globalSecret.setStatus(restTemplate.postForEntity(URL_.concat(k.getOwner()).concat("/").concat(k.getName()).concat("/secrets"), request, String.class).getStatusCode().value())
                );
                System.out.println("Status: "+globalSecret.getStatus());
                if (globalSecret.getStatus()==200){
                    globalSecretsService.saveGlobalSecret(globalSecret);
                    consumer.start();
                } else {
                    System.out.println("ERROR");
                }
            } catch (HttpServerErrorException se){
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("QUEUE end: " + queue);
        }
        return "redirect:/globals";
    }

    @RequestMapping("globals/{id}")
    public String deleteGlobalSecret(@PathVariable Integer id){
//        String url = URL_.concat("HAAK/t.vileno/secrets/nicola");
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", token);
//        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
//        restTemplate.exchange(url, DELETE, entity, String.class);
        globalSecretsService.deleteGlobalSecretById(id);
        return "redirect:/globals";
    }
}
