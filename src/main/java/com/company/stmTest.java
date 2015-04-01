package com.company;

import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnRef;

import java.util.Date;
import java.util.Random;

import static org.multiverse.api.StmUtils.atomic;
import static org.multiverse.api.StmUtils.newTxnInteger;

/**
 * Created by Mike-PC on 4/1/2015.
 */
public class stmTest {

    Random random = new Random();
    public stmTest() {

    }

    public void thread() {
        atomic(new Runnable() {
            public void run() {
                int randomNum = random.nextInt();
            }
        });
    }
}