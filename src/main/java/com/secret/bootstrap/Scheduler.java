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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
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

    @Value("${url.get.repo}")
    private String url;

    private GlobalSecretService globalSecretService;



    @Autowired
    public void setGlobalSecretService(GlobalSecretService globalSecretService) {
        this.globalSecretService = globalSecretService;
    }



    @Scheduled(fixedRate = 4000)
    public void refreshSecrets() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<Repository> repToCreate = new ArrayList<>();
        List<GlobalSecret> globalSecretList = new ArrayList<GlobalSecret>((Collection<? extends GlobalSecret>) globalSecretService.listAllGlobalSecrets());
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        try{
            Arrays.asList(restTemplate.exchange(url, GET, entity, Repository[].class).getBody()).forEach(i -> {
                Repository repository = Repository.builder()
                        .id(i.getId())
                        .owner(i.getOwner())
                        .name(i.getName())
                        .timeout(i.getTimeout())
                        .build();
                repToCreate.add(repository);
                System.out.println("REPO TO CREATE: " + repToCreate);
            });
        } catch (HttpClientErrorException | ResourceAccessException e){
            e.printStackTrace();
        }


        for (int i = 0; i < globalSecretList.size(); i++) {
            GlobalSecret gs = globalSecretList.get(i);
            HttpEntity<String> request = new HttpEntity<String>(gs.toString(), headers);
            for (Repository list : repToCreate) {
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
                        l -> System.out.println("Adress: " + URL_.concat(l.getOwner()).concat("/").concat(l.getName()).concat("/secrets"))
                );
                System.out.println("Request: " + request.getHeaders());
                repToCreate.forEach(
                        k -> restTemplate
                                .postForEntity(URL_.concat(k.getOwner()).concat("/").concat(k.getName()).concat("/secrets"), request, String.class)
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
