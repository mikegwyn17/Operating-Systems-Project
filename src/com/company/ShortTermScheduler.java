package com.company;

import java.util.*;

/**
 * Created by Mike-PC on 3/1/2015.
 */
public class ShortTermScheduler
{
    public ArrayList<PCBObject> readyQueue;
    //Cpu cpu = new Cpu();
    public PCBObject Job;
    public boolean go = false;
    static PCB.sorttype byPriority = PCB.sorttype.JOB_PRIORITY;
    static PCB.sorttype byJobNo = PCB.sorttype.JOB_NUMBER;

    public ShortTermScheduler() {
        readyQueue = new ArrayList<PCBObject>();
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

        runCpu();
    }

    public void FIFOSchedule() {
        int noOfJobs = Driver.pcb.getNumberOfJobs();

        if(Driver.pcb.getPCBSortStatus() != byJobNo) {
            Driver.pcb.sortPCB(byJobNo);
        }

        if(!readyQueue.isEmpty()) {
            readyQueue.clear();
        }

        for(int i = 1; i < noOfJobs+1; i++) {
            readyQueue.add(Driver.pcb.getPCB(i));
        }

        runCpu();
    }

    public void runCpu () {
        for(int i = 0; i < readyQueue.size(); i++) {
            Driver.cpu.loadCpu(readyQueue.get(i));
        }
    }

}
