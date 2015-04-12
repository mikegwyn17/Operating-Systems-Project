package com.company;

/**
 * Created by evanross on 2/23/15.
 */

class RamSlot {
    private String data;
    boolean dirty;
    boolean empty;

    public RamSlot() {
        dirty = false;
        empty = true;
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
}

public class Ram {

    private int ramData = 0;
    private RamSlot[] RAM;
    public static int ramFilled;



    public Ram(){
        RAM = new RamSlot[1024];
        ramFilled = 0;
    }

    public String readRam(int index){
        return RAM[index].readData();
    }
    public void writeRam(String s, int index){

        if(RAM[index].empty == true) {
            ramFilled++;
        }
        RAM[index].write(s);
    }

    public void deleteSlot(int index) {
        RAM[index].deleteSlot();
        ramFilled--;
    }

    public float getRamFilled() {
        return ramFilled / 1024.00f;
    }

    public int getRamSize() {
        return RAM.length;
    }
}
