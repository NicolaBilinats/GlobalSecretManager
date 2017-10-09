package com.secret.bootstrap;

import com.secret.domain.GlobalSecret;
import com.secret.repositories.GlobalSecretRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by nicola on 06.10.17.
 */
@Component
public class GlobalSecretsLoader implements ApplicationListener<ContextRefreshedEvent> {

    private GlobalSecretRepo globalSecretRepo;

    private Logger log = Logger.getLogger(GlobalSecretsLoader.class);

    @Autowired
    public void setGlobalSecretRepo(GlobalSecretRepo globalSecretRepo){
        this.globalSecretRepo = globalSecretRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        GlobalSecret secret1 = new GlobalSecret();
        secret1.setEvent("[push,tag]");
        secret1.setName("Nicola");
        System.out.println(secret1);
        globalSecretRepo.save(secret1);
        System.out.println(globalSecretRepo);
        log.info("Secret's one ID " + secret1.getId());

        GlobalSecret secret2 = new GlobalSecret();
        secret2.setName("Bilinac");
        secret2.setEvent("[push,tag]");
        globalSecretRepo.save(secret2);

        log.info("Secret's two ID " + secret2.getId());

    }
}

