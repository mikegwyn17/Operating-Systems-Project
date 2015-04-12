package com.company;

/**
 * Created by amanbhimani on 4/12/15.
 */
public class PageTable {
    public Page[] pages;
    int diskStartPoint;

    public PageTable(int diskStartPoint, int instructionCount, int dataCount) {
        int totalSpace = instructionCount + dataCount;
        this.diskStartPoint = diskStartPoint;

        if(totalSpace % 4 != 0) {
            pages = new Page[(totalSpace / 4) + 1];
        } else {
            pages = new Page[totalSpace / 4];
        }

        int temp = diskStartPoint;
        for(int i = 0; i < pages.length; i++) {
            pages[i] = new Page();
            pages[i].setDisk(temp);
            temp += 4;
        }
    }

    public void printPageTable() {
        for(int i = 0; i < pages.length; i++) {
            System.out.println("\n*********PAGE " + i + "*********");
            pages[i].printPage();
        }
    }
}
