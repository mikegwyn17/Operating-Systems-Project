package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by evanross on 2/4/15.
 */
public class Loader {

    FileReader fr;
    BufferedReader br;
    PCBObject pcbObject;

    int lastJobNo;

    boolean executed;

    final int JOB_NUMBER = 2, INPUT_BUFFER = 2;
    final int JOB_INSTRUCTIONS = 3, OUTPUT_BUFFER = 3;
    final int JOB_PRIORITY = 4, TEMPORARY_BUFFER = 3;

    public Loader(String file) {

        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
        } catch (IOException e) {
            executed = false;
        }
    }

    public void Start() throws IOException {
        String line;
        int index = 0;
        while(true) {

            line = br.readLine();
            if(line == null) break;

            if(line.contains("JOB")) {
                String[] control = line.split("\\s+");

                lastJobNo = Integer.parseInt(control[JOB_NUMBER], 16);

                pcbObject = new PCBObject(lastJobNo, Integer.parseInt(control[JOB_PRIORITY], 16), index, Integer.parseInt(control[JOB_INSTRUCTIONS], 16));
                Driver.pcb.insert(pcbObject);
            }
            else if(line.contains("Data")) {

                String[] control = line.split("\\s+");

                if(Driver.pcb.getPCBSortStatus() == PCB.sorttype.JOB_NUMBER) {
                    PCBObject thisJob = Driver.pcb.getPCB(lastJobNo);
                    thisJob.setDataDiskAddress(index);
                    thisJob.setInputBuffer(Integer.parseInt(control[INPUT_BUFFER], 16));
                    thisJob.setOutputBuffer(Integer.parseInt(control[OUTPUT_BUFFER], 16));
                    thisJob.setTemporaryBuffer(Integer.parseInt(control[TEMPORARY_BUFFER], 16));
                } else {
                    Driver.pcb.sortPCB(PCB.sorttype.JOB_NUMBER);
                }
            }
            else if(line.contains("END")) { }
            else {
                Driver.disk.writeDisk(line, index);
                index++;
            }
        }
        executed = true;
    }

}
