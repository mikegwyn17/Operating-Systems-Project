package com.company;

/**
 * Created by Michael on 4/8/2015.
 */
public class Page
{
    public String word1;
    public String word2;
    public String word3;
    public String word4;

    public int pageOffset;
    public int pageNumber;
    public boolean inRam;

    public Page (String w1, String w2, String w3, String w4)
    {
        word1 = w1;
        word2 = w2;
        word3 = w3;
        word4 = w4;
    }
}
