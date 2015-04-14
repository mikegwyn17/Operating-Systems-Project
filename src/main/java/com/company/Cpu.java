package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;

/**
 * Created by Michael on 2/3/2015.
 */

class executeTimes
{
    int jobNo;
    long waitTime;

    public executeTimes(int j, long w)
    {
        jobNo = j;
        waitTime = w;
    }
}

public class Cpu
{
    // boolean used to end thread
    private volatile boolean running = true;

    // Registers for the cpu
    public int sReg1;
    public int sReg2;
    public int dReg;
    public int bReg;
    public int reg1;
    public int reg2;
    public int tempReg;
    public static int zero = 1;
    public static int accumulator = 0;
    public int[] regArray;

    public int opCode;
    public int instructionType;
    public long address;
    public static int ioCount;
    public long start;

    public int inputBufferSize;
    public int outputBufferSize;
    public int tempBufferSize;
    public int jobSize;
    public int cacheSize;

    public long cpuBuffer;

    public int pc;
    public boolean jumped;

    public PCBObject Job;
    public static int jobCount;
    public DMAChannel dma;
    public int jobNumber;
    public executeTimes times;

    public String[] cache;

    public Cpu()
    {
        regArray = new int[16];
        regArray[zero] = 0;
        jumped = false;
        jobCount = 1;
        dma = new DMAChannel();
        cache = new String[100];
    }

    public executeTimes loadCpu(PCBObject job)
    {
        start = System.currentTimeMillis();
        Job = job;
        jobNumber = Job.getJobNumber();
        ioCount = 0;
        tempBufferSize = Job.getTemporaryBufferSize();
        inputBufferSize = Job.getInputBufferSize();
        outputBufferSize = Job.getOutputBufferSize();
        jobSize = Job.getInstructionCount();
        cacheSize = tempBufferSize + inputBufferSize + outputBufferSize + jobSize;
        cache = new String[cacheSize];
        int q = Job.getJobMemoryAddress();
        for (int i = 0; i < cacheSize; i++)
        {
            cache[i] = Driver.ram.readRam(q);
            q++;
        }
        pc = 0;
        int instructionCount = 1;
        // algorithm used to call the fetch and decode cycle
        while (pc < jobSize)
        {
            System.out.println("***** Job Number " + Job.getJobNumber() + " *****");
            System.out.println("Instruction Count " + instructionCount);
            String instr = fetch(pc, cache);
            execute(decode(instr, cache), cache);
            if (!jumped)
            {
                pc++;
            } else
            {
                jumped = false;
            }
            instructionCount++;
        }
        return times;
    }

    public int effectiveAddress(int i, long a)
    {
        return regArray[i] + (int) a;
    }

    public String fetch(int p, String[] c)
    {
        pc = p;

        cache = c;
        // long used for converting to binary
        long tempLong;

        // temporary string used for storing the binary value of the instruction
        String tempInstr2;

        // temporary string used for manipulating the instruction
        String tempInstr;
        //tempInstr = memory.readRam(pc);
        tempInstr = cache[pc];
        tempInstr = tempInstr.substring(2);
        tempLong = Long.parseLong(tempInstr, 16);
        tempInstr2 = Long.toBinaryString(tempLong);
        while (tempInstr2.length() != 32)
        {
            tempInstr2 = "0" + tempInstr2.substring(0);
        }

        // string used as output of function
        String fetchedInstr = tempInstr2;

        // return fetched instruction
        return fetchedInstr;
    }

    public int decode(String fetchedInstr, String[] c)
    {
        cache = c;
        // temporary stings used for decoding the instruction set
        String tempInstr;
        String tempOppCode;
        String tempInstructionType;
        String tempReg1;
        String tempReg2;
        String tempAddress;
        String tempSReg1;
        String tempSReg2;
        String tempDReg;
        String tempBReg;

        tempInstr = fetchedInstr;

        // Use first 2 bits to determine instruction type
        tempInstructionType = tempInstr.substring(0, 2);
        instructionType = Integer.parseInt(tempInstructionType);
        System.out.println("Instruction type: " + instructionType);

        // Use second 6 bits as the oppcode
        tempOppCode = tempInstr.substring(2, 8);
        opCode = Integer.parseInt(tempOppCode, 2);
        System.out.println("OpCode: " + opCode);

        // determine what instruction should be executed
        switch (instructionType)
        {
            // Arithmetic instruction format
            case 00:
            {
                // set reg-1
                System.out.println("Arithmetic case");
                tempSReg1 = tempInstr.substring(8, 12);
                sReg1 = Integer.parseInt(tempSReg1, 2);
                System.out.println("S-Reg1: " + sReg1);

                // set reg-2
                tempSReg2 = tempInstr.substring(12, 16);
                sReg2 = Integer.parseInt(tempSReg2, 2);
                System.out.println("S-Reg2: " + sReg2);

                // set d-reg
                tempDReg = tempInstr.substring(16, 20);
                dReg = Integer.parseInt(tempDReg, 2);
                System.out.println("D-Reg: " + dReg);
                break;
            }

            // Conditional Branch and Immediate format
            case 01:
            {
                // set b-reg
                System.out.println("Conditional format");
                tempBReg = tempInstr.substring(8, 12);
                bReg = Integer.parseInt(tempBReg, 2);
                System.out.println("B-reg: " + bReg);

                // set d-reg
                tempDReg = tempInstr.substring(12, 16);
                dReg = Integer.parseInt(tempDReg, 2);
                System.out.println("D-reg: " + dReg);

                // set address
                tempAddress = tempInstr.substring(16);
                address = Long.parseLong(tempAddress, 2);
                System.out.println("Address: " + address);
                break;
            }

            // Unconditional Jump format
            case 10:
            {
                // set address
                System.out.println("Unconditional jump");
                tempAddress = tempInstr.substring(8);
                address = Long.parseLong(tempAddress, 2);
                System.out.println("Address: " + address);
                break;
            }

            // Input and Output instruction format
            case 11:
            {
                // set reg-1
                System.out.println("Input and Output format");
                tempReg1 = tempInstr.substring(8, 12);
                reg1 = Integer.parseInt(tempReg1, 2);
                System.out.println("Reg1: " + reg1);

                // set reg-2
                tempReg2 = tempInstr.substring(12, 16);
                reg2 = Integer.parseInt(tempReg2, 2);
                System.out.println("Reg2: " + reg2);

                // set address
                tempAddress = tempInstr.substring(16);
                address = Long.parseLong(tempAddress, 2);
                System.out.println("Address: " + address);
                ioCount++;
                break;
            }

            // Error case
            default:
            {
                System.out.println("Error: instruction type not supported");
                break;
            }
        }
        return opCode;
    }

    public void execute(int o, String[] c)
    {
        cache = c;
        opCode = o;
        switch (opCode)
        {
            // Reads the content of I/P buffer into accumulator
            case 0:
            {
                if (address > 0)
                {
                    cpuBuffer = bufferAddress((int) address);
                    regArray[reg1] = (int) dma.readNCpu(cpuBuffer, cache);
                } else
                {
                    regArray[reg1] = regArray[reg2];
                }
                System.out.println("RD Instruction");
                System.out.println("Register " + reg1 + " now contains " + regArray[reg1]);
                break;
            }

            // Writes the content of the accumulator into O/P buffer
            case 1:
            {
                cpuBuffer = bufferAddress((int) address);
                dma.write(Job, cpuBuffer, regArray[reg2]);
                System.out.println("WR Instruction");
                break;
            }

            // Stores content of a register into an address
            case 2:
            {
                regArray[bReg] += regArray[dReg];
                System.out.println("ST Instruction");
                System.out.println("Stored " + regArray[dReg] + " into register: " + bReg + " contents now: " + regArray[bReg]);
                break;
            }

            // Loads the content of an address into a register
            case 3:
            {
                System.out.println("LW Instruction");
                regArray[dReg] = regArray[effectiveAddress(bReg, address) % 16];
                System.out.println("Loaded: " + regArray[effectiveAddress(bReg, address) % 16] + " into register: " + dReg + " contents now: " + regArray[dReg]);
                break;
            }

            // Transfers the content of one register into another register
            case 4:
            {
                // Swap contents of reg1 and reg 2
                regArray[tempReg] = regArray[sReg1];
                regArray[sReg1] = regArray[sReg2];
                regArray[sReg2] = regArray[tempReg];
                System.out.println("MOV Instruction");
                System.out.println("Swapped register: " + sReg1 + " and register: " + sReg2);
                System.out.println("Contents of register " + sReg1 + " now: " + regArray[sReg1] + " contents of register " + sReg2 + " now: " + regArray[sReg2]);
                break;
            }

            // Adds content of two S-regs into D-reg
            case 5:
            {
                regArray[dReg] = regArray[sReg1] + regArray[sReg2];
                System.out.println("ADD Instruction");
                System.out.println("added register: " + sReg1 + " and register: " + sReg2);
                System.out.println("Contents of register " + sReg1 + ": " + regArray[sReg1] + " + " + " contents of register " + sReg2 + " " + regArray[sReg2] + " Contents of register " + dReg + " now " + regArray[dReg]);
                break;
            }

            // Subtracts content of two S-regs into D-reg
            case 6:
            {
                regArray[dReg] = regArray[sReg1] - regArray[sReg2];
                System.out.println("SUB Instruction");
                System.out.println("subtracted register: " + sReg1 + " and register: " + sReg2);
                System.out.println("Contents of register " + sReg1 + ": " + regArray[sReg1] + " - " + " contents of register " + sReg2 + " " + regArray[sReg2] + " Contents of register " + dReg + " now " + regArray[dReg]);
                break;
            }

            // Multiplies content of two S-regs into D-reg
            case 7:
            {
                dReg = sReg1 * sReg2;
                System.out.println("MUL Instruction");
                System.out.println("multiplied register: " + sReg1 + " and register: " + sReg2);
                System.out.println("Contents of register " + sReg1 + ": " + regArray[sReg1] + " * " + " contents of register " + sReg2 + " " + regArray[sReg2] + " Contents of register " + dReg + " now " + regArray[dReg]);
                break;
            }

            // Divides content of two S-regs into D-reg
            case 8:
            {
                System.out.println("DIV Instruction");
                if (regArray[sReg2] == 0)
                {
                    System.out.println("Couldn't divide registers");
                    return;
                } else
                {
                    regArray[dReg] = regArray[sReg1] / regArray[sReg2];
                    System.out.println("divided register: " + sReg1 + " and register: " + sReg2);
                    System.out.println("Contents of register " + sReg1 + ": " + regArray[sReg1] + " + " + " contents of register " + sReg2 + " " + regArray[sReg2] + " Contents of register " + dReg + " now " + regArray[dReg]);
                }
                break;
            }

            // Logical AND of two S-regs into D-reg
            case 9:
            {
                regArray[dReg] = regArray[sReg1] & regArray[sReg2];
                System.out.println("AND Instruction");
                System.out.println("logical and of register: " + sReg1 + " and register: " + sReg2);
                System.out.println("Contents of register " + sReg1 + ": " + regArray[sReg1] + " and " + " contents of register " + sReg2 + " " + regArray[sReg2] + " Contents of register " + dReg + " now " + regArray[dReg]);
                break;
            }

            // Logical OR of two S-regs into D-reg
            case 10:
            {
                regArray[dReg] = regArray[sReg1] | regArray[sReg2];
                System.out.println("OR Instruction");
                System.out.println("logical or of register: " + sReg1 + " and register: " + sReg2);
                System.out.println("Contents of register " + sReg1 + ": " + regArray[sReg1] + " or " + " contents of register " + sReg2 + " " + regArray[sReg2] + " Contents of register " + dReg + " now " + regArray[dReg]);
                break;
            }

            // Transfers address/data directly into a register
            case 11:
            {
                if (address > 0)
                {
                    cpuBuffer = bufferAddress((int) address);
                    regArray[dReg] = (int) dma.readNCpu(cpuBuffer, cache);
                } else
                {
                    regArray[dReg] = 0;
                }

                System.out.println("MOVI Instruction");
                System.out.println("Register" + dReg + " now contains " + regArray[dReg]);
                break;
            }
            // Adds a data directly to the content of a register
            case 12:
            {
                if (address > 1)
                {
                    address /= 4;
                }
                regArray[dReg] += address;

                System.out.println("ADDI Instruction");
                System.out.println("Register " + dReg + " now contains " + regArray[dReg]);
                break;
            }

            // Multiplies a data directly to the content of a register
            case 13:
            {
                regArray[dReg] *= (int) address;
                System.out.println("MULI Instruction");
                System.out.println("Register " + dReg + " now contains " + regArray[dReg]);
                break;
            }

            // Divides a data directly to the content of a register
            case 14:
            {
                regArray[dReg] /= (int) address;
                System.out.println("DIVI Instruction");
                System.out.println("Register " + dReg + " now contains " + regArray[dReg]);
                break;
            }

            // Loads a data/address directly to the content of a register
            case 15:
            {
                regArray[dReg] = (int) address / 4;
                System.out.println("LDI Instruction");
                System.out.println("Register " + dReg + " now contains " + regArray[dReg]);
                break;
            }

            // Sets the D-reg to 1 if  first S-reg is less than second B-reg, and 0 otherwise
            case 16:
            {
                System.out.println("SLT Instruction");
                if (regArray[sReg1] < regArray[sReg2])
                    regArray[dReg] = 1;
                else
                    regArray[dReg] = 0;
                System.out.println("Register " + dReg + " now contains " + regArray[dReg]);
                break;
            }

            // Sets the D-reg to 1 if first S-reg is less than a data, and 0 otherwise
            case 17:
            {
                if (regArray[sReg1] < (int) address)
                    regArray[dReg] = 1;
                else
                    regArray[dReg] = 0;

                System.out.println("SLTI Instruction");
                System.out.println("Register: " + dReg + " now contains " + regArray[dReg]);
                break;
            }

            // Logical end of program
            case 18:
            {
                System.out.println("HLT Instruction");
                long elapsedTimeMillis = System.currentTimeMillis() - start;
                System.out.println("Amount of time Job was running " + elapsedTimeMillis + " milliseconds");
                System.out.println("Io count for Job " + ioCount);
                System.out.println("End Program");
                times = new executeTimes(jobNumber, elapsedTimeMillis);
                break;
            }

            // Does nothing and moves to next instruction
            case 19:
            {
                System.out.println("NOP Instruction");
                System.out.println("Moving to next instruction");
                break;
            }

            // Jumps to a specified location
            case 20:
            {
                pc = (int) address / 4;
                jumped = true;

                System.out.println("JMP Instruction");
                System.out.println("PC set to " + pc);
                break;
            }

            // Branches to an address when content of B-reg = D-reg
            case 21:
            {
                System.out.println("BEQ Instruction");
                if (regArray[bReg] == regArray[dReg])
                {
                    pc = (int) address / 4;
                    System.out.println("branch to PC " + pc);
                }
                break;
            }

            // Branches to an address when content of B-reg <> D-reg
            case 22:
            {
                System.out.println("BNE Instruction");
                if (regArray[bReg] != regArray[dReg])
                {
                    ;
                    pc = (int) address / 4;
                    System.out.println("branch to PC " + pc);
                }
                break;
            }

            // Branches to an address when content of B-reg = 0
            case 23:
            {
                System.out.println("After BEZ Instruction");
                if (regArray[bReg] == 0)
                {
                    pc = (int) address / 4;
                    //pc += Job.getJobMemoryAddress();
                    System.out.println("branch to PC " + pc);
                }
                break;
            }

            // Branches to an address when content of B-reg <> 0
            case 24:
            {
                System.out.println("BNZ Instructions");
                System.out.println("contents of b-reg " + regArray[bReg] + " contents of pc: " + pc + "contents of address: " + address);
                if (regArray[bReg] != 0)
                {
                    pc = (int) address / 4;
                    //pc += Job.getJobMemoryAddress();
                    System.out.println("branch to PC " + pc);
                }
                break;
            }

            // Branches to an address when content of B-reg > 0
            case 25:
            {
                System.out.println("BGZ Instruction");
                if (regArray[bReg] > 0)
                {
                    pc = (int) address / 4;
                    System.out.println("branch to PC " + pc);
                }
                break;
            }

            // Branches to an address when content of B-reg < 0
            case 26:
            {
                System.out.println("After BLZ Instruction");
                if (regArray[bReg] < 0)
                {
                    pc = (int) address / 4;
                    System.out.println("branch to PC " + pc);
                }
                break;
            }

            // Error case
            default:
            {
                System.out.println("Error, Op code not supported");
                break;
            }
        }
    }

    // method used to determine physical address to store or read data from Ram
    public long bufferAddress(int i)
    {
        return i / 4;
    }
}

