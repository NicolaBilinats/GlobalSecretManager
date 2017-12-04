package com.secret.bootstrap;

import com.secret.domain.User;
import com.secret.repositories.UsersRepo;
import com.secret.services.UserService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import lombok.Builder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

/**
 * Created by nicola on 09.10.17.
 */

@Component
public class UsersLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${token}")
    private String token;

    @Value("${url.get.users}")
    private String url;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private void getReq() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        try{
            Arrays.asList(restTemplate.exchange(url, GET, entity, User[].class).getBody()).forEach(i -> {
                User user = User.builder()
                        .id(i.getId())
                        .login(i.getLogin())
                        .email(i.getEmail())
                        .avatar_url(i.getAvatar_url())
                        .active(i.getActive())
                        .build();
                userService.saveUser(user);
            });
        } catch (HttpClientErrorException | ResourceAccessException e ){
            e.printStackTrace();
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        getReq();
    }
}
