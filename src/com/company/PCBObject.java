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

    public int getJobNumber() { return jobNumber; }
    public int getJobPriority() { return jobPriority; }
    public int getJobDiskAddress() { return jobDiskAddress; }
    public int getJobMemoryAddress() { return jobMemoryAddress; }
    public int getDataDiskAddress() { return dataDiskAddress; }
    public int getDataMemoryAddress() { return dataMemoryAddress; }
    public int getInstructionCount() { return instructionCount; }

    public PCBObject(int jobNumber, int jobPriority, int jobDiskAddress, int instructionCount) {
        this.jobNumber = jobNumber;
        this.jobPriority = jobPriority;
        this.jobDiskAddress = jobDiskAddress;
        this.instructionCount = instructionCount;
    }

    public void setDataDiskAddress(int k) {
        dataDiskAddress = k;
    }
    public void setJobMemoryAddress(int k) {
        jobMemoryAddress = k;
    }
    public void setDataMemoryAddress(int k) {
        dataMemoryAddress = k;
    }
}
