//package com.company;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//
///**
// * Created by evanross on 2/4/15.
// */
//public class Loader {
//
//    public void method(){
//
//        for (int i = 0; i < 10; i++)
//            System.out.println(i % 2);
//        System.out.println("Hello World");
//    }
//
//    FileReader fr;
//    BufferedReader br;
//
//    public  Loader(String file) {
//
//        try {
//            fr = new FileReader(file);
//            br = new BufferedReader(fr);
//
//            String line;
//            int index = 0;
//            while(true) {
//                line = br.readLine();
//                if(line == null) break;
//
////                String[] parts = line.split(" ");
////
////                System.out.println(index);
////
////                if(parts.length > 0) {
////                    if(parts[1].equals("JOB")) {
////                        //System.out.println("Inserting Job " + parts[2] + " starting index " + index);
////                        System.out.println("inserting job");
////                    }
////                    else if(parts[1].equals("Data")) {
////                        //System.out.println("Inserting Data " + parts[2] + "starting index " + index);
////                        System.out.println("inserting data");
////                    }
////                } else {
////                    Main.disk.writeDisk(line, index);
////                    System.out.println(line + index);
////                    index++;
////                }
//                if(line.contains("JOB") || line.contains("Data")) {
//
//                } else {
//                    Main.disk.writeDisk(line, index);
//                    index++;
//                }
//
//            }
//            //JUST TO TEST.
//            System.out.println(Main.disk.returnData(4));
//
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
