package com.secret.bootstrap;

import com.secret.domain.Repository;
import com.secret.repositories.RepositoryRepo;
import com.secret.services.RepositoryService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

/**
 * Created by nicola on 10.10.17.
 */

@Component
public class ReposLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${token}")
    private String token;

    @Value("${url.get.repo}")
    private String url;

    private RepositoryService repositoryService;

    @Autowired
    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    public void getReq() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        if (restTemplate.exchange(url, GET, entity, Repository[].class).getStatusCode().value() == 200){
            Arrays.asList(restTemplate.exchange(url, GET, entity, Repository[].class).getBody()).forEach(i -> {
                Repository repository = new Repository.Builder()
                        .setId(i.getId())
                        .setOwner(i.getOwner())
                        .setName(i.getName())
                        .setTimeout(i.getTimeout())
                        .build();
                repositoryService.save(repository);
            });
        } else {
            System.out.println("Drone is not working");
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        getReq();
    }
}
