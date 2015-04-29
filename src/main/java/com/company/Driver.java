package com.company;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class Driver {
    public static double totalPercent;
    public static double sumPercent;
    public static int counter = 0;
    public static Disk disk = new Disk();
    public static PCB pcb = new PCB();
    public static Ram ram = new Ram();
    public static ShortTermScheduler sts;
    public static LongTermScheduler lts;
    public static Loader loader;
    public static Cpu cpu1, cpu2, cpu3, cpu4;
    public static int cpu1Count, cpu2Count, cpu3Count, cpu4Count;

    static public DecimalFormat df = new DecimalFormat("#.##%");

    static PCB.sorttype byPriority = PCB.sorttype.JOB_PRIORITY;
    static PCB.sorttype byJobNo = PCB.sorttype.JOB_NUMBER;
    static PCB.sorttype byShortestJob = PCB.sorttype.SHORTEST_JOB;
    static long startTime;

    public static void main(String[] a) {
        startTime = System.currentTimeMillis();
        String program = "program.txt";

        cpu1 = new Cpu();
        cpu2 = new Cpu();
        cpu3 = new Cpu();
        cpu4 = new Cpu();
        cpu1Count = 0;
        cpu2Count = 0;
        cpu3Count = 0;
        cpu4Count = 0;

        sts = new ShortTermScheduler();
        lts = new LongTermScheduler();
        loader = new Loader(program);

        try {
            loader.Start();
        } catch(IOException e) {
            System.out.println("Your program file was not found. Please rename the file to 'program.txt' and place it in the root directory of this project!");
        }

//        FIFO SCHEDULING

        pcb.clearStatus();
        ram.clearRam();
        lts.loadJobs(byJobNo);
        System.out.println("*************STARTING FIFO SCHEDULING SCHEDULING*************");
        sts.FIFOSchedule();
        sts.printWaitingTimes(byJobNo);

        System.out.println("\nCurrent RAM Usage (FIFO): " + df.format(ram.getRamFilled()) + " filled.");
        System.out.println("Cpu1 Job Count: " + cpu1Count);
        System.out.println("Cpu2 Job Count: " + cpu2Count);
        System.out.println("Cpu3 Job Count: " + cpu3Count);
        System.out.println("Cpu4 Job Count: " + cpu4Count);
        cpu1Count = 0;
        cpu2Count = 0;
        cpu3Count = 0;
        cpu4Count = 0;

//        PRIORITY SCHEDULING

        pcb.clearStatus();
        ram.clearRam();
        startTime = System.currentTimeMillis();
        lts.loadJobs(byPriority);
        System.out.println("\n\n\n\n*************STARTING PRIORITY SCHEDULING*************");
        sts.PrioritySchedule();
        sts.printWaitingTimes(byPriority);

        System.out.println("\nCurrent RAM Usage (PRIORITY): " + df.format(ram.getRamFilled()) + " filled.");
        System.out.println("Cpu1 Job Count: " + cpu1Count);
        System.out.println("Cpu2 Job Count: " + cpu2Count);
        System.out.println("Cpu3 Job Count: " + cpu3Count);
        System.out.println("Cpu4 Job Count: " + cpu4Count);
        cpu1Count = 0;
        cpu2Count = 0;
        cpu3Count = 0;
        cpu4Count = 0;

//        SJF SCHEDULING

        pcb.clearStatus();
        ram.clearRam();
        startTime = System.currentTimeMillis();
        lts.loadJobs(byShortestJob);
        System.out.println("\n\n\n\n*************STARTING SHORTEST JOB SCHEDULING*************");
        sts.SJFSchedule();
        sts.printWaitingTimes(byShortestJob);

        System.out.println("\nCurrent RAM Usage (SJF):" + df.format(ram.getRamFilled()) + " filled.");
        System.out.println("Cpu1 Job Count: " + cpu1Count);
        System.out.println("Cpu2 Job Count: " + cpu2Count);
        System.out.println("Cpu3 Job Count: " + cpu3Count);
        System.out.println("Cpu4 Job Count: " + cpu4Count);
        cpu1Count = 0;
        cpu2Count = 0;
        cpu3Count = 0;
        cpu4Count = 0;

        if(loader.executed) {
            System.out.println("\nAll jobs have been loaded on to the Disk.\nYour disk is " + df.format(disk.diskPercent()) + " filled.");
        }
    }
}