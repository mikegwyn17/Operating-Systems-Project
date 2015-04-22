package com.company;

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

    ArrayList<waitTimes> waitTimesFIFO;
    ArrayList<waitTimes> waitTimesPriority;
    ArrayList<waitTimes> waitTimesSJF;
    ArrayList<executeTimes> executeTimesFIFO;
    ArrayList<executeTimes> executeTimesPriority;
    ArrayList<executeTimes> executeTimesSJF;

    PCB.sorttype algorithm;

    public ShortTermScheduler()
    {
        readyQueue = new ArrayList<PCBObject>();
        waitTimesFIFO = new ArrayList<waitTimes>();
        waitTimesPriority = new ArrayList<waitTimes>();
        waitTimesSJF = new ArrayList<waitTimes>();
        executeTimesFIFO = new ArrayList<executeTimes>();
        executeTimesPriority = new ArrayList<executeTimes>();
        executeTimesSJF = new ArrayList<executeTimes>();
    }

    public void PrioritySchedule()
    {
        int noOfJobs = Driver.pcb.getNumberOfJobs();

        if (Driver.pcb.getPCBSortStatus() != Driver.byPriority)
        {
            Driver.pcb.sortPCB(Driver.byPriority);
        }

        if (!readyQueue.isEmpty())
        {
            readyQueue.clear();
        }

        for (int i = 1; i < noOfJobs + 1; i++)
        {
            readyQueue.add(Driver.pcb.getPCB(i));
        }

        algorithm = Driver.byPriority;
        dispatcher();

    }

    public void FIFOSchedule()
    {
        int noOfJobs = Driver.pcb.getNumberOfJobs();

        System.out.println(noOfJobs);

        if (Driver.pcb.getPCBSortStatus() != Driver.byJobNo)
        {
            Driver.pcb.sortPCB(Driver.byJobNo);
        }

        if (!readyQueue.isEmpty())
        {
            readyQueue.clear();
        }

        for (int i = 1; i < noOfJobs + 1; i++)
        {
            readyQueue.add(Driver.pcb.getPCB(i));
        }

        algorithm = Driver.byJobNo;
        dispatcher();
    }

    public void SJFSchedule()
    {
        int noOfJobs = Driver.pcb.getNumberOfJobs();

        if (Driver.pcb.getPCBSortStatus() != Driver.byShortestJob)
        {
            Driver.pcb.sortPCB(Driver.byShortestJob);
        }

        if (!readyQueue.isEmpty())
        {
            readyQueue.clear();
        }

        for (int i = 1; i < noOfJobs + 1; i++)
        {
            readyQueue.add(Driver.pcb.getPCB(i));
        }

        algorithm = Driver.byShortestJob;
        dispatcher();
    }

    public void dispatcher()
    {
        if (Driver.pcb.getPCBSortStatus() != Driver.byJobNo)
        {
            Driver.pcb.sortPCB(Driver.byJobNo);
        }

        for (int i = 0; i < readyQueue.size(); i++)
        {

            int jobNumber = readyQueue.get(i).getJobNumber();

            if (!Driver.pcb.getPCB(jobNumber).isInMemory())
            {
                Driver.lts.needJobInMemory(Driver.pcb.getPCB(jobNumber));
            }

            if (algorithm == Driver.byJobNo)
            {
                waitTimesFIFO.add(new waitTimes(readyQueue.get(i).getJobNumber(), (System.currentTimeMillis() - Driver.startTime)));
                executeTimesFIFO.add(Driver.cpu.loadCpu(Driver.pcb.getPCB(jobNumber)));
            } else if (algorithm == Driver.byPriority)
            {
                waitTimesPriority.add(new waitTimes(readyQueue.get(i).getJobNumber(), (System.currentTimeMillis() - Driver.startTime)));
                executeTimesPriority.add(Driver.cpu.loadCpu(Driver.pcb.getPCB(jobNumber)));
            } else
            {
                waitTimesSJF.add(new waitTimes(readyQueue.get(i).getJobNumber(), (System.currentTimeMillis() - Driver.startTime)));
                executeTimesSJF.add(Driver.cpu.loadCpu(Driver.pcb.getPCB(jobNumber)));
            }
            Driver.pcb.getPCB(jobNumber).setHasJobRan(true);
        }
    }

    public void printWaitingTimes(PCB.sorttype s)
    {

        long FIFOTotal = 0, PriorityTotal = 0, SJFTotal = 0;

        Collections.sort(waitTimesPriority, new Comparator<waitTimes>()
        {
            @Override
            public int compare(waitTimes o1, waitTimes o2)
            {
                return o1.jobNo - o2.jobNo;
            }
        });
        Collections.sort(waitTimesSJF, new Comparator<waitTimes>()
        {
            @Override
            public int compare(waitTimes o1, waitTimes o2)
            {
                return o1.jobNo - o2.jobNo;
            }
        });

        Collections.sort(executeTimesSJF, new Comparator<executeTimes>()
        {
            @Override
            public int compare(executeTimes o1, executeTimes o2)
            {
                return o1.jobNo - o2.jobNo;
            }
        });
        Collections.sort(executeTimesPriority, new Comparator<executeTimes>()
        {
            @Override
            public int compare(executeTimes o1, executeTimes o2)
            {return o1.jobNo - o2.jobNo;}});

        if (s == Driver.byPriority)
        {
            for (int i = 0; i < waitTimesPriority.size(); i++)
            {
                System.out.println("Job " + i + " Waits -- Priority: " + waitTimesPriority.get(i).waitTime);
                PriorityTotal += waitTimesPriority.get(i).waitTime;
            }

            System.out.println("***************AVERAGES***************\nPriority: " + (PriorityTotal / 30.0));

            PriorityTotal = 0;

            for (int i = 0; i < executeTimesPriority.size(); i++)
            {
                System.out.println("Job " + i + " Executes -- Priority: " + executeTimesPriority.get(i).waitTime);
                PriorityTotal += executeTimesPriority.get(i).waitTime;
            }

            System.out.println("***************AVERAGES***************\nPriority: " + (PriorityTotal / 30.0) + "\n");

        } else if (s == Driver.byJobNo)
        {
            for (int i = 0; i < waitTimesFIFO.size(); i++)
            {
                System.out.println("Job: " + i + " Waits -- FIFO: " + waitTimesFIFO.get(i).waitTime);
                FIFOTotal += waitTimesFIFO.get(i).waitTime;
            }

            System.out.println("***************AVERAGES***************\nFIFO: " + (FIFOTotal / 30.0));

            FIFOTotal = 0;

            for (int i = 0; i < executeTimesFIFO.size(); i++)
            {
                System.out.println("Job: " + i + " Executes -- FIFO: " + executeTimesFIFO.get(i).waitTime);
                FIFOTotal += executeTimesFIFO.get(i).waitTime;
            }
            System.out.println("***************AVERAGES***************\nFIFO: " + (FIFOTotal / 30.0) + "\n");
        } else
        {
            for (int i = 0; i < waitTimesSJF.size(); i++)
            {
                System.out.println("Job: " + i + " Waits -- SJF: " + waitTimesSJF.get(i).waitTime);
                SJFTotal += waitTimesSJF.get(i).waitTime;
            }
            System.out.println("***************AVERAGES***************\nSJF: " + (SJFTotal / 30.0));

            SJFTotal = 0;

            for (int i = 0; i < executeTimesSJF.size(); i++)
            {
                System.out.println("Job: " + i + " Executes -- SJF: " + executeTimesSJF.get(i).waitTime);
                SJFTotal += executeTimesSJF.get(i).waitTime;
            }
            System.out.println("***************AVERAGES***************\nSJF: " + (SJFTotal / 30.0) + "\n");
        }
    }
}
