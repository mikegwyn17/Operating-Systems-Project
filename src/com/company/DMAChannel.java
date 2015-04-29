package com.company;

/**
 * Created by Michael on 3/11/2015.
 */
public class DMAChannel
{
    public DMAChannel()
    {

    }

    // read and return data from cache as an integer value
    public long readCpu(long address, String[] cache)
    {
        int intInstruction;
        String stringInstruction;
        long returnThing;
        stringInstruction = cache[(int)address];
        stringInstruction = stringInstruction.substring(2);
        returnThing = Long.parseLong(stringInstruction, 16);
        intInstruction = (int) returnThing;

        return intInstruction;
    }

    // Writes passed address data into RAM as a hex String
    public void write (long buffer, int data)
    {
        String stringInstruction = "0x";
        String hexInstruction = Integer.toHexString(data);
        while (hexInstruction.length() != 8) {
            hexInstruction = "0" + hexInstruction.substring(0);
        }
        stringInstruction = stringInstruction.concat(hexInstruction);
        Driver.ram.writeRam(stringInstruction,(int)buffer);
        System.out.println("Output buffer contents: " + Driver.ram.readRam((int)buffer));
    }
}