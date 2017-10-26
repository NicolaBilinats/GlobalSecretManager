package com.secret.bootstrap;

import com.secret.domain.GlobalSecret;
import com.secret.services.GlobalSecretService;
import com.secret.services.SecretsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by nicola on 24.10.17.
 */
@Component
public class DBMonitoring extends Thread {
    private GlobalSecretService globalSecretService;

    @Autowired
    public void setGlobalSecretService(GlobalSecretService globalSecretService) {
        this.globalSecretService = globalSecretService;
    }

    public void run() {
        Thread thread = new Thread();
        System.out.println("Start thread");
        try {
            while (true) {
                System.out.println(Thread.currentThread().getName());
                thread.sleep(3000);
                for (GlobalSecret i : globalSecretService.listAllGlobalSecrets())
                {
                    System.out.println("Element: " + i);
                }
                System.out.println("GlobalSecret's lenght in H2: " + globalSecretService.getLenght());
            }
        } catch (InterruptedException e) {
            System.out.println("Thread is stopped");
        } catch (NullPointerException f) {
            f.printStackTrace();
        }
    }

    public DBMonitoring() {
//        start();
    }
}
