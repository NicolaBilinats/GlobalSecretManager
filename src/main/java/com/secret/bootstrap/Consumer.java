package com.secret.bootstrap;

import com.secret.domain.GlobalSecret;
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
        System.out.println("--------Consumer started--------");
        System.out.println("In queue start: " + queue);
        queue.poll();
        System.out.println("In queue end: " + queue);
    }
}
