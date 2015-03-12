package com.company;

import java.util.List;

public class Driver {
    public static Disk disk = new Disk();
    public static PCB pcb = new PCB();
    public static Ram ram = new Ram();
    public static ShortTermScheduler sts = new ShortTermScheduler(pcb);
    public static void main(String[] a) {
        String program = "program.txt";
        Loader loader = new Loader(program);
        Cpu cpu = new Cpu(disk);

        int pcbSize = pcb.getNumberOfJobs();

        for(int i = 1; i <= pcbSize; i++)
        {
            System.out.println(pcb.getPCB(i).toString());
        }
        for (int i = 1; i <= pcbSize; i++)
        {
            cpu.loadCpu(pcb.getPCB(i));
        }
    }
}