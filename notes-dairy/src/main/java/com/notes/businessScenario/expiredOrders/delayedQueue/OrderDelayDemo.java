package com.notes.businessScenario.expiredOrders.delayedQueue;

import java.util.*;
import java.util.concurrent.DelayQueue;

public class OrderDelayDemo {

    public static void main(String[] args) throws InterruptedException {

        Set<String> orderIds = new LinkedHashSet<String>(){{add("00000001");add("00000002");add("00000003");add("00000004");add("00000005");}};
        DelayQueue<OrderDelay> queue = new DelayQueue<OrderDelay>();
        int second = 0; //延迟的秒数
        for (String orderId : orderIds) {
            queue.put( new OrderDelay(orderId,++second) );
        }
        long start = System.currentTimeMillis();
        while (true){
            OrderDelay take = queue.take();
            long end = System.currentTimeMillis();
            System.out.println( (end-start) + "毫秒后删除订单"+take.getOrderId() );
        }
    }

}



