package com.secret.bootstrap;

import com.secret.domain.GlobalSecret;
import com.secret.services.GlobalSecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by nicola on 25.10.17.
 */

public class Consumer implements Runnable {

    ConcurrentLinkedQueue<GlobalSecret> queue;

    public Consumer(ConcurrentLinkedQueue<GlobalSecret> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
//        GlobalSecret secret;
        System.out.println("--------Consumer started--------");

        System.out.println("In queue start: " + queue);
        queue.poll();
        System.out.println("In queue end: " + queue);

//            System.out.println("Http 200 ok");
//            while ((secret = queue.poll()) != null){
//                System.out.println("Removed: " + secret);
//            }

    }
}
