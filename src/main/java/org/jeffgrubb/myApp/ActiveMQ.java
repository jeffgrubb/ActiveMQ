/**
 * Created by jgrubb on 12/13/15.
 */

package org.jeffgrubb.myApp;

public class ActiveMQ {

    public static void main(String[] args) throws Exception {
        if(args.length != 1) {
            System.out.println("args length: " + args.length);
            System.out.println("Useage: activemq [consumer|producer]");
            return;
        }

        if(args[0].compareToIgnoreCase("consumer") == 0) {
            System.out.println("Consumer");
            while(true) {
                thread(new FooConsumer(), false);
                Thread.sleep(1000);
            }
        } else if(args[0].compareToIgnoreCase("producer") == 0) {
            System.out.println("Producer");

            while(true) {
                try {
                    System.in.read();
                    thread(new FooProducer(), false);
                } catch(Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }
            }
        } else {
            System.out.println("Useage: activemq [consumer|producer]");
        }
    }

    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }
}
