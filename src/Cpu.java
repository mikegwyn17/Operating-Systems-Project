/**
 * Created by Michael on 2/3/2015.
 */
public class Cpu
{
    public String memory[] = {"0xC050005C", "0x4B060000"};
    public int sReg;
    public int dReg;
    public int oppCode;
    public String instructionType;

    public String fetch (int pc)
    {
        // temporary string used for manipulating the instruction
        String tempInstr = "";
        tempInstr = memory[pc];

        // string used as output of function
        String fetchedInstr = tempInstr;

        // point program counter to next instruction
        pc++;

        // return fetched instruction
        return fetchedInstr;
    }

    public String decode (String fetchedInstr)
    {
        // temporary sting used for manipulating the instruction
        String tempInstr;

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


