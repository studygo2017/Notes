package com.notes.demo;

import com.notes.NotesApplication;
import com.notes.aop.DemoClass;
import com.notes.aop.pojo.Car;
import com.notes.aop.pojo.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @author tiny
 * @date 2021/10/11 17:58
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NotesApplication.class)
public class ControllerDemo {

    @Autowired
    private DemoClass demoClass;

    @Test
    public void testDemo(){
        demoClass.demo(new Student("小明",12),new Car("BMW",new BigDecimal(50.5)) );

    }
}
