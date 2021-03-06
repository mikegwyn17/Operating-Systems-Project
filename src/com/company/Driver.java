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
    public static Cpu cpu;
    static public DecimalFormat df = new DecimalFormat("#.##%");

    static PCB.sorttype byPriority = PCB.sorttype.JOB_PRIORITY;
    static PCB.sorttype byJobNo = PCB.sorttype.JOB_NUMBER;
    static PCB.sorttype byShortestJob = PCB.sorttype.SHORTEST_JOB;
    static long startTime;

    public static void main(String[] a) {
        startTime = System.currentTimeMillis();
        String program = "program.txt";

        cpu = new Cpu();
        sts = new ShortTermScheduler();
        lts = new LongTermScheduler();
        loader = new Loader(program);

        try {
           loader.Start();
        } catch(IOException e) {
            System.out.println("Your program file was not found. Please rename the file to 'program.txt' and place it in the root directory of this project!");
        }

//        FIFO SCHEDULING

        ram.clearRam();
        lts.loadJobs(byJobNo);
        System.out.println("*************STARTING FIFO SCHEDULING SCHEDULING*************");
        sts.FIFOSchedule();
        sts.printWaitingTimes(byJobNo);
        System.out.println("\nCurrent RAM Usage: (FIFO) " + df.format(ram.getRamFilled()) + " filled.");


//        PRIORITY SCHEDULING

        ram.clearRam();
        pcb.clearStatus();
        startTime = System.currentTimeMillis();
        lts.loadJobs(byPriority);
        System.out.println("\n\n\n\n*************STARTING PRIORITY SCHEDULING*************");
        sts.PrioritySchedule();
        sts.printWaitingTimes(byPriority);
        System.out.println("\nCurrent RAM Usage: (PRIORITY) " + df.format(ram.getRamFilled()) + " filled.");


//        SJF SCHEDULING

        ram.clearRam();
        pcb.clearStatus();
        startTime = System.currentTimeMillis();
        lts.loadJobs(byShortestJob);
        System.out.println("\n\n\n\n*************STARTING SHORTEST JOB SCHEDULING*************");
        sts.SJFSchedule();
        sts.printWaitingTimes(byShortestJob);
        System.out.println("\nCurrent RAM Usage: (SJF) " + df.format(ram.getRamFilled()) + " filled.");

        if(loader.executed) {
            System.out.println("\nAll jobs have been loaded on to the Disk.\nYour disk is " + df.format(disk.diskPercent()) + " filled.");
        }
    }
}