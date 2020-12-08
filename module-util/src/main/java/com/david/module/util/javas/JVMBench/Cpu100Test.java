package com.david.module.util.javas.JVMBench;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cpu100Test {

    public static Object l1 = new Object();
    public static Object l2 = new Object();
    private int index;

    public static void main(String[] a) {
        regext();
    }

    private static void testDeadLock() {
        Thread t1 = new Thread1();
        Thread t2 = new Thread2();
        t1.start();
        t2.start();
    }

    private static class Thread1 extends Thread {
        public void run() {
            synchronized (l1) {
                System.out.println("Thread 1: Holding lock 1...");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
                System.out.println("Thread 1: Waiting for lock 2...");
                synchronized (l2) {
                    System.out.println("Thread 2: Holding lock 1 & 2...");
                }
            }
        }
    }

    private static class Thread2 extends Thread {
        public void run() {
            synchronized (l2) {
                System.out.println("Thread 2: Holding lock 2...");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
                System.out.println("Thread 2: Waiting for lock 1...");
                synchronized (l1) {
                    System.out.println("Thread 2: Holding lock 2 & 1...");
                }
            }
        }
    }

    private static void regext() {
        String[] patternMatch = {"([\\w\\s]+)+([+\\-/*])+([\\w\\s]+)",
                "([\\w\\s]+)+([+\\-/*])+([\\w\\s]+)+([+\\-/*])+([\\w\\s]+)"};
        List<String> patternList = new ArrayList<String>();

        patternList.add("Avg Volume Units product A + Volume Units product A");
        patternList.add("Avg Volume Units /  Volume Units product A");
        patternList.add("Avg retailer On Hand / Volume Units Plan / Store Count");
        patternList.add("Avg Hand Volume Units Plan Store Count");
        patternList.add("1 - Avg merchant Volume Units");
        patternList.add("Total retailer shipment Count");

        for (String s : patternList) {

            for (int i = 0; i < patternMatch.length; i++) {
                Pattern pattern = Pattern.compile(patternMatch[i]);

                Matcher matcher = pattern.matcher(s);
                System.out.println(s);
                if (matcher.matches()) {

                    System.out.println("Passed");
                } else
                    System.out.println("Failed;");
            }

        }
    }
}
