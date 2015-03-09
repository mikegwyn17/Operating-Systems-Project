package com.company;
import java.util.ArrayList;

/**
 * Created by amanbhimani on 2/23/15.
 */
public class PCB {
    private ArrayList<PCBObject> pcb;
    public enum sorttype { JOB_NUMBER, JOB_PRIORITY }
    sorttype sortType;

    public PCB() {
        pcb = new ArrayList<PCBObject>(30);
        sortType = sorttype.JOB_NUMBER;
    }

    public boolean insert(PCBObject o) {
        try {
            pcb.add(o);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public PCBObject getPCB(int jobNumber) {
        return pcb.get(jobNumber - 1);
    }

    public sorttype getPCBSortStatus() {
        return sortType;
    }

    public void sortPCB(sorttype s) {
        switch(s) {
            case JOB_NUMBER:
                //TODO: make an algorithm to sort the PCB.
                break;
            case JOB_PRIORITY:
                //TODO: make an algorithm to sort the PCB with job priority.
                break;
        }
    }

    public int getNumberOfJobs() { return pcb.size(); }
}
