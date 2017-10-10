package com.secret.bootstrap;

import com.secret.domain.Repository;
import com.secret.repositories.RepositoryRepo;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by nicola on 10.10.17.
 */
@Component
public class ReposLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${token}")
    private String token;

    @Value("${url.get.repo}")
    private String url;

    private RepositoryRepo repositoryRepo;

    private Logger log = Logger.getLogger(ReposLoader.class);

    @Autowired
    public void setRepositoryRepo(RepositoryRepo repositoryRepo) {
        this.repositoryRepo = repositoryRepo;
    }
    public List<Repository> getReq() {
        List<Repository> response = null;
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(url);
            response = Arrays.asList(webResource.accept("application/json")
                    .header("Authorization", token)
                    .get(Repository[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        getReq().forEach(i->{
            Repository repo = new Repository();
            repo.setId(i.getId());
            repo.setOwner(i.getOwner());
            repo.setName(i.getName());
            repo.setTimeout(i.getTimeout());
            repositoryRepo.save(repo);
        });
    }
}
