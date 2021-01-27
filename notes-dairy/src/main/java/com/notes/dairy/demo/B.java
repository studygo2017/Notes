package com.notes.dairy.demo;

public class B extends A{

    public int num = 2;

    private String str = "bbb";

    public String getStr(){
        return str;
    }

    void m(){
        System.out.println("B===========method==============");
    }

    static void m2(){
        System.out.println("B=======static====method==============");
    }
}
