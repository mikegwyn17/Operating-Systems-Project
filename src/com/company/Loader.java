package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by evanross on 2/4/15.
 */
public class Loader {

    public void method(){

        for (int i = 0; i < 10; i++)
            System.out.println(i % 2);
        System.out.println("Hello World");
    }

    FileReader fr;
    BufferedReader br;

    public  Loader(String file) {

        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line;
            int index = 0;
            while(true) {
                line = br.readLine();
                if(line == null) break;

                if(line.contains("JOB") || line.contains("Data")) {

                } else {
                    Main.disk.writeDisk(line, index);
                    index++;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
