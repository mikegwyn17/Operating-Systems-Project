package com.company;
/**
 * Created by Michael on 2/3/2015.
 */
public class Cpu
{
    // Array of strings used to simulate memory, will be replaced.
    public String memory[] = {"0x56810018"};

    // Registers for the cpu
    public int sReg1;
    public int sReg2;
    public int dReg;
    public int bReg;
    public int reg1;
    public int reg2;
    public int tempReg;
    public int accumulator;

    public int opCode;
    public int instructionType;
    public long address;
    public int inputBuffer;
    public int outputBuffer;
    public int tempBuffer;
    public int pc;

    public Cpu ()
    {
        pc = 0;
    }

    public String fetch ()
    {
        // long used for converting to binary
        long tempLong;

        int numLeadingZeroes = 0;
        // temporary string used for storing the binary value of the instruction
        String tempInstr2;

        // temporary string used for manipulating the instruction
        String tempInstr = "";
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
                reg1 = Integer.parseInt(tempSReg1);
                System.out.println("S-Reg1: " + sReg1);

                // set reg-2
                tempSReg2 = tempInstr.substring(13,16);
                reg1 = Integer.parseInt(tempSReg2);
                System.out.println("S-Reg2: " + sReg2);

                // set d-reg
                tempDReg = tempInstr.substring(17,20);
                dReg = Integer.parseInt(tempDReg);
                System.out.println("D-Reg: " + dReg);
                break;
            }

            // Conditional Branch and Immediate format
            case 01:
            {
                // set b-reg
                System.out.println("Conditional format");
                tempBReg = tempInstr.substring(9,12);
                bReg = Integer.parseInt(tempBReg,2);
                System.out.println("B-reg: " + bReg);

                // set d-reg
                tempDReg = tempInstr.substring(13,16);
                dReg = Integer.parseInt(tempDReg,2);
                System.out.println("D-reg: " + dReg);

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
                reg1 = Integer.parseInt(tempReg1,2);
                System.out.println("Reg1: " + reg1);

                // set reg-2
                tempReg2 = tempInstr.substring(13,16);
                reg2 = Integer.parseInt(tempReg2,2);
                System.out.println("Reg2: " + reg2);

                // set address
                tempAddress = tempInstr.substring(17);
                address = Long.parseLong(tempAddress,2);
                System.out.println("Address: " + address);

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
                System.out.println("contents of accumulator: " + accumulator + " contents of input buffer: " + inputBuffer);

                System.out.println("After RD Instruction");
                accumulator = inputBuffer;
                System.out.println("contents of accumulator: " + accumulator + " contents of input buffer: " + inputBuffer);
                break;
            }

            // Writes the content of the accumulator into O/P buffer
            case 1:
            {
                System.out.println("Before WR Instruction");
                System.out.println("contents of output buffer: " + outputBuffer + " contents of accumulator: " + accumulator);

                System.out.println("After WR Instruction");
                outputBuffer = accumulator;
                System.out.println("contents of output buffer: " + outputBuffer + " contents of accumulator: " + accumulator);
                break;
            }

            // Stores content of a register into an address
            case 2:
            {
                System.out.println("Before ST Instruction");
                System.out.println("contents of address: " + address + " contents of d-reg: " + dReg);

                System.out.println("After ST Instruction");
                address += dReg;
                System.out.println("contents of address: " + address + " contents of d-reg: " + dReg);
                break;
            }

            // Loads the content of an address into a register
            case 3:
            {
                System.out.println("Before LW Instruction");
                System.out.println("contents of d-reg: " + dReg + " contents of address: " + address);

                System.out.println("After LW Instruction");
                dReg = (int)address;
                System.out.println("contents of d-reg: " + dReg + " contents of address: " + address);
                break;
            }

            // Transfers the content of an address into register
            case 4:
            {
                System.out.println("Before MOV instruction");
                System.out.println("contents of register 1: " + reg1 + " contents of register 2: " + reg2);

                // Swap contents of reg1 and reg 2
                tempReg = reg1;
                reg1 = reg2;
                reg2 = tempReg;
                System.out.println("After MOV Instruction");
                System.out.println("contents of register 1: " + reg1 + " contents of register 2: " + reg2);
                break;
            }

            // Adds content of two S-regs into D-reg
            case 5:
            {
                System.out.println("Before ADD Instruction");
                System.out.println("contents of s-reg1: " + sReg1 + " contents of s-reg2: " + sReg2 + " contents of d-reg: " + dReg);

                tempReg = reg1 + reg2;
                dReg = tempReg;
                System.out.println("After ADD Instruction");
                System.out.println("contents of s-reg1: " + sReg1 + " contents of s-reg2: " + sReg2 + " contents of d-reg: " + dReg);
                break;
            }

            // Subtracts content of two S-regs into D-reg
            case 6:
            {
                System.out.println("Before SUB Instruction");
                System.out.println("contents of s-reg1: " + sReg1 + " contents of s-reg2: " + sReg2 + " contents of d-reg: " + dReg);

                if (sReg1 > sReg2)
                    tempReg = sReg1-sReg2;

                else
                    tempReg = sReg2-sReg1;

                System.out.println("After SUB Instruction");
                System.out.println("contents of s-reg1: " + sReg1 + " contents of s-reg2: " + sReg2 + " contents of d-reg: " + dReg);
                break;
            }

            // Multiplies content of two S-regs into D-reg
            case 7:
            {
                System.out.println("Before MUL Instruction");
                System.out.println("contents of s-reg1: " + sReg1 + " contents of s-reg2: " + sReg2 + " contents of d-reg: " + dReg);

                int tempReg = sReg1 * sReg2;
                dReg = tempReg;
                System.out.println("After MUL Instruction");
                System.out.println("contents of s-reg1: " + sReg1 + " contents of s-reg2: " + sReg2 + " contents of d-reg: " + dReg);
                break;
            }

            // Divides content of two S-regs into D-reg
            case 8:
            {
                System.out.println("Before DIV Instruction");
                System.out.println("contents of s-reg1: " + sReg1 + " contents of s-reg2: " + sReg2 + " contents of d-reg: " + dReg);
                int tempReg;
                if (sReg1 > sReg2)
                    tempReg = sReg2/sReg1;

                else
                    tempReg = sReg1/sReg2;

                dReg = tempReg;
                System.out.println("After DIV Instruction");
                System.out.println("contents of s-reg1: " + sReg1 + " contents of s-reg2: " + sReg2 + " contents of d-reg: " + dReg);
                break;
            }

            // Logical AND of two S-regs into D-reg
            case 9:
            {
                System.out.println("Before AND Instruction");
                System.out.println("contents of s-reg1: " + sReg1 + " contents of s-reg2: " + sReg2 + " contents of d-reg: " + dReg);

                dReg = (int)sReg1&sReg2;
                System.out.println("After AND Instruction");
                System.out.println("contents of s-reg1: " + sReg1 + " contents of s-reg2: " + sReg2 + " contents of d-reg: " + dReg);
                break;
            }

            // Logical OR of two S-regs into D-reg
            case 10:
            {
                System.out.println("Before OR Instruction");
                System.out.println("contents of s-reg1: " + sReg1 + " contents of s-reg2: " + sReg2 + " contents of d-reg: " + dReg);

                dReg = (int)sReg1|sReg2;
                System.out.println("After OR Instruction");
                System.out.println("contents of s-reg1: " + sReg1 + " contents of s-reg2: " + sReg2 + " contents of d-reg: " + dReg);
                break;
            }

            // Transfers address/data directly into a register
            case 11:
            {
                System.out.println("Before MOVI Instruction");
                break;
            }

            // Adds a data directly to the content of a register
            case 12:
            {
                System.out.println("ADDI Instruction");
                break;
            }

            // Multiplies a data directly to the content of a register
            case 13:
            {
                System.out.println("MULI Instruction");
                break;
            }

            // Divides a data directly to the content of a register
            case 14:
            {
                System.out.println("DIVI Instruction");
                break;
            }

            // Loads a data/address directly to the content of a register
            case 15:
            {
                System.out.println("LDI Instruction");
                break;
            }

            // Sets the D-reg to 1 if  first S-reg is less than second B-reg, and 0 otherwise
            case 16:
            {
                System.out.println("SLT Instruction");
                break;
            }

            // Sets the D-reg to 1 if  first S-reg is less than a data, and 0 otherwise
            case 17:
            {
                System.out.println("SLTI Instruction");
                break;
            }

            // Logical end of program
            case 18:
            {
                System.out.println("HLT Instruction");
                break;
            }

            // Does nothing and moves to next instruction
            case 19:
            {
                System.out.println("NOP Instruction");
                break;
            }

            // Jumps to a specified location
            case 20:
            {
                System.out.println("JMP Instruction");
                break;
            }

            // Branches to an address when content of B-reg = D-reg
            case 21:
            {
                System.out.println("BEQ Instruction");
                break;
            }

            // Branches to an address when content of B-reg <> D-reg
            case 22:
            {
                System.out.println("BNE Instruction");
                break;
            }

            // Branches to an address when content of B-reg = 0
            case 23:
            {
                System.out.println("BEZ Instruction");
                break;
            }

            // Branches to an address when content of B-reg <> 0
            case 24:
            {
                System.out.println("BNZ Instructions");
                break;
            }

            // Branches to an address when content of B-reg <> 0
            case 25:
            {
                System.out.println("BGZ Instruction");
                break;
            }

            // Branches to an address when content of B-reg < 0
            case 26:
            {
                System.out.println("BLZ Instruction");
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


