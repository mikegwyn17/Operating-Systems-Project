package com.company;

/**
 * Created by D on 2/3/2015.
 */
public class Disk {
    private String data;
    //Each sector in the disk array will have a string data.
    public Disk()
    {
        data = null;
        //data will be null when 1st create a disk
    }
    public String returnData()
    {
        return this.data;
        //return a disk data in that sector in the array
    }
    public String readDisk()
    {
        String s = disk.returnData();
        return s;
        //read a data from a disk and copy it into RAM
    }
    public void writeDisk(String s)
    {
        this.data = s;
        //read string from a text file and write it into disk
    }
    public void copyDisk(String s)
    {
        //this may not be needed
    }
}

