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
    //AMANS CODE ENDS

//
//
//    public void checkType() {
//        int k = Driver.pcb.getNumberOfJobs();
//
//        for (int i = 0; i < k; i++) {
//            if(Job.getInstruction() == "00" || Job.getInstruction() == "01") {
//                Job.setIObound(true);
//            }
//            else {
//                Job.setIObound(false);
//            }
//        }
//    }
//
//    public void beginSchedule()
//    {
//        int k = Driver.pcb.getNumberOfJobs();
//        int IOboundInside =0;
//        int CPUboundInside=0;
//        //loop check
//        while(IOboundInside - CPUboundInside <=2 || IOboundInside - CPUboundInside >= -2) {
//            for (int i = 1; i <= k; i++) {
//                readyQueue.add(Driver.pcb.getPCB(i));
//                if(Job.checkIOBound() == true){
//                    IOboundInside++;
//                }
//                else{
//                    CPUboundInside++;
//                }
//            }
//        }
//    }
//
//    /*
//    readyQueue.add(Driver.pcb.getPCB(i));
//                    if(Job.checkIOBound() == true){
//                        IOboundInside++;
//                    }
//                    else{
//                        CPUboundInside++;
//                    }
//     */
//    public void PrioritySchedule(){
//
//        int numOfJobs = Driver.pcb.getNumberOfJobs();
//
//    }
//
//    /*
//    public void PrioritySchedule () {
//        int noOfJobs = Driver.pcb.getNumberOfJobs();
//
//        if(Driver.pcb.getPCBSortStatus() != byPriority) {
//            Driver.pcb.sortPCB(byPriority);
//        }
//
//        if(!readyQueue.isEmpty()) {
//            readyQueue.clear();
//        }
//
//        for(int i = 1; i < noOfJobs+1; i++) {
//            readyQueue.add(Driver.pcb.getPCB(i));
//        }
//
//        runCpu();
//    }
//
//     */
//
//    public void RamPercent(int p){
//
//        switch (p){
//            case 0:
//                average = (ramSize - ramLeft);
//                percent = (average / ramSize);
//                System.out.println("The percentage of RAM used is:" + (percent * 100));
//                break;
//            case 1:
//                Driver.totalPercent = Driver.sumPercent / Driver.counter;
//                System.out.println("The average percent of RAM used is:" + Driver.totalPercent * 100);
//                System.out.println("The average RAM percentage is:" + Driver.totalPercent * 100);
//                break;
//
//        }
//    }

