package com.company;

public class Main {
    public static Disk disk;

    public static void main(String[] args) {
        String file = "program.txt";
        disk = new Disk();
        Loader loader = new Loader(file);
    }
}