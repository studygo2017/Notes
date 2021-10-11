package com.notes.aop.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author tiny
 * @date 2021/10/11 18:04
 * @Description:
 */
@Data
@AllArgsConstructor
public class Car {
    private String brand;

    private BigDecimal price;

    @Override
    public String toString(){
        return brand+"牌轿车花了"+price+"万元...";
    }
}
