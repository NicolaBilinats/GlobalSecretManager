package com.secret.bootstrap;

import com.secret.domain.User;
import com.secret.repositories.UsersRepo;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by nicola on 09.10.17.
 */
@Component
public class UsersLoader implements ApplicationListener<ApplicationStartingEvent> {

    @Value("${token}")
    private String token;

    @Value("${url.get.users}")
    private String url;

    private UsersRepo usersRepo;
    private Logger log = Logger.getLogger(UsersLoader.class);

    @Autowired
    public void setUsersRepo(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public List<User> getReq() {
        List<User> response = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(url);
            response = Arrays.asList(webResource.accept("application/json")
                    .header("Authorization", token)
                    .get(User[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        getReq().forEach(i -> {
            User user = new User();
            user.setId(i.getId());
            user.setLogin(i.getLogin());
            user.setEmail(i.getEmail());
            user.setActive(i.getActive());
            usersRepo.save(user);
        });
    }
}
