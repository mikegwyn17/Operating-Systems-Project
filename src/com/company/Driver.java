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
    static long startTime;

    public static void main(String[] a) {
        startTime = System.currentTimeMillis();
        String program = "program.txt";
        cpu = new Cpu(ram);



        sts = new ShortTermScheduler();
        lts = new LongTermScheduler();

        loader = new Loader(program);

        int pcbSize = pcb.getNumberOfJobs();

        try {
           loader.Start();
        } catch(IOException e) {
            System.out.println("Your program file was not found. Please rename the file to 'program.txt' and place it in the root directory of this project!");
        }

        System.out.println(pcbSize);


        lts.loadJobs(byJobNo);

        System.out.println("*************STARTING FIFO SCHEDULING SCHEDULING*************");
        sts.FIFOSchedule();
        pcb.clearRanStatus();

        startTime = System.currentTimeMillis();
        lts.loadJobs(byPriority);

        System.out.println("\n\n\n\n*************STARTING PRIORITY SCHEDULING*************");
        sts.PrioritySchedule();

        sts.printWaitingTimes();

//        for(int i = 1; i <= 30; i++) {
//            System.out.println(pcb.getPCB(i).toString());
//        }

        if(loader.executed) {
            System.out.println("\nAll jobs have been loaded on to the Disk.\nYour disk is " + df.format(disk.diskPercent()) + " filled.");
        }

//      These comments print the whole PCB. Only used for debugging purposes.
//        for(int i = 1; i <= pcbSize; i++) {
//            System.out.println(pcb.getPCB(i).toString());
//        }
//        for (int i = 1; i <= pcbSize; i++)
//        {
//            cpu.loadCpu(pcb.getPCB(i));
//        }
    }
}