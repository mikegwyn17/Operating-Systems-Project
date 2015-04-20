package com.company;

/**
 * Created by amanbhimani on 4/12/15.
 */
public class Page {

    private int[] ram;
    private int[] disk;
    boolean inMemory;

    public Page() {
        ram = new int[4];
        disk = new int[4];
        inMemory = false;
    }

    public void setDisk(int s) {
        for(int i = 0; i < disk.length; i++) {
            disk[i] = s++;
        }
    }

    public void setRam(int s) {
        for(int i = 0; i < ram.length; i++) {
            ram[i] = s++;
        }
    }

    public int returnRAM(int i) {
        return ram[i];
    }

    public int returnDisk(int i) {
        return disk[i];
    }

    public int returnPageBaseDisk() {
        return disk[0];
    }

    public int returnPageBaseRam() {
        return ram[0];
    }

    public int ramLength() { return ram.length; }

    public void clearRam() {
        for(int i = 0; i < ram.length; i++) {
            ram[i] = -1;
        }
    }

    public void printPage() {
        for(int i = 0; i < 4; i++) {
            if(inMemory) {
                System.out.println("Ram" + i + ": " + ram[i] + " \t\tData: " + Driver.ram.readRam(ram[i]));
            } else {
                System.out.println("Ram" + i + ": " + ram[i] + " \t\tData: N/A");
            }

            System.out.println("Disk" + i + ": " + disk[i] + " \t\tData: " + Driver.disk.readDisk(disk[i]));
        }
    }
}
