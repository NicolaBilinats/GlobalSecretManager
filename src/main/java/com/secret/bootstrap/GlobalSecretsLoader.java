package com.secret.bootstrap;

import com.secret.domain.Secret;
import com.secret.repositories.SecretRepo;
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

    private SecretRepo globalSecretRepo;

    private Logger log = Logger.getLogger(GlobalSecretsLoader.class);

    @Autowired
    public void setGlobalSecretRepo(SecretRepo globalSecretRepo){
        this.globalSecretRepo = globalSecretRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//
//        Secret secret1 = new Secret();
//        secret1.setId(12123);
//        secret1.setEvent("[push,tag]");
//        secret1.setName("Nicola");
//        System.out.println(secret1);
//        globalSecretRepo.save(secret1);
//        System.out.println(globalSecretRepo);
//        log.info("Secret's one ID " + secret1.getId());
//
//        Secret secret2 = new Secret();
//        secret2.setId(14123);
//        secret2.setName("Bilinac");
//        secret2.setEvent("[push,tag]");
//        globalSecretRepo.save(secret2);
//
//        log.info("Secret's two ID " + secret2.getId());

    }
}

