package com.secret.bootstrap;

import com.secret.domain.GlobalSecret;
import com.secret.domain.Repository;
import com.secret.domain.Secret;
import com.secret.services.GlobalSecretService;
import com.secret.services.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.springframework.http.HttpMethod.GET;

/**
 * Created by nicola on 04.12.17.
 */

@Component
public class Scheduler {

    @Value("${token}")
    private String token;

    @Value("${url.repos}")
    private String URL_;

    private GlobalSecretService globalSecretService;

    private RepositoryService repositoryService;

    @Autowired
    public void setGlobalSecretService(GlobalSecretService globalSecretService) {
        this.globalSecretService = globalSecretService;
    }

    @Autowired
    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @Scheduled(fixedRate = 3000)
    public void refreshSecrets() {

        List<Repository> repToCreate = new ArrayList<Repository>((Collection<? extends Repository>) repositoryService.listAllRepos());
        List<GlobalSecret> globalSecretList = new ArrayList<GlobalSecret>((Collection<? extends GlobalSecret>) globalSecretService.listAllGlobalSecrets());
        for (Repository r : repToCreate) {
            System.out.println("Repositories: " + r);
        }



        for (int i = 0; i < globalSecretList.size(); i++) {
            GlobalSecret gs = globalSecretList.get(i);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(gs.toString(), headers);
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            for (Repository list : repositoryService.listAllRepos()) {
                for (Secret l : Arrays.asList(restTemplate
                        .exchange(URL_.concat(list.getOwner()).concat("/").concat(list.getName().concat("/secrets/")), GET, entity, Secret[].class)
                        .getBody())) {
                    if (l.getName().equals(gs.getName())) {
                        repToCreate.remove(list);
                        break;
                    }
                }
            }
            try {
                repToCreate.forEach(
                        k -> gs.setStatus(restTemplate
                                .postForEntity(URL_.concat(k.getOwner()).concat("/").concat(k.getName()).concat("/secrets"), request, String.class)
                                .getStatusCode()
                                .value())
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
