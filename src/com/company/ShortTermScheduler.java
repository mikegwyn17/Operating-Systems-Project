package com.company;

import java.util.*;

/**
 * Created by Mike-PC on 3/1/2015.
 */
public class ShortTermScheduler
{
    public ArrayList<PCBObject> readyQueue;
    Cpu cpu = new Cpu();
    public PCBObject Job;
    public boolean go = false;

    public ShortTermScheduler (PCB pcb)
    {

    }

    public void SJF ()
    {
        // Sort ready queue in order with highest priority at the front
        int n = readyQueue.size();
        go = true;
        while (go)
        {
            n--;
            for (int i = 0; i < n; i++)
            {
                PCBObject Job1 = readyQueue.get(i);
                PCBObject Job2 = readyQueue.get(i + 1);

                if (Job1.getJobPriority() < Job2.getJobPriority())
                {
                    Job = Job1;
                    PCBObject tempJob = readyQueue.get(i + 1);
                    readyQueue.set(i, tempJob);
                    readyQueue.set(i + 1, Job);
                }
            }
        }
    }

    public static void runCpu ()
    {

    }
}
