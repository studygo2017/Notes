package com.notes.dairy.demo;

import com.notes.dairy.demo.A;
import com.notes.dairy.demo.B;
import org.junit.Test;

/**
 * 类中元素的静态绑定与动态绑定测试
 */
public class Demo01 {

    @Test
    public void testAB(){
        B b = new B();
        A ab = b;

        System.out.println(b.num); //2
        System.out.println(ab.num); //1

        //实例方法都走的B的实现
        b.m();
        ab.m();

        //静态方法，走的静态绑定，编译时方法与类就绑定上了
        b.m2(); // 走的B的m2()实现
        ab.m2(); // 走的A的m2()实现

        //实例方法被重写，动态绑定，以下两次调用都走的B中的getStr()实现
        System.out.println(b.getStr());
        System.out.println(ab.getStr());
    }
}

