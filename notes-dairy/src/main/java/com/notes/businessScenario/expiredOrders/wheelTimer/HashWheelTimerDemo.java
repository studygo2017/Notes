package com.notes.businessScenario.expiredOrders.wheelTimer;

import com.notes.utils.TimeUtil;
import com.sun.org.apache.xpath.internal.operations.Or;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;

import java.util.concurrent.TimeUnit;

/**
 * netty框架提供的时间轮工具
 * io.netty.util.HashedWheelTimer 来实现达到指定延时时间时开异步线程来删除未支付的订单
 */
public class HashWheelTimerDemo {

    private static Timer timer = new HashedWheelTimer();

    public static void main(String[] argv) throws InterruptedException {
        OrderTimerTask orderTimerTask = new OrderTimerTask(100000L, 8,TimeUnit.SECONDS);
        //异步线程,指定时间后执行其中run方法
        timer.newTimeout(orderTimerTask, orderTimerTask.getTimeout(), orderTimerTask.getUnit());
        TimeUtil.stopwatch(orderTimerTask.getTimeout()+1);
    }

}
