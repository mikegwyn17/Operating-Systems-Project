package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class Driver {
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


    static int pageFaultCount;

    public static void main(String[] a) {
        startTime = System.currentTimeMillis();
        String program = "program.txt";
        pageFaultCount = 0;

        cpu = new Cpu();
        sts = new ShortTermScheduler();
        lts = new LongTermScheduler();
        loader = new Loader(program);
        pager = new Pager();

        try {
           loader.Start();
        } catch(IOException e) {
            System.out.println("Your program file was not found. Please rename the file to 'program.txt' and place it in the root directory of this project!");
        }

//        FIFO SCHEDULING

        pcb.clearStatus();
        ram.clearRam();
        pager.initialFrames();
        //lts.loadJobs(byJobNo);
        System.out.println("*************STARTING FIFO SCHEDULING SCHEDULING*************");
        sts.FIFOSchedule();
        sts.printWaitingTimes(byJobNo);
        System.out.println("RAM Usage: (FIFO) " + df.format(ram.getRamFilled()));

        pcb.printPageFaults();

        System.out.println("\nFIFO PAGE FAULT SERVICE TIMES: ");
        for(int i = 1; i <= 30; i++) {
            pcb.getPCB(i).pageFaultServiceTime();
        }

//        PRIORITY SCHEDULING

        pageFaultCount = 0;
        pcb.clearStatus();
        ram.clearRam();
        startTime = System.currentTimeMillis();
        pager.initialFrames();
        System.out.println("\n\n\n\n*************STARTING PRIORITY SCHEDULING*************");
        sts.PrioritySchedule();
        sts.printWaitingTimes(byPriority);
        System.out.println("RAM Usage: (Priority) " + df.format(ram.getRamFilled()));

        pcb.printPageFaults();

        System.out.println("\nPRIORITY PAGE FAULT SERVICE TIMES: ");
        for(int i = 1; i <= 30; i++) {
            pcb.getPCB(i).pageFaultServiceTime();
        }

//        SJF SCHEDULING

        pcb.clearStatus();
        ram.clearRam();
        pageFaultCount = 0;
        startTime = System.currentTimeMillis();
        pager.initialFrames();
        System.out.println("\n\n\n\n*************STARTING SHORTEST JOB SCHEDULING*************");
        sts.SJFSchedule();
        sts.printWaitingTimes(byShortestJob);
        System.out.println("RAM Usage: (SJF) " + df.format(ram.getRamFilled()));

        pcb.printPageFaults();

        System.out.println("\nSJF PAGE FAULT SERVICE TIMES: ");
        for(int i = 1; i <= 30; i++) {
            pcb.getPCB(i).pageFaultServiceTime();
        }

        for(int i = 1; i <= 30; i++) {
            pcb.getPCB(i).printPageTable();
        }

        if(loader.executed) {
            System.out.println("\nAll jobs have been loaded on to the Disk.\nYour disk is " + df.format(disk.diskPercent()) + " filled.");
        }
        System.out.println("\nCurrent RAM Usage: " + df.format(ram.getRamFilled()) + " filled.");
    }
}