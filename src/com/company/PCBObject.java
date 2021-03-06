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

    public PCBObject(int jobNumber, int jobPriority, int jobDiskAddress, int instructionCount) {
        this.jobNumber = jobNumber;
        this.jobPriority = jobPriority;
        this.jobDiskAddress = jobDiskAddress;
        this.instructionCount = instructionCount;
        jobInMemory = false;
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

    @Override
    public String toString() {
        return "\n\n*****- JOB " + jobNumber + " INFO -*****\n" + "Job Priority: " + jobPriority + ", Instructions: " + instructionCount + ", Disk Address: " + jobDiskAddress + ", Memory Address: " + jobMemoryAddress + "\n*****- DATA INFO -*****\nDisk Address: " + dataDiskAddress + ", Memory Address: " + dataMemoryAddress + "\n*****- BUFFERS -*****\nInput: " + inputBuffer + ", Output: " + outputBuffer + ", Temporary: " + temporaryBuffer + "In Memory: " + jobInMemory + "Ran: " + hasJobRan;
    }
}
