package com.company;

/**
 * Created by amanbhimani on 4/16/15.
 */
public class Pager {

    public Pager() {

    }

    public int needMemAddress(int jobNo, int index) {

        int page = index / 4;
        int instruction = index % 4;

        if(Driver.pcb.getPCBSortStatus() != Driver.byJobNo) {
            Driver.pcb.sortPCB(Driver.byJobNo);
        }

        if(!Driver.pcb.getPCB(jobNo).getPage(page).inMemory) {
            framePage(jobNo, page);
        }

        return Driver.pcb.getPCB(jobNo).getPage(page).ram[instruction];
    }

    public void framePage(int jobNo, int page) {
        int nextRamSlot;

        if(Driver.ram.freeFrames.empty()) {
            Driver.ram.freeFrames.push((Integer) Driver.ram.fullFrames.remove());
        }
        nextRamSlot = ((Integer) Driver.ram.freeFrames.pop() * 4);
        Driver.pcb.getPCB(jobNo).getPage(page).setRam(nextRamSlot);

        Driver.ram.fullFrames.add((nextRamSlot / 4));

        for(int i = 0; i < Driver.pcb.getPCB(jobNo).getPage(page).ram.length; i++) {
            Driver.ram.writeRam(Driver.disk.readDisk(Driver.pcb.getPCB(jobNo).getPage(page).disk[i]), nextRamSlot++);
        }
    }

    public void initialFrames() {
        for(int job = 1; job <= 30; job++) {
            for(int page = 0; page < 4; page++) {
                framePage(job, page);
            }
        }
    }
}
