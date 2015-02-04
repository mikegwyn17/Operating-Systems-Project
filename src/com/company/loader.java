package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by evanross on 2/4/15.
 */
public class loader {

    public void method(){

        for (int i = 0; i < 10; i++)
            System.out.println(i % 2);
        System.out.println("Hello World");
    }

    FileReader fr;
    BufferedReader br;

    public  loader(String file) {

        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line;
            while(true) {
                line = br.readLine();
                if(line == null) break;

                System.out.println(line);
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
