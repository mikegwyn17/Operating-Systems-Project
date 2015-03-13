package com.company;
import java.util.ArrayList;


/**
 * Created by evanross on 2/23/15.
 */
public class LongTermScheduler {

    public ArrayList<PCBObject> readyQueue;
    private final int ramSize = 1024;
    private int ramLeft = 1024;
    public PCBObject Job;
    private int curJob;
    private double average;
    public  double percent;



    public LongTermScheduler(){
            readyQueue = new ArrayList<PCBObject>();
            curJob = 0;
            //Begin();
    }

    public void RamPercent(int p){

        switch (p){
            case 0:
                average = (ramSize - ramLeft);
                percent = (average / ramSize);
                System.out.println("The percentage of RAM used is:" + (percent * 100));
                break;
            case 1:
                Driver.totalPercent = Driver.sumPercent / Driver.counter;
                System.out.println("The average percent of RAM used is:" + Driver.totalPercent * 100);
                System.out.println("The average RAM percentage is:" + Driver.totalPercent * 100);
                break;

        }
    }




/*

 */















}
