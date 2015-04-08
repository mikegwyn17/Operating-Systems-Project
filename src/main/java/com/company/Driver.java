package com.company;

import java.io.IOException;
import java.text.DecimalFormat;

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
    public static Cpu cpu1;
    public static Cpu cpu2;
    public static Cpu cpu3;
    public static Cpu cpu4;
    static public DecimalFormat df = new DecimalFormat("#.##%");

    static final boolean PRIORITY = true;
    static final boolean FIFO = false;

    static PCB.sorttype byPriority = PCB.sorttype.JOB_PRIORITY;
    static PCB.sorttype byJobNo = PCB.sorttype.JOB_NUMBER;
    static long startTime;

    public static void main(String[] a) {
//
//        stmTest stm = new stmTest();
//        stmTest stm2 = new stmTest();
//        stmTest stm3 = new stmTest();
//        stmTest stm4= new stmTest();
//
//        threadTest threadTest1 = new threadTest();
//        threadTest threadTest2 = new threadTest();
//        threadTest threadTest3 = new threadTest();
//        threadTest threadTest4 = new threadTest();
//
//        Thread thread1 = new Thread (threadTest1);
//        Thread thread2 = new Thread (threadTest2);
//        Thread thread3 = new Thread (threadTest3);
//        Thread thread4 = new Thread (threadTest4);
//
//        startTime = System.currentTimeMillis();
//        for (int i = 0; i < 30; i++)
//        {
//            stm.thread();
//            stm2.thread();
//            stm3.thread();
//            stm4.thread();
//        }
//
//        long elapsedTimeMillis = System.currentTimeMillis()-startTime;
//        System.out.println("Time for STM: " + elapsedTimeMillis);
//
//        startTime = System.currentTimeMillis();
////        for (int i = 0; i < 30; i++)
////        {
//            thread1.start();
//            thread2.start();
//            thread3.start();
//            thread4.start();
////        }
//        elapsedTimeMillis = System.currentTimeMillis()-startTime;
//        System.out.println("Time for thread: " + elapsedTimeMillis);

        startTime = System.currentTimeMillis();
        String program = "program.txt";

        cpu = new Cpu(ram);
        cpu1 = new Cpu(ram);
        cpu2 = new Cpu(ram);
        cpu3 = new Cpu(ram);
        cpu4 = new Cpu(ram);
        sts = new ShortTermScheduler();
        lts = new LongTermScheduler();
        loader = new Loader(program);

        try {
           loader.Start();
        } catch(IOException e) {
            System.out.println("Your program file was not found. Please rename the file to 'program.txt' and place it in the root directory of this project!");
        }

//        cpu1.setJob(pcb.getPCB(1));
//        cpu2.setJob(pcb.getPCB(2));
//        cpu3.setJob(pcb.getPCB(3));
//        cpu4.setJob(pcb.getPCB(4));

//        cpu1.thread(pcb.getPCB(1));
//        cpu2.thread(pcb.getPCB(2));
//        cpu3.thread(pcb.getPCB(3));
//        cpu4.thread(pcb.getPCB(4));

//        Thread cpuThread1 = new Thread (cpu1);
//        Thread cpuThread2 = new Thread (cpu2);
//        Thread cpuThread3 = new Thread (cpu3);
//        Thread cpuThread4 = new Thread (cpu4);
//
//        cpuThread1.start();
//        cpuThread2.start();
//        cpuThread3.start();
//        cpuThread4.start();

//      FIFO SCHEDULING
        lts.loadJobs(byJobNo);
        System.out.println("*************STARTING FIFO SCHEDULING*************");
        sts.FIFOSchedule();
        sts.printWaitingTimes(FIFO);
        pcb.clearStatus();

//      PRIORITY SCHEDULING
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


