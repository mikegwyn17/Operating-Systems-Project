package com.company;

/**
 * Created by amanbhimani on 2/23/15.
 */
public class PCBObject {
    private int jobNumber;
    private int jobPriority;
    private int jobDiskAddress;
    private int jobMemoryAddress;
    private int dataDiskAddress;
    private int dataMemoryAddress;
    private int instructionCount;
    private int inputBuffer;
    private int outputBuffer;
    private int temporaryBuffer;

    private boolean IObound; //IObound or not
    private String instruction; //instuction set for job
    private boolean jobInMemory;
    private boolean hasJobRan;
    private ProcessStatus pStatus;
    private PageTable pageTable;
    private int dataCount;

    public enum ProcessStatus { NEW, READY, WAITING, RUNNING, FINISHED, ERROR };

    public PCBObject(int jobNumber, int jobPriority, int jobDiskAddress, int instructionCount) {
        this.jobNumber = jobNumber;
        this.jobPriority = jobPriority;
        this.jobDiskAddress = jobDiskAddress;
        this.instructionCount = instructionCount;
        jobInMemory = false;
        pStatus = ProcessStatus.NEW;
        dataCount = 44;

        setUpPageTable();
    }

    public void pageFaultServiceTime() {
        long total = 0;
        for(int i = 0; i < getPageTableSize(); i++) {
            total += getPage(i).getPageServiceTime();
        }
        System.out.println("Job " + getJobNumber() + " Page Fault Service Time: " + total + "ns");
        //System.out.println(total);
    }

    private void setUpPageTable() {
        pageTable = new PageTable(getJobDiskAddress(), instructionCount, dataCount);
    }

    public void printPageTable() {
        pageTable.printPageTable();
    }

    public int getJobNumber() { return jobNumber; }
    public int getJobPriority() { return jobPriority; }
    public int getJobDiskAddress() {return jobDiskAddress; }
    public int getJobMemoryAddress() { return jobMemoryAddress; }
    public int getDataDiskAddress() { return dataDiskAddress; }
    public int getDataMemoryAddress() { return dataMemoryAddress; }
    public int getInstructionCount() { return instructionCount; }
    public int getInputBufferSize() { return inputBuffer; }
    public int getOutputBufferSize() { return outputBuffer; }
    public int getTemporaryBufferSize() { return temporaryBuffer; }
    public String getInstruction(){return instruction;}
    public boolean checkIOBound(){return IObound; }
    public boolean isInMemory(){ return jobInMemory; }
    public boolean hasJobRan() {return hasJobRan; }
    public ProcessStatus getProcessStatus() { return pStatus; }
    public int getPageTableSize() { return pageTable.numOfPages(); }

    public void setDataDiskAddress(int k) {
        dataDiskAddress = k;
    }
    public void setJobMemoryAddress(int k) {
        jobMemoryAddress = k;
    }
    public void setDataMemoryAddress(int k) {
        dataMemoryAddress = k;
    }
    public void setInputBuffer(int k) {inputBuffer = k;}
    public void setOutputBuffer(int k) {outputBuffer = k;}
    public void setTemporaryBuffer(int k) {temporaryBuffer = k;}
    public void setIObound(boolean k) {IObound = k; }
    public void setJobInMemory(boolean k) { jobInMemory = k; }
    public void setHasJobRan(boolean k) {hasJobRan = k; }
    public void setProcessStatus(ProcessStatus k) { pStatus = k; }

    public Page getPage(int index) {
        return pageTable.pages[index];
    }


    @Override
    public String toString() {
        return "\n\n*****- JOB " + jobNumber + " INFO -*****\n" + "Job Priority: " + jobPriority + ", Instructions: " + instructionCount + ", Disk Address: " + jobDiskAddress + ", Memory Address: " + jobMemoryAddress + "\n*****- DATA INFO -*****\nDisk Address: " + dataDiskAddress + ", Memory Address: " + dataMemoryAddress + "\n*****- BUFFERS -*****\nInput: " + inputBuffer + ", Output: " + outputBuffer + ", Temporary: " + temporaryBuffer + "In Memory: " + jobInMemory + "Ran: " + hasJobRan;
    }
}
