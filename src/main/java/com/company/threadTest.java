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
        try {
                int randomNum = random.nextInt();
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
