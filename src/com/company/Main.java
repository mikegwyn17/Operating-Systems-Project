package com.company;

public class Main {

    public static void main(String[] args)
    {
        Cpu cpu1 = new Cpu();
        String foo = cpu1.fetch();
        System.out.println(foo);
        cpu1.decode(foo);
        cpu1.execute();

//        String main = "0xC050005C";
//        String newMain = main.substring(2);
//        String some = "123";
//        long correct = 0;
//        String foo = "";
//        correct = Long.parseLong(newMain,16);
//        foo = Long.toBinaryString(correct);
//
//        System.out.println(newMain);
//
//        System.out.println(correct);
//        System.out.println(foo);

    }
}
