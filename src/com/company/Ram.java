package com.company;

/**
 * Created by evanross on 2/23/15.
 */
public class Ram {

    private int ramData = 0;
    private String[] RAM;
    public static int ramFilled;

    public Ram(){
        RAM = new String[1024];
        ramFilled = 0;
    }

    public String readRam(int index){
        return RAM[index];
    }
    public void writeRam(String s, int index){

        if(RAM[index] == null) {
            ramFilled++;
        }
        RAM[index] = s;
    }

    public float getRamFilled() {
        return ramFilled / 1024.00f;
    }

    public int getRamSize() {
        return RAM.length;
    }
}
