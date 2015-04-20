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

    public void deleteSlot(int index) {
        RAM[index].deleteSlot();
        ramFilled--;
    }

    public int getPageNo(int slot) {
        return RAM[slot].getPageNo();
    }

    public void setPageNo(int slot, int job, int page) {
        RAM[slot].pageNo = page;
        RAM[slot].jobNo = job;
    }

    public void clearPageNo(int slot) {
        RAM[slot].pageNo = -1;
    }
    public int getJobNo(int slot) {
        return RAM[slot].jobNo;
    }

    public float getRamFilled() {
        return ramFilled / 1024.00f;
    }

    public int getRamSize() {
        return RAM.length;
    }
}
