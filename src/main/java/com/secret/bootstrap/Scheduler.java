package com.secret.bootstrap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by nicola on 04.12.17.
 */

@Component
public class Scheduler {

    @Scheduled(fixedRate = 1500)
    public void refreshSecrets(){

    }
}
