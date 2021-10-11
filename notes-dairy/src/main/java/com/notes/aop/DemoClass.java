package com.notes.aop;

import com.notes.aop.pojo.Car;
import com.notes.aop.pojo.Student;
import org.springframework.stereotype.Component;

/**
 * @author tiny
 * @date 2021/10/11 17:15
 * @Description:
 */
@Component
public class DemoClass {

    public void demo(Student student, Car car){
        System.out.println("=============================================");
        System.out.println("方法执行...");
        System.out.println("=============================================");
    }

}
