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

    // This method loads the initial jobs in the RAM. This is called by the driver.
    // Based on the algorithm, it depends which jobs are loaded into RAM at first
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
    
     // This method takes one job and puts it into memory depending on where there is space
    // If there is no space, then it takes a job out and replaces it with one thats needed.
    public void needJobInMemory(PCBObject j) {

        if(Driver.pcb.getPCBSortStatus() != Driver.byJobNo) {
            Driver.pcb.sortPCB(byJobNo);
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
