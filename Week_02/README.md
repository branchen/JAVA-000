# 作业1
写一段对于不同GC的总结

测试代码：
```java

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
/*
演示GC日志生成与解读
*/
public class GCLogAnalysis {
    private static Random random = new Random();
    public static void main(String[] args) {
        // 当前毫秒时间戳
        long startMillis = System.currentTimeMillis();
        // 持续运行毫秒数; 可根据需要进行修改
        long timeoutMillis = TimeUnit.SECONDS.toMillis(1);
        // 结束时间戳
        long endMillis = startMillis + timeoutMillis;
        LongAdder counter = new LongAdder();
        System.out.println("正在执行...");
        // 缓存一部分对象; 进入老年代
        int cacheSize = 2000;
        Object[] cachedGarbage = new Object[cacheSize];
        // 在此时间范围内,持续循环
        while (System.currentTimeMillis() < endMillis) {
            // 生成垃圾对象
            Object garbage = generateGarbage(100*1024);
            counter.increment();
            int randomIndex = random.nextInt(2 * cacheSize);
            if (randomIndex < cacheSize) {
                cachedGarbage[randomIndex] = garbage;
            }
        }
        System.out.println("执行结束!共生成对象次数:" + counter.longValue());
    }

    // 生成对象
    private static Object generateGarbage(int max) {
        int randomSize = random.nextInt(max);
        int type = randomSize % 4;
        Object result = null;
        switch (type) {
            case 0:
                result = new int[randomSize];
                break;
            case 1:
                result = new byte[randomSize];
                break;
            case 2:
                result = new double[randomSize];
                break;
            default:
                StringBuilder builder = new StringBuilder();
                String randomString = "randomString-Anything";
                while (builder.length() < randomSize) {
                    builder.append(randomString);
                    builder.append(max);
                    builder.append(randomSize);
                }
                result = builder.toString();
                break;
        }
        return result;
    }
}
```

##### 串行化GC
启动参数
````java
java -XX:+UseSerialGC -Xms512m -Xmx512m -Xlog:gc GCLogAnalysis.java
-XX:+UseSerialGC 配置串行GC
````
总结：SerialGC、ParNewGC 都是单线程的垃圾收集器，不能进行并行处理。在进行gc时，都会触发stw，停止所有应用线程。

##### 并行化GC
启动参数
````java
java -XX:+UseParallelGC -Xms512m -Xmx512m -Xlog:gc GCLogAnalysis.java
-XX:+UseParallelGC 配置并行GC
````
总结：ParallelGC和SerialGC对象分配速度和总的STW时间性能相似，但是平均STW时间相差近半。


##### cms.
启动参数
`-XX:+UseConcMarkSweepGC`

总结：‐XX:+UseParNewGC ‐XX:+UseConcMarkSweepGC 是等价的。但如果只指定 ‐XX:+UseParNewGC 参数则老年代GC会使用SerialGC。使用CMS时，命令行参数中会自动计算出年轻代、老年代的初始值和最大值，以及最大晋升阈值等信息（例如 ‐ XX:MaxNewSize=178958336 ‐XX:NewSize=178958336 ‐ XX:OldSize=357912576 ）

##### G1
启动参数
`‐XX:+UseG1GC`
总结：原则上不能指定G1垃圾收集器的年轻代
大小，否则不仅是画蛇添足，更是自废武功了。因为G1的回收方式是小批量划定 区块（region）进行，可能一次普通GC中既有年轻代又有老年代，可能某个区块 一会是老年代，一会又变成年轻代了。

##### cms和g1
启动参数
`java  -XX:+UseG1GC  -XX:ConcGCThreads=3   -XX:MaxGCPauseMillis=50  -Xms1g -Xmx1g -XX:+PrintGCDetails  -XX:-PrintGCApplicationStoppedTime GCLogAnalysis.java
-XX:+UseParallelGC 配置并行GC`

总结：G1和CMS比较总结：G1触发GC的频率要比CMS高，但是G1平均STW时间性能快5倍，G1最大STW时间也比CMS平均快60%。两者内存的分配速度相差不大.
