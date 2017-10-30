package com.secret.bootstrap;

import com.secret.domain.Repository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;

/**
 * Created by nicola on 26.10.17.
 */
@Component
public class DroneMonitoring extends Thread{

    @Value("${token}")
    private String token;

    @Value("${url.get.repo}")
    private String url;

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    DroneMonitoring(){
//       start();

    }


    @Override
    public void run() {
        int status = 200;
        int repoLenght;
        System.out.println("--------Start monitoring Drone--------");
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        try {
            while (status == 200){
                status = restTemplate.exchange(url, GET, entity, Repository[].class).getStatusCode().value();
                repoLenght = restTemplate.exchange(url, GET, entity, Repository[].class).getBody().length;
                System.out.println("Number of repo from Drone: "+repoLenght);
                Thread.sleep(2000);
            }
            if (status != 200){
                System.out.println("Connection with drone lost");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NullPointerException n){
            n.printStackTrace();
        }
    }
}
