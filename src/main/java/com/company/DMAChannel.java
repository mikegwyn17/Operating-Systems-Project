package com.company;

/**
 * Created by Michael on 3/11/2015.
 */
public class DMAChannel
{
    public DMAChannel()
    {

    }

//  read and return data from RAM as an integer value
    public long read (PCBObject Job,long address)
    {
        int otherReturnThing;
        String ramThing;
        long returnThing;
        int workThing = Job.getJobMemoryAddress();
        address  = address + workThing;
        ramThing = Driver.ram.readRam((int) address);
        ramThing = ramThing.substring(2);
        returnThing = Long.parseLong(ramThing, 16);
        otherReturnThing = (int) returnThing;

        return otherReturnThing;
    }

// read and return data from cache as an integer value
    public long readNCpu (long address, String[] cache)
    {
        int otherReturnThing;
        String ramThing;
        long returnThing;
        ramThing = cache[(int)address];
        ramThing = ramThing.substring(2);
        returnThing = Long.parseLong(ramThing, 16);
        otherReturnThing = (int) returnThing;

        return otherReturnThing;
    }

// Writes passed address data into RAM as a hex String
    public synchronized void write (PCBObject Job, long buffer, int data)
    {
        String ramThing = "0x";
        String ramThing2 = Integer.toHexString(data);
        while (ramThing2.length() != 8) {
            ramThing2 = "0" + ramThing2.substring(0);
        }
        ramThing = ramThing.concat(ramThing2);
        Driver.ram.writeRam(ramThing,(int)buffer);
        System.out.println("Output buffer contents: " + Driver.ram.readRam((int)buffer));
    }
}
