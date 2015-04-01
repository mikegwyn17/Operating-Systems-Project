package com.company;

/**
 * Created by D on 2/3/2015.
 */
public class Disk {
    private String[] disk;
    int diskFilled = 0;

    //Each sector in the disk array will have a string data.
    public Disk() {
        disk = new String[4096];
        //data will be null when 1st create a disk
    }
    public String readDisk(int index){
        return disk[index];
    }

    public void writeDisk(String s, int index) {
        if(disk[index] == null) {
            diskFilled++;
        }
        //read string from a text file and write it into disk
        disk[index] = s;
    }
    public void copyDisk(String s)
    {
        //this may not be needed
    }

    public void eraseBlock(int index) {
        if(disk[index] == null) {

        } else {
            disk[index] = null;
            diskFilled--;
        }
    }

    public float diskPercent() {
        return (float) (diskFilled / 4096.00);
    }
}

