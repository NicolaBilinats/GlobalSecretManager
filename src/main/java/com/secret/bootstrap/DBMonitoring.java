package com.secret.bootstrap;

import com.secret.services.SecretsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by nicola on 24.10.17.
 */
@Component
public class DBMonitoring  extends Thread{
    private SecretsService secretsService;

    @Autowired
    public void setSecretsService(SecretsService secretsService) {
        this.secretsService = secretsService;
    }

    public void run(){
        System.out.println("Start thread");
        try {
            while (true){
                System.out.println("Thread is working");
                System.out.println("Secret's in H2: " + secretsService.listAllSecrets());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread is stopped");
        }
    }

    public DBMonitoring() {
        super();
        start();
    }
}
