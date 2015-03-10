package com.company;

/**
 * Created by D on 2/3/2015.
 */
public class Disk {
    private String data;
    private String[] disk;

    //Each sector in the disk array will have a string data.
    public Disk()
    {
        data = null;
        disk = new String[4096];
        //data will be null when 1st create a disk
    }
    public String readDisk(int index){
        return disk[index];
    }

    public void writeDisk(String s, int index)
    {
        disk[index] = s;
        //read string from a text file and write it into disk
    }
    public void copyDisk(String s)
    {
        //this may not be needed
    }
}

