package com.company;

/**
 * Created by amanbhimani on 4/12/15.
 */
public class Page {

    private int[] ram;
    private int[] disk;
    boolean inMemory;
    long pageServiceTime;
//This initializes the page with a new RAM and DISK arrays. These store the memory addresses in RAM and DISK respectively
    public Page() {
        ram = new int[4];
        disk = new int[4];
        inMemory = false;
        pageServiceTime = 0;
    }
//This takes the address provided and sets it for the next 4 words
    public void setDisk(int s) {
        for(int i = 0; i < disk.length; i++) {
            disk[i] = s++;
        }
    }

    //This takes the address provided and sets it for the next 4 words in RAM

    public void setRam(int s) {
        for(int i = 0; i < ram.length; i++) {
            ram[i] = s++;
        }
    }

    //This returns the address at the slot provided in RAM (0-3)
    //The number means that it needs the nth word in this page.
    public int returnRAM(int i) {
        return ram[i];
    }
    //This returns the address at the slot provided in Disk (0-3)
    //The number means that it needs the nth word in this page.
    public int returnDisk(int i) {
        return disk[i];
    }

    //Returns the length of the RAM array. It is always 4.
    public int ramLength() { return ram.length; }

    //Sets and gets the page service times for this page.

    public void setPageServiceTime(long s) {
        pageServiceTime = s;
    }
    public long getPageServiceTime() {
        return pageServiceTime;
    }
    //Clears the RAM array so all of the addresses are 0.

    public void clearRam() {
        for(int i = 0; i < ram.length; i++) {
            ram[i] = 0;
        }
    }
    
    //Prints the page.
    public void printPage() {
        for(int i = 0; i < 4; i++) {
            if(inMemory) {
                System.out.println("Ram" + i + ": " + ram[i] + " \t\tData: " + Driver.ram.readRam(ram[i]));
            } else {
                System.out.println("Ram" + i + ": " + ram[i] + " \t\tData: N/A");
            }

            System.out.println("Disk" + i + ": " + disk[i] + " \t\tData: " + Driver.disk.readDisk(disk[i]));
            System.out.println("Fault Service Time: " + getPageServiceTime() + "ns");
        }
    }
}
