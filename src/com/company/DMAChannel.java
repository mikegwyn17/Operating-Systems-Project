package com.company;

/**
 * Created by Michael on 3/11/2015.
 */
public class DMAChannel
{
    public Pager pager;
    public DMAChannel()
    {
        pager = new Pager();
    }

    // read and return data from cache as an integer value
    public long readNCpu (int address, int j)
    {
        int returnedInstruction;
        String instructionInRam;
        long longInstruction;
        int ramIndex = pager.needMemAddress(j,address);
        instructionInRam = Driver.ram.readRam(ramIndex);
        instructionInRam = instructionInRam.substring(2);
        longInstruction = Long.parseLong(instructionInRam, 16);
        returnedInstruction = (int) longInstruction;

        return returnedInstruction;
    }

    // Writes passed address data into RAM as a hex String
    public void write (long buffer, int data, int j)
    {
        String stringInstruction = "0x";
        String hexInstruction = Integer.toHexString(data);
        while (hexInstruction.length() != 8) {
            hexInstruction = "0" + hexInstruction.substring(0);
        }
        stringInstruction = stringInstruction.concat(hexInstruction);
        int ramIndex = pager.needMemAddress(j,(int)buffer);
        Driver.ram.writeRam(stringInstruction,ramIndex);
        System.out.println("Output buffer contents: " + Driver.ram.readRam(ramIndex));
    }
}
