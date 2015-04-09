package com.company;

import java.util.Random;

/**
 * Created by Mike-PC on 4/1/2015.
 */
public class threadTest implements Runnable {
    public Random random = new Random();

    public threadTest ()
    {

    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted())
        {
            int randomNum = random.nextInt();
            System.out.println(randomNum);
            Thread.currentThread().interrupt();
        }
    }
}
