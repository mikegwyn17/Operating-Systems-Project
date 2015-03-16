package com.company;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by amanbhimani on 2/23/15.
 */
public class PCB {
    private ArrayList<PCBObject> pcb;

    public enum sorttype { JOB_NUMBER, JOB_PRIORITY }
    sorttype sortType;

    public PCB() {
        pcb = new ArrayList<PCBObject>();
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

    public PCBObject getPCB(int index) {
        return pcb.get(index - 1);
    }

    public sorttype getPCBSortStatus() {
        return sortType;
    }

    public void sortPCB(sorttype s) {
        switch(s) {
            case JOB_PRIORITY:
                Collections.sort(pcb, new Comparator<PCBObject>() {
                    @Override
                    public int compare(PCBObject o1, PCBObject o2) {
                        return o1.getJobPriority() - o2.getJobPriority();
                    }
                });

                sortType = sorttype.JOB_PRIORITY;

                break;
            case JOB_NUMBER:
                Collections.sort(pcb, new Comparator<PCBObject>() {
                    @Override
                    public int compare(PCBObject o1, PCBObject o2) {
                        return o1.getJobNumber() - o2.getJobNumber();
                    }
                });
                sortType = sorttype.JOB_NUMBER;
                break;
        }
    }

    public void clearRanStatus() {
        for(PCBObject b : pcb) {
            b.setHasJobRan(false);
        }
    }

    public int getNumberOfJobs() { return pcb.size(); }
}

