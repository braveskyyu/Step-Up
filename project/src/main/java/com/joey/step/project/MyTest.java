package com.joey.step.project;

public class MyTest {
    public static void main(String[] args) {
        MyTest.getTest();
    }

    public static void getTest(){
        System.out.println(num);
    }

    static {
        num=1;
    }

    public static int num = 0;



    public MyTest(){
        System.out.println("MyTest is loading!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}