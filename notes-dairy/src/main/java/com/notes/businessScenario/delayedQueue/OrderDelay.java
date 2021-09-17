package com.notes.businessScenario.delayedQueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author tiny
 * @date 2021/9/17 18:03
 * @Description:
 */
public class OrderDelay implements Delayed {

    //订单id
    private String orderId;

    //超时时间
    private long timeout;

    public OrderDelay(String orderId,long timeout){
        this.orderId = orderId;
        this.timeout = timeout;
    }

    //定义指定单位的延时时长
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(timeout - System.nanoTime(),TimeUnit.NANOSECONDS);
    }

    //定义延时队列的元素排序规则
    @Override
    public int compareTo(Delayed o) {
        if(o==this) return 0;
        OrderDelay t = (OrderDelay) o;
        long d = (getDelay(TimeUnit.NANOSECONDS) - t
                .getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }
}
