package com.company;

/**
 * Created by Michael on 2/3/2015.
 */
public class Cpu
{
    public String memory[] = {"0xC050005C", "0x4B060000"};
    public int sReg;
    public int dReg;
    public int reg1;
    public int reg2;
    public int oppCode;
    public int instructionType;
    public int pc;

    public Cpu ()
    {
        pc = 0;
    }

    public String fetch ()
    {
        // long used for converting to binary
        long tempLong;

        // temporary string used for storing the binary value of the instruction
        String tempInstr2;

        // temporary string used for manipulating the instruction
        String tempInstr = "";
        tempInstr = memory[pc];
        tempInstr = tempInstr.substring(2);
        tempLong = Long.parseLong(tempInstr,16);
        tempInstr2 = Long.toBinaryString(tempLong);

        // string used as output of function
        String fetchedInstr = tempInstr2;

        // point program counter to next instruction
        pc++;

        // return fetched instruction
        return fetchedInstr;
    }

    public String decode (String fetchedInstr)
    {
        // temporary stings used for manipulating the instruction set
        String tempInstr;
        String tempOppCode;
        String tempInstructionType;
        String tempReg1;
        String tempReg2;

        tempInstr = fetchedInstr;

        // Use first 2 bits to determine instruction type
        tempInstructionType = tempInstr.substring(0,2);
        instructionType = Integer.parseInt(tempInstructionType);
        System.out.println(instructionType);


        // Use second 6 bits as the oppcode
        tempOppCode = tempInstr.substring(3,8);
        oppCode = Integer.parseInt(tempOppCode);
        System.out.println(oppCode);

        // determine what instruction should be executed
        switch(instructionType)
        {
            // Arithmetic instruction format
            case 00:
            {
                System.out.println("Arithmetic case");
                break;
            }

            // Conditional Branch and Immediate format
            case 01:
            {
                System.out.println("Conditional format");
                break;
            }

            // Unconditional Jump
            case 10:
            {
                System.out.println("Unconditional jump");
                break;
            }

            // Input and Output instruction format
            case 11:
            {
                System.out.println("Input and Output format");
                tempReg1 = tempInstr.substring(9,12);
                reg1 = Integer.parseInt(tempReg1);
                System.out.println(reg1);

                tempReg2 = tempInstr.substring(13,16);
                reg2 = Integer.parseInt(tempReg2);
                System.out.println(reg2);

                break;
            }

            // Error case
            default:
            {
                System.out.println("Error not supported instruction type");
                break;
            }
        }

        // string used for output of function
        String decodedInstr = "";

        fetchedInstr = decodedInstr;

        // return decoded instruction
        return decodedInstr;
    }

    public void execute ()
    {

    }

}


