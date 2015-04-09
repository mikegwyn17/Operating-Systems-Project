package com.company;

import java.util.ArrayList;

/**
 * Created by Michael on 4/8/2015.
 */
public class PageTable
{
    private ArrayList<Page> pt;

    public PageTable ()
    {
        pt = new ArrayList<Page>();
    }

    public void insert (Page p)
    {
        pt.add(p);
    }
}
