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
    public int read (PCBObject Job, int address)
    {
        address = address + Job.getDataMemoryAddress();
        String ramThing = memory.readRam(address);
        ramThing = ramThing.substring(2);
        return Integer.parseInt(ramThing,16);
    }

    public void write (PCBObject Job, int address, int buffer)
    {
        String ramThing = "0x";

        String ramThing2 = Integer.toHexString(address);
        ramThing = ramThing2.concat(ramThing2);
        System.out.println(ramThing);
        memory.writeRam(ramThing,buffer);
        System.out.println("Output buffer contents: " + memory.readRam(buffer));
    }
}
