package com.secret.bootstrap;

import com.secret.domain.Repository;
import com.secret.services.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

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
        try{
            Arrays.asList(restTemplate.exchange(url, GET, entity, Repository[].class).getBody()).forEach(i -> {
                Repository repository = Repository.builder()
                        .id(i.getId())
                        .owner(i.getOwner())
                        .name(i.getName())
                        .timeout(i.getTimeout())
                        .build();
                repositoryService.save(repository);
            });
        } catch (HttpClientErrorException | ResourceAccessException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        getReq();
    }
}
