package com.secret.bootstrap;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by nicola on 25.10.17.
 */
public class Producer implements Runnable {

    ConcurrentLinkedQueue<String> queue;

    public Producer(ConcurrentLinkedQueue<String> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        System.out.println("--------Producer started--------");
        for (int i = 0; i < 10; i++) {
            try {
                queue.add("String" + i);
                System.out.println("Added: String" + i);
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
