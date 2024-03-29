# Notes
开发相关的工作、学习笔记（源自出版书籍、网络博客、视频网课、实际工作等）

## JAVA基础

### 泛型
- 泛型将接口的概念进一步延伸，字面意思即“广泛的类型”。类、接口和方法代码可以应用于非常
广泛的类型，代码与它们能够操作的数据类型不再绑定到一起，同一套代码可以用于多种数据类型，这样，不仅可以复用代码，
降低耦合，而且可以提高代码的可读性和安全性。
- 泛型可以使用在接口、类、方法中，一般用<T>来表示类型参数。泛型就是数据类型参数化，处理的数据类型不是固定的，而是可以
作为参数传入。
- 泛型的内部原理：java的泛型是靠“泛型擦除”来实现的。对于泛型类，java编译器会将泛型代码转换为普通的非泛型代码；
即将类型参数T擦除，替换为Object，插入必要的强制类型转换，在JVM实际运行程序时，并不知道泛型的实际类型参数。
- 泛型的作用：更好的安全性与更好的可读性。
程序设计的一个重要目标就是尽可能的将bug消灭在编译过程中，而不是运行时再发现。只是用Object的话，类型弄措时，
开发环境和编译器并不能帮我们发现问题；而使用泛型的话，若编写时弄错类型，开发环境或编译器会提示类型错误，这称之为“类型安全”。
因此泛型提高了程序的安全性；另外还省去了使用Object时繁琐的类型转换，明确了类型信息，使得代码的可读性也更好。
- 但要记住，泛型是靠“擦除”实现了，编译成.class字节码文件时泛型参数都是Object。

### 

### 内部类

## 日常搬砖

### 单测 
1. 个人心得:
   - 尤其对于业务繁琐,要扣细节,调用了大量第三方api,工具类的模块,要做好单测,精确到代码的最小运行单元--每一个方法.
   - 尤其要注意调用第三方api,尤其在处理金额(小数计算),Date(日期)的三方工具类方法的接口,这些方法引用自网络开源或者同事编写的,
源码逻辑看上去可能没什么问题,但由于小数/日期的特殊性,有一定埋坑的可能.单测这些工具类方法没有发现问题,引用了这些方法的接口service实现
也要重点测.
   - 保证小单元的准确性,才能避免模块测试时,不断出现形形色色的bug,排查起来远不如单测小单元时容易.   
2. junit5的使用
   - @Test注解在方法上标记方法为测试方法,以便构建工具和IDE能够识别执行它们.Junit5不再需要手动将测试类与测试方法为public,包可见的访问级别足矣.eg:
```
    @Test
    void testDemo() {
        assertEquals(2, 1 + 1);
    }
```
   - 初始化与销毁: 需要执行一些代码来在测试逻辑执行前后完成一些初始化或销毁的操作.Junit5中有4个注解可能会用于如此工作:
      + @BeforeAll 只执行一次,执行时机是在所有测试和@BeforeEach注解方法之前
      + @BeforeEach 在每次测试执行之前执行
      + @AfterEach 在每个测试执行之后执行
      + @AfterAll 只执行一次,执行时机是在所有测试和@AfterEach注解方法之后
   - 断言
      + assertEquals(x,y) ; assertEquals(x,y,z)
      + assertTrue(true, () -> {...} );   
      + assertFalse(x)
      + assertNotNull(Object obj) 检查对象不为空
      + assertNull(Object obj) 检查对象为空
      + assertArrayEquals(expectrArr,resultArr) 检查两个数组是否相等
      + assertAll
      + ...
   - junit测试方法超时与否 eg:
   ```
        @Test
        @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
        void testTimeOut(){
            ...
        }

   ```
   - 异常测试
      + Junit 用代码处理提供了一个追踪异常的选项。你可以测试代码是否它抛出了想要得到的异常。expected 参数和 @Test 注释一起使用
   ```
        @Test(expected = NullPointerException)

   ```     
   - 参数测试
      略...
   - 其他注解
      + @After @Before @Ignore(忽略不需要执行的测试)   
   
   
## 常见场景解决方案

### 用户下订单30分钟未支付,则自动取消的实现   
- 容易想到的方案: 数据库轮询; 但除了小型项目否则不会采用;因为对服务器内存消耗过大,对数据库资源占用过多,并且存在延迟--最大的延迟时间就是轮询的间隔时间
- jdk的延迟队列
```
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

```
- 时间轮算法
```
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
```
- redis缓存
- 消息中间件   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   