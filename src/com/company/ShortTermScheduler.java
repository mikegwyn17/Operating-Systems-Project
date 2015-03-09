package com.company;

import java.util.ArrayList;

/**
 * Created by Mike-PC on 3/1/2015.
 */
public class ShortTermScheduler
{
    public ArrayList<PCBObject> readyQueue;
    Cpu cpu = new Cpu();
    public boolean go;
    public PCBObject Job;

    public ShortTermScheduler (PCB pcb)
    {

    }

    public void SJF ()
    {
        // Sort ready queue in order with highest priority at the front
        go = true;
        int n = readyQueue.size();

        while (go) {
            n--;
            go = false;
            for (int i = 0; i < n; i++) {
                PCBObject Job1 = readyQueue.get(i);
                PCBObject Job2 = readyQueue.get(i + 1);

                if (Job1.getJobPriority() < Job2.getJobPriority()) {
                    Job = Job1;
                    PCBObject tempJob = readyQueue.get(i + 1);
                    readyQueue.add(i, tempJob);
                    readyQueue.add(i + 1, Job);
                    go = true;
                }
            }
        }
    }

    public static void runCpu ()
    {

    }
}
