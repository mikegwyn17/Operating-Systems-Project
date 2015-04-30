package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by evanross on 2/23/15.
 */

class RamSlot {
    private String data;
    boolean dirty;
    boolean empty;
    int pageNo;
    int jobNo;

    public RamSlot() {
        dirty = false;
        empty = true;
        pageNo = 0;
        jobNo = 0;
    }

    public void write(String d) {
        data = d;
    }

    public String readData() {
        return data;
    }

    public void deleteSlot() {
        data = "";
        empty = true;
        dirty = false;
    }

    public int getPageNo() {
        return pageNo;
    }
}

public class Ram {

    private int ramData = 0;
    private RamSlot[] RAM;
    public static int ramFilled;

    Stack freeFrames;
    Queue<Integer> fullFrames;


    public Ram(){
        RAM = new RamSlot[1024];
        ramFilled = 0;
        freeFrames = new Stack();
        fullFrames = new LinkedList<Integer>();

        for(int i = 0; i < RAM.length; i++) {
            RAM[i] = new RamSlot();
        }

        for(int i = 0; i < 256; i++) {
            freeFrames.push(new Integer(i));
        }
    }

    public String readRam(int index){
        return RAM[index].readData();
    }
    public void writeRam(String s, int index){
        if(RAM[index].empty == true) {
            ramFilled++;
        }
        RAM[index].write(s);
        RAM[index].empty = false;
    }

    //This returns the page number and job number that is in this ram slot
    public int getPageNo(int slot) {
        return RAM[slot].getPageNo();
    }
    public int getJobNo(int slot) {
        return RAM[slot].jobNo;
    }

    //This sets the page number for this ram slot. This is needed later when we try to clear the ram slot
    public void setPageNo(int slot, int job, int page) {
        RAM[slot].pageNo = page;
        RAM[slot].jobNo = job;
    }

    //Clears the page number and makes it -1 so it seems there is no page in this ram slot.
    public void clearPageNo(int slot) {
        RAM[slot].pageNo = -1;
    }

    //Returns the RAM percentage used in a floating point format
    public float getRamFilled() {
        float number = 0.0f;
        for(RamSlot s : RAM) {
            if(!s.empty) {
                number += 1.0f;
            }
        }

        return number / 1024.0f;
    }

    //Clears all ram slots
    public void clearRam() {
        for(RamSlot s : RAM) {
            s.deleteSlot();
        }
    }

    //returns the ram size (1024)
    public int getRamSize() {
        return RAM.length;
    }
}
