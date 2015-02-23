package com.company;

/**
 * Created by evanross on 2/23/15.
 */
public class Ram {
    private String data;
    private String[] ram = new String[1024];

    public Ram(){
        ram = null;
        //data will be null when 1st create ram
    }
    public  String returnData(int index){
        return ram[index];
        //return a disk data in that sector in the array
    }
    public String readRam(){
        //String s = this.returnData();
        System.out.print("test");
        return "s";
        //read a data from ram and copy it into CPU
    }
    public void writeRam(String s, int index){
        ram[index] = s;
        //read string from a text file and write it into CPU
    }
    public void copyRam(String s){
        //may or not be needed
    }
}
