package com.company;

/**
 * Created by evanross on 2/23/15.
 */
public class Ram {

    private String data;
    private String[] RAM;

    public Ram(){
        data = null;
        RAM = new String[1024];
    }
    public String returnData(int index){
        return RAM[index];
    }
    public String readRam(int index){
        return RAM[index];
    }
    public void writeRam(String s, int index){
        RAM[index] = s;
    }

}
