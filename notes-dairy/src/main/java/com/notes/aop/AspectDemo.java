package com.notes.aop;

import com.notes.aop.pojo.Car;
import com.notes.aop.pojo.Student;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author tiny
 * @date 2021/10/11 17:53
 * @Description:
 */
@Aspect
@Component
public class AspectDemo {

    /**
     * 定义切入点
     */
    @Pointcut("execution(public * com.notes.aop.DemoClass.*(..))")
    public void demoMethod(){}

    @After("demoMethod()")
    public void doAfter(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Student student = null;
        Car car = null;
        if(args[0] instanceof Student)
            student = (Student) args[0];
        if(args[1] instanceof Car)
            car = (Car) args[1];
        if(student!=null)
            System.out.println(student);
        if(car!=null)
            System.out.println(car);
        System.out.println("do.................after..................");
    }
}
