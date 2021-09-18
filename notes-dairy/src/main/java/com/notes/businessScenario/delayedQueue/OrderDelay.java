package com.notes.businessScenario.delayedQueue;

import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author tiny
 * @date 2021/9/17 18:03
 * @Description:
 */
@Data
public class OrderDelay implements Delayed {

    //订单id
    private String orderId;

    //超时时刻,单位纳秒
    private long timeout;

    public OrderDelay(String orderId, long timeout) {
        this.orderId = orderId;
        this.timeout = TimeUnit.NANOSECONDS.convert(timeout,TimeUnit.SECONDS) + System.nanoTime();
    }

    //返回值小于等于0时即延时时间到
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(timeout- System.nanoTime(),TimeUnit.NANOSECONDS);
    }

    //定义延时队列的元素排序规则
    @Override
    public int compareTo(Delayed o) {
        if(o==this) return 0;
        OrderDelay orderDelay = (OrderDelay) o;
        long d = this.getDelay(TimeUnit.NANOSECONDS) - orderDelay.getDelay(TimeUnit.NANOSECONDS);
        return d==0 ? 0 : (d > 0 ? 1 : -1) ;
    }

    public void print(){
        System.out.println("删除订单............................"+this.orderId);
    }

}
