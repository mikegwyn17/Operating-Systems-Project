package com.company;
import java.util.ArrayList;


/**
 * Created by evanross on 2/23/15.
 */
public class LongTermScheduler {

    public ArrayList<PCBObject> readyQueue;
    private final int ramSize = 1024;
    private int ramLeft = 1024;
    public PCBObject Job;
    private double average;
    public double percent;
    static PCB.sorttype byPriority = PCB.sorttype.JOB_PRIORITY;
    static PCB.sorttype byJobNo = PCB.sorttype.JOB_NUMBER;

    private final int dataSize = 44;

    //instruction code

    public LongTermScheduler() {

    }

    public void loadJobs(PCB.sorttype s) {
        int index = 0;

        if (s == byJobNo) {
            if (Driver.pcb.getPCBSortStatus() != byJobNo) {
                Driver.pcb.sortPCB(byJobNo);
            }
        } else {
            if (Driver.pcb.getPCBSortStatus() != byPriority) {
                Driver.pcb.sortPCB(byPriority);
            }
        }

        PCBObject j;
        int totalSpace, k;
        for (int i = 1; i <= 30; i++) {
            j = Driver.pcb.getPCB(i);
            totalSpace = j.getInstructionCount() + dataSize;

            if ((Driver.ram.getRamSize() - (index + totalSpace)) >= 0) {

                int instructionCount = j.getInstructionCount();

                j.setJobMemoryAddress(index);
                j.setDataMemoryAddress(index + instructionCount);
                j.setJobInMemory(true);

                for (k = j.getJobMemoryAddress(); k < j.getJobMemoryAddress() + totalSpace; k++) {
                    Driver.ram.writeRam(Driver.disk.readDisk(k), index);
                    index++;
                }
            }
        }
    }

    public void needJobInMemory(PCBObject j) {
        int index = 0, k;
        int instructionCount = j.getInstructionCount();
        int totalSpace = j.getInstructionCount() + 44;

        j.setJobMemoryAddress(index);
        j.setDataMemoryAddress(index + instructionCount);
        j.setJobInMemory(true);

        for (k = j.getJobMemoryAddress(); k < j.getJobMemoryAddress() + totalSpace; k++) {
            Driver.ram.writeRam(Driver.disk.readDisk(k), index);
            index++;
        }
    }
}
