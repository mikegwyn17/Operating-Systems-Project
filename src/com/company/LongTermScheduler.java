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

    private final int dataSize = 44;

    //instruction code

    public LongTermScheduler() {

    }

    public void loadJobs(PCB.sorttype s) {
        int index = 0;

        if (s == Driver.byJobNo) {
            if (Driver.pcb.getPCBSortStatus() != Driver.byJobNo) {
                Driver.pcb.sortPCB(Driver.byJobNo);
            }
        } else if (s == Driver.byPriority) {
            if (Driver.pcb.getPCBSortStatus() != Driver.byPriority) {
                Driver.pcb.sortPCB(Driver.byPriority);
            }
        } else {
            if (Driver.pcb.getPCBSortStatus() != Driver.byShortestJob) {
                Driver.pcb.sortPCB(Driver.byShortestJob);
            }
        }

        int totalSpace, k;
        for (int i = 1; i <= 30; i++) {
            totalSpace = Driver.pcb.getPCB(i).getInstructionCount() + dataSize;

            int j = Driver.pcb.getPCB(i).getJobNumber();

            if ((Driver.ram.getRamSize() - (index + totalSpace)) >= 0) {

                int instructionCount = Driver.pcb.getPCB(i).getInstructionCount();

                Driver.pcb.getPCB(i).setJobMemoryAddress(index);
                Driver.pcb.getPCB(i).setDataMemoryAddress(index + instructionCount);
                Driver.pcb.getPCB(i).setJobInMemory(true);

                for (k = Driver.pcb.getPCB(i).getJobDiskAddress(); k < Driver.pcb.getPCB(i).getJobDiskAddress() + totalSpace; k++) {
                    Driver.ram.writeRam(Driver.disk.readDisk(k), index);
                    index++;
                }
            }
        }
    }

    public void needJobInMemory(PCBObject j) {

        if(Driver.pcb.getPCBSortStatus() != Driver.byJobNo) {
            Driver.pcb.sortPCB(Driver.byJobNo);
        }

        int jobNumber = j.getJobNumber();
        int index = 0, k = 0;
        int instructionCount = Driver.pcb.getPCB(jobNumber).getInstructionCount();
        int totalSpace = Driver.pcb.getPCB(jobNumber).getInstructionCount() + 44;

        Driver.pcb.getPCB(jobNumber).setJobMemoryAddress(index);
        Driver.pcb.getPCB(jobNumber).setDataMemoryAddress(index + instructionCount);
        Driver.pcb.getPCB(jobNumber).setJobInMemory(true);

        for (k = Driver.pcb.getPCB(jobNumber).getJobDiskAddress(); k < j.getJobDiskAddress() + totalSpace; k++) {
            Driver.ram.writeRam(Driver.disk.readDisk(k), index);
            index++;
        }
    }
}
