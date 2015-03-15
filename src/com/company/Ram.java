package com.company;

/**
 * Created by evanross on 2/23/15.
 */
public class Ram {

    private int ramData;
    private String[] RAM;

    public Ram(){ RAM = new String[1024]; }


    public String returnData(int index){
        return RAM[index];
    }
    public String readRam(int index){
        return RAM[index];
    }
    public void writeRam(String s, int index){

        if(RAM[index]==null){
            ramData++;
        }
        s = RAM[index];
    }

}
