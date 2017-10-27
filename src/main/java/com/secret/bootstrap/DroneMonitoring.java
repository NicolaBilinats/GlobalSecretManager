//package com.secret.bootstrap;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * Created by nicola on 26.10.17.
// */
//@Component
//public class DroneMonitoring extends Thread{
//
//    ReposLoader reposLoader;
//
//    @Autowired
//    public ReposLoader getReposLoader() {
//        return reposLoader;
//    }
//
//    Thread thread = new Thread();
//    DroneMonitoring(){
//       thread.start();
//
//    }
//
//    @Override
//    public void run() {
//        System.out.println(reposLoader.getReq());
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
