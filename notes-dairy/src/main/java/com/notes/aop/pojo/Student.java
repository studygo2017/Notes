package com.notes.aop.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tiny
 * @date 2021/10/11 18:02
 * @Description:
 */
@Data
@AllArgsConstructor
public class Student {

    private String name;

    private Integer age;

    @Override
    public String toString(){
        return "学生"+name+" , 今年"+age+"岁了...";
    }
}
