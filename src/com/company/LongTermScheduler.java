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
    //private int curJob;
    private double average;
    public  double percent;
    //instruction code

    public LongTermScheduler(){ readyQueue = new ArrayList<PCBObject>(); }

    //Checks to see if a process is I/O or CPU bound
    public void checkType() {
        int k = Driver.pcb.getNumberOfJobs();

        for (int i = 0; i <=k; i++) {
            if(Job.getInstruction() == "00" || Job.getInstruction() == "01") {
                Job.setIObound(true);
            }
            else {
                Job.setIObound(false);
            }
        }
    }

    public void beginSchedule() {
        int k = Driver.pcb.getNumberOfJobs();
        int IOboundInside =0;
        int CPUboundInside=0;
        //loop check
        while(IOboundInside - CPUboundInside <= 2 || IOboundInside - CPUboundInside >= -2) {
            for (int i = 0; i <=k; i++) {
                readyQueue.add(Driver.pcb.getPCB(i));
                if(Job.checkIOBound() == true){
                    IOboundInside++;
                }
                else{
                    CPUboundInside++;
                }
            }
        }
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
