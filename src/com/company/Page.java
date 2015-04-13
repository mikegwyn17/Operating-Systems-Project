package com.company;

/**
 * Created by amanbhimani on 4/12/15.
 */
public class Page {

    int[] ram;
    int[] disk;
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
        for(int i = 0; i < disk.length; i++) {
            disk[i] = s++;
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

    public void printPage() {
        for(int i = 0; i < 4; i++) {
            System.out.println("Ram" + i + ": " + ram[i]);
            System.out.println("Disk" + i + ": " + disk[i] + "Data: " + Driver.disk.readDisk(disk[i]));
        }
    }
}
