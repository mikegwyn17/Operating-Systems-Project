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

    static final boolean PRIORITY = true;
    static final boolean FIFO = false;

    static PCB.sorttype byPriority = PCB.sorttype.JOB_PRIORITY;
    static PCB.sorttype byJobNo = PCB.sorttype.JOB_NUMBER;
    static long startTime;

    public static void main(String[] a) {
        startTime = System.currentTimeMillis();
        String program = "program.txt";

        cpu = new Cpu(ram);
        sts = new ShortTermScheduler();
        lts = new LongTermScheduler();
        loader = new Loader(program);

        try {
           loader.Start();
        } catch(IOException e) {
            System.out.println("Your program file was not found. Please rename the file to 'program.txt' and place it in the root directory of this project!");
        }

//        FIFO SCHEDULING
//        COMMENT THIS OUT IF YOU WANT TO RUN PRIORITY
        lts.loadJobs(byJobNo);
        System.out.println("*************STARTING FIFO SCHEDULING SCHEDULING*************");
        sts.FIFOSchedule();
        sts.printWaitingTimes(FIFO);
        pcb.clearRanStatus();


//        PRIORITY SCHEDULING
//        COMMENT THIS OUT IF YOU WANT TO RUN FIFO
        startTime = System.currentTimeMillis();
        lts.loadJobs(byPriority);
        System.out.println("\n\n\n\n*************STARTING PRIORITY SCHEDULING*************");
        sts.PrioritySchedule();
        sts.printWaitingTimes(PRIORITY);

        if(loader.executed) {
            System.out.println("\nAll jobs have been loaded on to the Disk.\nYour disk is " + df.format(disk.diskPercent()) + " filled.");
        }
        System.out.println("\nCurrent RAM Usage: " + df.format(ram.getRamFilled()) + " filled.");
    }
}