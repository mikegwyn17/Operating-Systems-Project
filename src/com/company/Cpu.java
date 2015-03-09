package com.company;
/**
 * Created by Michael on 2/3/2015.
 */
public class Cpu
{
    // Array of strings used to simulate memory, will be replaced.
    public String memory[] = {"0x56810018"};

    // Registers for the cpu
    public int sReg1 = 2;
    public int sReg2 = 3;
    public int dReg = 4;
    public int bReg = 5;
    public int reg1 = 6;
    public int reg2 = 7;
    public int tempReg = 8;
    public int zero = 1;
    public int accumulator = 0;
    public int[] regArray;

    public int opCode;
    public int instructionType;
    public long address;
    public static int ioCount;

    public int inputBuffer;
    public int outputBuffer;
    public int tempBuffer;

    public int pc;
    public boolean jumped;

    public Cpu ()
    {
        pc = 0;
        regArray = new int[16];
        regArray[zero] = 0;
        ioCount = 0;
        jumped = false;
    }

    public int effectiveAddress(int i, long a)
    {
        return regArray[i] + (int)a;
    }

    public String fetch ()
    {
        // long used for converting to binary
        long tempLong;

        // temporary string used for storing the binary value of the instruction
        String tempInstr2;

        // temporary string used for manipulating the instruction
        String tempInstr;
        tempInstr = memory[pc];
        tempInstr = tempInstr.substring(2);
        tempLong = Long.parseLong(tempInstr,16);
        tempInstr2 = Long.toBinaryString(tempLong);
        while (tempInstr2.length() < 32)
        {
            tempInstr2 = "0" + tempInstr2.substring(0);
        }

        // string used as output of function
        String fetchedInstr = tempInstr2;

        // point program counter to next instruction
        pc++;

        // return fetched instruction
        return fetchedInstr;
    }

    public void decode (String fetchedInstr)
    {
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
        tempInstructionType = tempInstr.substring(0,2);
        instructionType = Integer.parseInt(tempInstructionType);
        System.out.println("Instruction type: " + instructionType);

        // Use second 6 bits as the oppcode
        tempOppCode = tempInstr.substring(3,8);
        opCode = Integer.parseInt(tempOppCode,2);
        System.out.println("OpCode: " + opCode);

        // determine what instruction should be executed
        switch(instructionType)
        {
            // Arithmetic instruction format
            case 00:
            {
                // set reg-1
                System.out.println("Arithmetic case");
                tempSReg1 = tempInstr.substring(9,12);
                regArray[reg1] = Integer.parseInt(tempSReg1);
                System.out.println("S-Reg1: " + regArray[sReg1]);

                // set reg-2
                tempSReg2 = tempInstr.substring(13,16);
                regArray[reg1] = Integer.parseInt(tempSReg2);
                System.out.println("S-Reg2: " + regArray[sReg2]);

                // set d-reg
                tempDReg = tempInstr.substring(17,20);
                regArray[dReg] = Integer.parseInt(tempDReg);
                System.out.println("D-Reg: " + regArray[dReg]);
                break;
            }

            // Conditional Branch and Immediate format
            case 01:
            {
                // set b-reg
                System.out.println("Conditional format");
                tempBReg = tempInstr.substring(9,12);
                regArray[bReg] = Integer.parseInt(tempBReg,2);
                System.out.println("B-reg: " + regArray[bReg]);

                // set d-reg
                tempDReg = tempInstr.substring(13,16);
                regArray[dReg] = Integer.parseInt(tempDReg,2);
                System.out.println("D-reg: " + regArray[dReg]);

                // set address
                tempAddress = tempInstr.substring(17);
                address = Long.parseLong(tempAddress, 2);
                System.out.println("Address: " + address);
                break;
            }

            // Unconditional Jump format
            case 10:
            {
                // set address
                System.out.println("Unconditional jump");
                tempAddress = tempInstr.substring(9);
                address = Long.parseLong(tempAddress, 2);
                System.out.println("Address: " + address);
                break;
            }

            // Input and Output instruction format
            case 11:
            {
                // set reg-1
                System.out.println("Input and Output format");
                tempReg1 = tempInstr.substring(9,12);
                regArray[reg1] = Integer.parseInt(tempReg1,2);
                System.out.println("Reg1: " + regArray[reg1]);

                // set reg-2
                tempReg2 = tempInstr.substring(13,16);
                regArray[reg2] = Integer.parseInt(tempReg2,2);
                System.out.println("Reg2: " + regArray[reg2]);

                // set address
                tempAddress = tempInstr.substring(17);
                address = Long.parseLong(tempAddress,2);
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
    }

    public void execute ()
    {
        switch(opCode)
        {
            // Reads the content of I/P buffer into accumulator
            case 0:
            {
                System.out.println("Before RD Instruction");
                System.out.println("contents of accumulator: " + regArray[accumulator] + " contents of input buffer: " + inputBuffer);

                System.out.println("After RD Instruction");
                regArray[accumulator] = inputBuffer;
                System.out.println("contents of accumulator: " + regArray[accumulator] + " contents of input buffer: " + inputBuffer);
                break;
            }

            // Writes the content of the accumulator into O/P buffer
            case 1:
            {
                System.out.println("Before WR Instruction");
                System.out.println("contents of output buffer: " + outputBuffer + " contents of accumulator: " + regArray[accumulator]);

                System.out.println("After WR Instruction");
                outputBuffer = regArray[accumulator];
                System.out.println("contents of output buffer: " + outputBuffer + " contents of accumulator: " + regArray[accumulator]);
                break;
            }

            // Stores content of a register into an address
            case 2:
            {
                System.out.println("Before ST Instruction");
                System.out.println("contents of address: " + address + " contents of d-reg: " + regArray[dReg]);

                address += regArray[dReg];
                System.out.println("After ST Instruction");
                System.out.println("contents of address: " + address + " contents of d-reg: " + regArray[dReg]);
                break;
            }

            // Loads the content of an address into a register
            case 3:
            {
                System.out.println("Before LW Instruction");
                System.out.println("contents of d-reg: " + regArray[dReg] + " contents of address: " + address);

                System.out.println("After LW Instruction");
                regArray[dReg] = regArray[effectiveAddress(bReg, address) % 16];
                System.out.println("contents of d-reg: " + regArray[dReg] + " contents of address: " + address);
                break;
            }

            // Transfers the content of one register into another register
            case 4:
            {
                System.out.println("Before MOV instruction");
                System.out.println("contents of register 1: " + regArray[reg1] + " contents of register 2: " + regArray[reg2]);

                // Swap contents of reg1 and reg 2
                regArray[tempReg] = regArray[reg1];
                regArray[reg1] = regArray[reg2];
                regArray[reg2] = regArray[tempReg];
                System.out.println("After MOV Instruction");
                System.out.println("contents of register 1: " + regArray[reg1] + " contents of register 2: " + regArray[reg2]);
                break;
            }

            // Adds content of two S-regs into D-reg
            case 5:
            {
                System.out.println("Before ADD Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of s-reg2: " + regArray[sReg2] + " contents of d-reg: " + regArray[dReg]);

                regArray[tempReg] = regArray[sReg1] + regArray[sReg2];
                regArray[dReg] = regArray[tempReg];
                System.out.println("After ADD Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of s-reg2: " + regArray[sReg2] + " contents of d-reg: " + regArray[dReg]);
                break;
            }

            // Subtracts content of two S-regs into D-reg
            case 6:
            {
                System.out.println("Before SUB Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of s-reg2: " + regArray[sReg2] + " contents of d-reg: " + regArray[dReg]);

                if (regArray[sReg1] > regArray[sReg2])
                    regArray[tempReg] = regArray[sReg1]-regArray[sReg2];

                else
                    regArray[tempReg] = regArray[sReg2]-regArray[sReg1];

                System.out.println("After SUB Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of s-reg2: " + regArray[sReg2] + " contents of d-reg: " + regArray[dReg]);
                break;
            }

            // Multiplies content of two S-regs into D-reg
            case 7:
            {
                System.out.println("Before MUL Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of s-reg2: " + regArray[sReg2] + " contents of d-reg: " + regArray[dReg]);

                int tempReg = regArray[sReg1] * regArray[sReg2];
                regArray[dReg] = tempReg;
                System.out.println("After MUL Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of s-reg2: " + regArray[sReg2] + " contents of d-reg: " + regArray[dReg]);
                break;
            }

            // Divides content of two S-regs into D-reg
            case 8:
            {
                System.out.println("Before DIV Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of s-reg2: " + regArray[sReg2] + " contents of d-reg: " + regArray[dReg]);
                int tempReg;
                if (regArray[sReg1] > regArray[sReg2])
                {
                    tempReg = regArray[sReg2] / regArray[sReg1];
                }

                else
                    tempReg = regArray[sReg1]/regArray[sReg2];

                regArray[dReg] = tempReg;
                System.out.println("After DIV Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of s-reg2: " + regArray[sReg2] + " contents of d-reg: " + regArray[dReg]);
                break;
            }

            // Logical AND of two S-regs into D-reg
            case 9:
            {
                System.out.println("Before AND Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of s-reg2: " + regArray[sReg2] + " contents of d-reg: " + regArray[dReg]);

                regArray[dReg] = regArray[sReg1]&regArray[sReg2];
                System.out.println("After AND Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of s-reg2: " + regArray[sReg2] + " contents of d-reg: " + regArray[dReg]);
                break;
            }

            // Logical OR of two S-regs into D-reg
            case 10:
            {
                System.out.println("Before OR Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of s-reg2: " + regArray[sReg2] + " contents of d-reg: " + regArray[dReg]);

                regArray[dReg] = regArray[sReg1]|regArray[sReg2];
                System.out.println("After OR Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of s-reg2: " + regArray[sReg2] + " contents of d-reg: " + regArray[dReg]);
                break;
            }

            // Transfers address/data directly into a register
            case 11:
            {
                System.out.println("Before MOVI Instruction");
                System.out.println("contents of d-reg: " + regArray[dReg] + " contents of address: " + address);
                regArray[dReg] = (int)address;

                System.out.println("After MOVI Instruction");
                System.out.println("contents of d-reg: " + regArray[dReg] + " contents of address: " + address);
                break;
            }

            // Adds a data directly to the content of a register
            case 12:
            {
                System.out.println("Before ADDI Instruction");
                System.out.println("contents of d-reg: " + regArray[dReg] + " contents of address: " + address);
                regArray[dReg] += (int)address;

                System.out.println("After ADDI Instruction");
                System.out.println("contents of d-reg: " + regArray[dReg] + " contents of address: " + address);
                break;
            }

            // Multiplies a data directly to the content of a register
            case 13:
            {
                System.out.println("Before MULI Instruction");
                System.out.println("contents of d-reg: " + regArray[dReg] + " contents of address: " + address);
                regArray[dReg] *= (int)address;

                System.out.println("After MULI Instruction");
                System.out.println("contents of d-reg: " + regArray[dReg] + " contents of address: " + address);
                break;
            }

            // Divides a data directly to the content of a register
            case 14:
            {
                System.out.println("Before DIVI Instruction");
                System.out.println("contents of d-reg: " + regArray[dReg] + " contents of address: " + address);
                regArray[dReg] /= (int)address;

                System.out.println("After MULI Instruction");
                System.out.println("contents of d-reg: " + regArray[dReg] + " contents of address: " + address);
                break;
            }

            // Loads a data/address directly to the content of a register
            case 15:
            {
                System.out.println("Before LDI Instruction");
                System.out.println("contents of d-reg: " + regArray[dReg] + " contents of address: " + address);
                regArray[dReg] = (int)address;

                System.out.println("After MULI Instruction");
                System.out.println("contents of d-reg: " + regArray[dReg] + " contents of address: " + address);
                break;
            }

            // Sets the D-reg to 1 if  first S-reg is less than second B-reg, and 0 otherwise
            case 16:
            {
                System.out.println("Before SLT Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of s-reg2: " + regArray[sReg2] + " contents of d-reg: " + regArray[dReg]);

                if (regArray[sReg1] < regArray[sReg2])
                    regArray[dReg] = 1;
                else
                    regArray[dReg] = 0;

                System.out.println("After OR Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of s-reg2: " + regArray[sReg2] + " contents of d-reg: " + regArray[dReg]);

                break;
            }

            // Sets the D-reg to 1 if first S-reg is less than a data, and 0 otherwise
            case 17:
            {
                System.out.println("Before SLTI Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of s-reg2: " + regArray[sReg2] + " contents of d-reg: " + regArray[dReg]);

                if (regArray[sReg1] < (int)address)
                    regArray[dReg] = 1;
                else
                    regArray[dReg] = 0;

                System.out.println("After SLTI Instruction");
                System.out.println("contents of s-reg1: " + regArray[sReg1] + " contents of address: " + address + " contents of d-reg: " + regArray[dReg]);
                break;
            }

            // Logical end of program
            case 18:
            {
                System.out.println("HLT Instruction");
                System.out.println("End Program");
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
                System.out.println("Before JMP Instruction");
                System.out.println("contents of address: " + address + " contents of pc " + pc);
                pc = (int)address;
                jumped = true;

                System.out.println("After JMP Instruction");
                System.out.println("contents of address: " + address + " contents of pc " + pc);
                break;
            }

            // Branches to an address when content of B-reg = D-reg
            case 21:
            {
                System.out.println("Before BEQ Instruction");
                System.out.println("contents of b-reg " + regArray[bReg] + " contents of d-reg: " + regArray[dReg] + " contents of pc: " + pc);
                if (regArray[bReg] == regArray[dReg])
                {
                    pc = (int)address;
                }

                System.out.println("After BEQ Instruction");
                System.out.println("contents of b-reg " + regArray[bReg] + " contents of d-reg: " + regArray[dReg] + " contents of pc: " + pc);
                break;
            }

            // Branches to an address when content of B-reg <> D-reg
            case 22:
            {
                System.out.println("Before BNE Instruction");
                System.out.println("contents of b-reg " + regArray[bReg] + " contents of d-reg: " + regArray[dReg] + " contents of pc: " + pc);
                if (regArray[bReg] != regArray[dReg])
                {
                    pc = (int)address;
                }
                System.out.println("After BNE Instruction");
                System.out.println("contents of b-reg " + regArray[bReg] + " contents of d-reg: " + regArray[dReg] + " contents of pc: " + pc);
                break;
            }

            // Branches to an address when content of B-reg = 0
            case 23:
            {
                System.out.println("Before BEZ Instruction");
                System.out.println("contents of b-reg " + regArray[bReg] + " contents of pc: " + pc + "contents of address: " + address);
                if (regArray[bReg] == 0)
                {
                    pc = (int)address;
                }

                System.out.println("After BEZ Instruction");
                System.out.println("contents of b-reg " + regArray[bReg] + " contents of pc: " + pc + "contents of address: " + address);
                break;
            }

            // Branches to an address when content of B-reg <> 0
            case 24:
            {
                System.out.println("BNZ Instructions");
                System.out.println("contents of b-reg " + regArray[bReg] + " contents of pc: " + pc + "contents of address: " + address);
                if (regArray[bReg] != 0)
                {
                    pc = (int)address;
                }

                System.out.println("After BNZ Instruction");
                System.out.println("contents of b-reg " + regArray[bReg] + " contents of pc: " + pc + "contents of address: " + address);
                break;
            }

            // Branches to an address when content of B-reg > 0
            case 25:
            {
                System.out.println("Before BGZ Instruction");
                System.out.println("contents of b-reg " + regArray[bReg] + " contents of pc: " + pc + "contents of address: " + address);
                if (regArray[bReg] > 0)
                {
                    pc = (int)address;
                }
                System.out.println("After BGZ Instruction");
                System.out.println("contents of b-reg " + regArray[bReg] + " contents of pc: " + pc + "contents of address: " + address);
                break;
            }

            // Branches to an address when content of B-reg < 0
            case 26:
            {
                System.out.println("Before BLZ Instruction");
                System.out.println("contents of b-reg " + regArray[bReg] + " contents of pc: " + pc + "contents of address: " + address);
                if (regArray[bReg] < 0)
                {
                    pc = (int)address;
                }
                System.out.println("After BLZ Instruction");
                System.out.println("contents of b-reg " + regArray[bReg] + " contents of pc: " + pc + "contents of address: " + address);
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
}