package com.notes.utils;

public class TimeUtil {

    /**
     * 秒表计时器 每隔一秒打印读秒信息,直到指定的秒数后结束方法
     * @param stopSeconds 结束秒数
     */
    public static void stopwatch(long stopSeconds) throws InterruptedException {
        long second = 0L ;
        while (second<stopSeconds){
            Thread.sleep(1000);
            System.out.println("第"+ ++second+"秒过去了..............");
        }

    }
}
