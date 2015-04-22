package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
    public static Pager pager;
    static public DecimalFormat df = new DecimalFormat("#.##%");

    static PCB.sorttype byPriority = PCB.sorttype.JOB_PRIORITY;
    static PCB.sorttype byJobNo = PCB.sorttype.JOB_NUMBER;
    static PCB.sorttype byShortestJob = PCB.sorttype.SHORTEST_JOB;
    static long startTime;

    static FileWriter fileWriter;
    static BufferedWriter buffWriter;

    static int pageFaultCount;

    static int fifoPageFaults, priorityPageFaults, sjfPageFaults;

    public static void main(String[] a) {
        startTime = System.currentTimeMillis();
        String program = "program.txt";
        pageFaultCount = 0;

        cpu = new Cpu();
        sts = new ShortTermScheduler();
        lts = new LongTermScheduler();
        loader = new Loader(program);
        pager = new Pager();

        fifoPageFaults = 0;
        priorityPageFaults = 0;
        sjfPageFaults = 0;


        try {
           loader.Start();
        } catch(IOException e) {
            System.out.println("Your program file was not found. Please rename the file to 'program.txt' and place it in the root directory of this project!");
        }

//        FIFO SCHEDULING

        pcb.clearStatus();
        pager.initialFrames();
        //lts.loadJobs(byJobNo);
        System.out.println("*************STARTING FIFO SCHEDULING SCHEDULING*************");
        sts.FIFOSchedule();
        sts.printWaitingTimes(byJobNo);

        System.out.println("FIFO PAGE FAULT SERVICE TIMES: ");
        for(int i = 1; i <= 30; i++) {
            pcb.getPCB(i).pageFaultServiceTime();
        }

        fifoPageFaults = pageFaultCount;
        System.out.println("FIFO Page Faults: " + fifoPageFaults);


//        PRIORITY SCHEDULING

        pageFaultCount = 0;
        pcb.clearStatus();
        startTime = System.currentTimeMillis();
        pager.initialFrames();
        //lts.loadJobs(byPriority);
        System.out.println("\n\n\n\n*************STARTING PRIORITY SCHEDULING*************");
        sts.PrioritySchedule();
        sts.printWaitingTimes(byPriority);

        priorityPageFaults = pageFaultCount;
        System.out.println("PRIORITY PAGE FAULT SERVICE TIMES: ");
        for(int i = 1; i <= 30; i++) {
            pcb.getPCB(i).pageFaultServiceTime();
        }


        System.out.println("Priority Page Faults: " + priorityPageFaults);

//        SJF SCHEDULING

        pcb.clearStatus();
        pageFaultCount = 0;
        startTime = System.currentTimeMillis();
        pager.initialFrames();
        //lts.loadJobs(byShortestJob);
        System.out.println("\n\n\n\n*************STARTING SHORTEST JOB SCHEDULING*************");
        sts.SJFSchedule();
        sts.printWaitingTimes(byShortestJob);
        sjfPageFaults = pageFaultCount;

        System.out.println("SJF PAGE FAULT SERVICE TIMES: ");
        for(int i = 1; i <= 30; i++) {
            pcb.getPCB(i).pageFaultServiceTime();
        }

        System.out.println("SJF Page Faults: " + sjfPageFaults);

        for(int i = 1; i <= 30; i++) {
            pcb.getPCB(i).printPageTable();
        }

        if(loader.executed) {
            System.out.println("\nAll jobs have been loaded on to the Disk.\nYour disk is " + df.format(disk.diskPercent()) + " filled.");
        }
        System.out.println("\nCurrent RAM Usage: " + df.format(ram.getRamFilled()) + " filled.");
    }
}