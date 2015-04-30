package com.company;

/**
 * Created by amanbhimani on 4/16/15.
 */
public class Pager {

    long time, time2;
    long diffTime;

    public Pager() {
        time = 0;
        time2 = 0;
        diffTime = 0;

    }

    //Returns the memory address that is needed.
    //The job number and index for the instruction or data is provided. Depending on this, it finds the corect page and word in the page and returns the address.
    // If the page is not in memory, then it calls framePage() and puts the page into memory.
    public int needMemAddress(int jobNo, int index) {

        int page = index / 4;
        int instruction = index % 4;

        if(Driver.pcb.getPCBSortStatus() != Driver.byJobNo) {
            Driver.pcb.sortPCB(Driver.byJobNo);
        }

        if(!Driver.pcb.getPCB(jobNo).getPage(page).inMemory) {
            Driver.pcb.getPCB(jobNo).setProcessStatus(PCBObject.ProcessStatus.WAIT);
            Driver.pageFaultCount++;
            time = System.nanoTime();
            framePage(jobNo, page, true);
            time2 = System.nanoTime();
            diffTime = time2 - time;
            Driver.pcb.getPCB(jobNo).getPage(page).setPageServiceTime(diffTime);
            Driver.pcb.getPCB(jobNo).setProcessStatus(PCBObject.ProcessStatus.RUN);
        }

        return Driver.pcb.getPCB(jobNo).getPage(page).returnRAM(instruction);
    }

    //This method puts the page into RAM on a free slot.
    //If there are no free slots left then it takes out the oldest frame and replaces it with the one thats needed.
    public void framePage(int jobNo, int page, boolean fault) {
        int nextRamSlot, nextRamSlotPerm;
        int previousJob = -1, previousPage = -1;

        if(Driver.ram.freeFrames.empty()) {
            int s = Driver.ram.fullFrames.remove();

            previousJob = Driver.ram.getJobNo((s*4));
            previousPage = Driver.ram.getPageNo((s*4));

            Driver.ram.freeFrames.push(s);
        }
        nextRamSlot = ((Integer) Driver.ram.freeFrames.pop() * 4);
        nextRamSlotPerm = nextRamSlot;
        Driver.pcb.getPCB(jobNo).getPage(page).setRam(nextRamSlot);

        Driver.ram.fullFrames.add((nextRamSlot / 4));

        for(int i = 0; i < Driver.pcb.getPCB(jobNo).getPage(page).ramLength(); i++) {
            if(previousJob >= 0) {
                Driver.pcb.getPCB(previousJob).getPage(previousPage).clearRam();
                Driver.pcb.getPCB(previousJob).getPage(previousPage).inMemory = false;
                Driver.ram.clearPageNo(nextRamSlot);
            }

            Driver.ram.setPageNo(nextRamSlot, jobNo, page);
            Driver.ram.writeRam(Driver.disk.readDisk(Driver.pcb.getPCB(jobNo).getPage(page).returnDisk(i)), nextRamSlot++);
        }

        Driver.pcb.getPCB(jobNo).getPage(page).inMemory = true;
        Driver.ram.setPageNo(nextRamSlotPerm, jobNo, page);

        if(fault) Driver.pcb.getPCB(jobNo).addPageFault();

    }

    //Initially all jobs get 4 frames in RAM. It takes the first four pages and puts it into RAM non-contiguously.
    public void initialFrames() {
        for(int job = 1; job <= 30; job++) {
            for(int page = 0; page < 4; page++) {
                framePage(job, page, false);
            }
        }
    }
}
