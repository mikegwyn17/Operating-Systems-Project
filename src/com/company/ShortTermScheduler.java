package com.company;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Mike-PC on 3/1/2015.
 */

class waitTimes {
    int jobNo;
    long waitTime;

    public waitTimes(int j, long w) {
        jobNo = j;
        waitTime = w;
    }
}

public class ShortTermScheduler
{
    public ArrayList<PCBObject> readyQueue;
    //Cpu cpu = new Cpu();
    public PCBObject Job;
    public boolean go = false;
    static PCB.sorttype byPriority = PCB.sorttype.JOB_PRIORITY;
    static PCB.sorttype byJobNo = PCB.sorttype.JOB_NUMBER;

    ArrayList<waitTimes> waitTimesFIFO;
    ArrayList<waitTimes> waitTimesPriority;

    boolean FIFO;

    public ShortTermScheduler() {
        readyQueue = new ArrayList<PCBObject>();
        waitTimesFIFO = new ArrayList<waitTimes>();
        waitTimesPriority = new ArrayList<waitTimes>();
    }

    public void PrioritySchedule () {
        int noOfJobs = Driver.pcb.getNumberOfJobs();

        if(Driver.pcb.getPCBSortStatus() != byPriority) {
            Driver.pcb.sortPCB(byPriority);
        }

        if(!readyQueue.isEmpty()) {
            readyQueue.clear();
        }

        for(int i = 1; i < noOfJobs+1; i++) {
            readyQueue.add(Driver.pcb.getPCB(i));
        }

        FIFO = false;
        runCpu();

    }

    public void FIFOSchedule() {
        int noOfJobs = Driver.pcb.getNumberOfJobs();

        System.out.println(noOfJobs);

        if(Driver.pcb.getPCBSortStatus() != byJobNo) {
            Driver.pcb.sortPCB(byJobNo);
        }

        if(!readyQueue.isEmpty()) {
            readyQueue.clear();
        }

        for(int i = 1; i < noOfJobs+1; i++) {
            readyQueue.add(Driver.pcb.getPCB(i));
        }
        FIFO = true;
        runCpu();
    }

    public void runCpu () {
        if(Driver.pcb.getPCBSortStatus() != Driver.byJobNo) {
            Driver.pcb.sortPCB(Driver.byJobNo);
        }

        for(int i = 0; i < readyQueue.size(); i++) {

            int jobNumber = readyQueue.get(i).getJobNumber();

            if(Driver.pcb.getPCB(jobNumber).isInMemory()) {

                if(FIFO) waitTimesFIFO.add(new waitTimes(readyQueue.get(i).getJobNumber(), (System.currentTimeMillis() - Driver.startTime)));
                else waitTimesPriority.add(new waitTimes(readyQueue.get(i).getJobNumber(), (System.currentTimeMillis() - Driver.startTime)));

                Driver.cpu.loadCpu(Driver.pcb.getPCB(jobNumber));
                Driver.pcb.getPCB(jobNumber).setHasJobRan(true);
            }
            else {


                if(FIFO) waitTimesFIFO.add(new waitTimes(readyQueue.get(i).getJobNumber(), (System.currentTimeMillis() - Driver.startTime)));
                else waitTimesPriority.add(new waitTimes(readyQueue.get(i).getJobNumber(), (System.currentTimeMillis() - Driver.startTime)));

                Driver.lts.needJobInMemory(Driver.pcb.getPCB(jobNumber));
                Driver.cpu.loadCpu(Driver.pcb.getPCB(jobNumber));
                Driver.pcb.getPCB(jobNumber).setHasJobRan(true);
            }
        }
    }

    public void printWaitingTimes(boolean priority) {

        // s
        // true = FIFO
        // false = Priority

        long FIFOTotal = 0, PriorityTotal = 0;

        Collections.sort(waitTimesPriority, new Comparator<waitTimes>() {
            @Override
            public int compare(waitTimes o1, waitTimes o2) {
                return o1.jobNo - o2.jobNo;
            }
        });

        if(priority) {
            for(int i = 0; i < waitTimesPriority.size(); i++) {
                System.out.println("Job " + i + " Waits -- Priority: " + waitTimesPriority.get(i).waitTime);
                PriorityTotal += waitTimesPriority.get(i).waitTime;
            }

            System.out.println("***************AVERAGES***************\nPriority: " + (PriorityTotal / 30.0));
        } else {
            for(int i = 0; i < waitTimesFIFO.size(); i++) {
                System.out.println("Job: " + i + "Waits -- FIFO: " + waitTimesFIFO.get(i).waitTime);
                FIFOTotal += waitTimesFIFO.get(i).waitTime;
            }
            System.out.println("***************AVERAGES***************\nPriority: " + (FIFOTotal / 30.0));
        }
    }
}
