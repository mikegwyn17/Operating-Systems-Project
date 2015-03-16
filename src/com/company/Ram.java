package com.company;

/**
 * Created by evanross on 2/23/15.
 */
public class Ram {

    private int ramData = 0;
    private String[] RAM;
    private int ramFilled = 0;

    public Ram(){
        RAM = new String[1024];
    }

    public String readRam(int index){
        return RAM[index];
    }
    public void writeRam(String s, int index){

        if(RAM[index]==null){
            ramFilled++;
        }
        RAM[index] = s;
    }

    public int getRamFilled() {
        return ramFilled;
    }

    public void clearRam() {
        ramFilled = 0;
    }

    public int getRamSize() {
        return RAM.length;
    }
}
