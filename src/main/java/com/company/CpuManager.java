package com.company;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * Created by Michael on 4/9/2015.
 */
public class CpuManager implements Executor
{
    public CpuManager () {}

    @Override
    public void execute(Runnable command)
    {
        new Thread(command).start();
    }
}
