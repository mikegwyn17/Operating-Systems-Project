package com.company;

/**
 * Created by Michael on 3/11/2015.
 */
public class DMAChannel
{
    public Ram memory;
    public DMAChannel(Ram ram)
    {
        memory = ram;
    }

//  read and return data from RAM as an integer value
    public long read (PCBObject Job,long address)
    {
        int otherReturnThing;
        String ramThing;
        long returnThing;
        int workThing = Job.getJobMemoryAddress();
        address  = address + workThing;
//        if (address < 0)
//        {
            ramThing = memory.readRam((int) address);
            ramThing = ramThing.substring(2);
            returnThing = Long.parseLong(ramThing, 16);
            otherReturnThing = (int) returnThing;
//        }
//        else
//        {
//            address = address + Job.getDataMemoryAddress();
//            ramThing = memory.readRam((int) address);
//            ramThing = ramThing.substring(2);
//            returnThing = Long.parseLong(ramThing, 16);
//            otherReturnThing = (int) returnThing;
//        }
        return otherReturnThing;
    }

// Writes passed address data into RAM as a hex String
    public void write (PCBObject Job, long buffer, int data)
    {
        //int address = Job.getDataMemoryAddress();
        String ramThing = "0x";
        String ramThing2 = Integer.toHexString(data);
        while (ramThing2.length() != 8) {
            ramThing2 = "0" + ramThing2.substring(0);
        }
        ramThing = ramThing.concat(ramThing2);
        memory.writeRam(ramThing,(int)buffer);
        System.out.println("Output buffer contents: " + memory.readRam((int)buffer/2));
    }
}
