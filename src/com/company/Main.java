package com.company;

import java.util.List;

public class Main {
    public static Disk disk = new Disk();
    public static PCB pcb = new PCB();

    public static void main(String[] a) {
        String program = "program.txt";
        Loader loader = new Loader(program);

        int pcbSize = pcb.getNumberOfJobs();

        for(int i = 1; i <= pcbSize; i++) {
            System.out.println(pcb.getPCB(i).toString());
        }
    }
}