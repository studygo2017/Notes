package com.notes.businessScenario.expiredOrders.wheelTimer;

import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
@AllArgsConstructor
public class OrderTimerTask implements TimerTask {
    private long orderId;

    private long timeout; //延时时间 单位秒

    private TimeUnit unit;

    public void run(Timeout timeout) throws Exception {
            System.out.println("删除xxx订单....................."+orderId);
    }

}
