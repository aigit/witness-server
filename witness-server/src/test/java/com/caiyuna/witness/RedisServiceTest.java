/**
 * 
 */
package com.caiyuna.witness;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.caiyuna.witness.redis.RedisService;

import redis.clients.jedis.BitOP;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceTest.class);

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static CyclicBarrier cyclicBarrier;
    private static CountDownLatch countDownLatch = new CountDownLatch(5);
    // private volatile int countUser = 0;
    private Lock counterLock = new ReentrantLock();
    private AtomicInteger countUser = new AtomicInteger(0);

    static class CyclicBarrierThread extends Thread {
        /**
         * @Author Ldl
         * @Date 2018年3月13日
         * @since 1.0.0
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "到了");
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    static class BossThread extends Thread {
        /**
         * @Author Ldl
         * @Date 2018年3月13日
         * @since 1.0.0
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            System.out.println("Boss在会议室等待，总共有" + countDownLatch.getCount() + "个人开会...");
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("所有人都已经到齐了，开会吧...");
        }
    }

    static class EmployeeThread extends Thread {
        /**
         * @Author Ldl
         * @Date 2018年3月13日
         * @since 1.0.0
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ",到达会议室...");
            countDownLatch.countDown();
        }
    }

    volatile boolean shutDown;
    private AtomicInteger at = new AtomicInteger(2);

    @Autowired
    private RedisService redisService;

    @Test
    public void testlist() {
        final Jedis jedis = redisService.getResource();
        jedis.select(0);
        
        for (int i = 0; i < 10; i++) {
            jedis.lpush("mylist", "a" + i);
        }
        String rpop = jedis.rpop("mylist");
        Assert.assertEquals("a0", rpop);
        List<String> mylist = jedis.lrange("mylist", 0, -1);
        for (String string : mylist) {
            System.out.println(string);
        }

        jedis.ltrim("mylist", 0, 2);

        List<String> myPagelist = jedis.lrange("mylist", 3, -2);
        for (String string : myPagelist) {
            System.out.println("截取元素:" + string);
        }
        
         
        for (int i = 0; i < 20; i++) {
            jedis.lpush("listqueue", i + "c");
        }

        System.out.println("单个取出:" + jedis.rpop("listqueue"));

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future f1 = executor.submit(new ListConsumer(jedis, "消费者1"));
        Future f2 = executor.submit(new ListConsumer(jedis, "消费者2"));
        try {
            f1.get();
            f2.get();
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        jedis.close();
    }

    @Test
    public void pushAward() {
        Jedis jedis = redisService.getResource();
        Pipeline pipe = jedis.pipelined();
        for (int i = 0; i < 50000; i++) {
            pipe.lpush("awards", String.valueOf(i));
        }
        pipe.sync();
        LOGGER.info("already push awards,length:{}", jedis.llen("awards"));
        jedis.close();

    }

    @Test
    public void testVolatile() {
        shutDown = true;
    }

    @Test
    public void dowork() {

    }


    @Test
    public void testSet() {
        Jedis jedis = redisService.getResource();
        try {
            jedis.del("patient1", "patient2");

            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                jedis.sadd("patient1", String.valueOf(random.nextInt(50)));
            }
            for (int i = 0; i < 10; i++) {
                jedis.sadd("patient2", String.valueOf(random.nextInt(50)));
            }
            Set<String> pa1set = jedis.smembers("patient1");
            Set<String> pa2set = jedis.smembers("patient2");
            for (String doc1 : pa1set) {
                System.out.print(doc1 + ",");
            }
            System.out.print("\n");
            for (String doc2 : pa2set) {
                System.out.print(doc2 + ",");
            }
            System.out.print("\n");
            Set<String> commonset = jedis.sinter("patient1", "patient2");
            for (String commondoc : commonset) {
                System.out.print(commondoc + ",");
            }
            System.out.print("\n");
            Set<String> andset = jedis.sunion("patient1", "patient2");
            for (String ands : andset) {
                System.out.print(ands + ",");
            }
            System.out.print("\n");
            for (String doc1 : pa1set) {
                System.out.print(doc1 + ",");
                jedis.srem("patient1", doc1);
            }
            Assert.assertTrue(jedis.exists("patient1"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

    }

    // 订阅key过期触发事件
    @Test
    public void testPubSub() {
        Jedis jedis = redisService.getResource();
        try {
            jedis.select(1);
            jedis.subscribe(new KeyExpiredListener(), "__keyevent@1__:expired");
        } finally {
            jedis.close();
        }
    }

    @Test
    public void testBitSet() throws IOException {
        int[] initarr = new int[4000000];
        for (int i = 0; i < 4000000; i++) {
            initarr[i] = i;
        }
        initarr[13] = 0;
        int temp = initarr[32];
        initarr[32] = initarr[9];
        initarr[9] = temp;
        int[] counter = new int[10];
        int piece = (int) (initarr.length / 10);
        List<BitSet> bitarr = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            bitarr.add(new BitSet(piece));
        }
        for (Integer ints : initarr) {
            if (ints == 0) {
                bitarr.get(0).set(0, true);
                continue;
            }
            int x = (int) (ints / piece);
            int y = ints % piece;
            counter[x]++;
            bitarr.get(x).set(y, true);
        }
        int targetindex = 0;
        for (int i = 0; i < counter.length; i++) {
            if (counter[i] < piece) {
                targetindex = i;
                break;
            }
        }
        BitSet bs = (BitSet) bitarr.get(targetindex);
        Long targetnum = null;
        for (int i = 0; i < piece; i++) {
            if (!bs.get(i)) {
                targetnum = (long) (piece * targetindex +i);
                break;
            }
        }
        System.out.println("不包含数字:" + targetnum);

    }

    @Test
    public void testBitMap() {
        Jedis jedis = redisService.getResource();
        for (int i = 0; i < 100; i++) {
            if ((i % 3 == 0) || (i % 7 == 0)) {
                jedis.setbit("doct:signin:20180103", i, true);
            }
        }
        System.out.println("公因数:" + jedis.bitcount("doct:signin:20180103"));
        jedis.set("key1", "foobar");
        jedis.set("key2", "abcdef");
        jedis.bitop(BitOP.AND, "desck", "key1", "key2");
        System.out.println("destk:" + jedis.get("desck"));
        jedis.close();
    }

    private static boolean tryGetDistributedLock(Jedis jedis,String lockKey,String requestId,int expireTime){
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 并发原子操作
     * @author Ldl
     *
     */
    @Test
    public void testConcurrentOper() {
        Jedis jedis = redisService.getResource();
        jedis.select(0);
        ExecutorService exeservice = Executors.newFixedThreadPool(80);
        List<Callable<Void>> callers = new ArrayList<>();
        for (int i = 0; i < 80; i++) {
            callers.add(new ConcurrentThread(i + "-" + System.currentTimeMillis(), redisService));
        }
        try {
            exeservice.invokeAll(callers);
            exeservice.shutdown();
            LOGGER.info("{}个人抢了红包", countUser.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 原子操作
     * @author Ldl
     */
    private class ConcurrentThread implements Callable<Void>{

        private Jedis jediscli;
        private String callName;
        
        public ConcurrentThread(String callName, final RedisService redisService) {
            jediscli = redisService.getResource();
            this.callName = callName;
        }
        @Override
        public Void call() throws Exception {
            while (jediscli.llen("awards") > 0L) {
                String award = jediscli.rpop("awards");
                if (award != null) {
                    countUser.getAndIncrement();
                }
            }
            jediscli.close();
            return null;
        }
        
    }

    /**
     * 事务测试
     * @throws InterruptedException
     */
    @Test
    public void testTrans() throws InterruptedException {
        final Jedis jedis = redisService.getResource();
        jedis.select(0);
        // 乐观锁控制
        jedis.set("c1", "0");
        jedis.set("c2", "10");
        // jedis.set("c2", "a");
        LOGGER.info("c1值:{}", jedis.get("c1"));
        jedis.close();
        ExecutorService exeservice = Executors.newCachedThreadPool();
        List<Callable<Void>> callers = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            callers.add(new TransThread(i + "-" + System.currentTimeMillis(), redisService, i * 100L));
        }
        try {
            exeservice.invokeAll(callers);
            exeservice.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("c1 最后的值:{},c2 最后的值:{}", jedis.get("c1"), jedis.get("c2"));
    }

    public class TransThread implements Callable<Void> {
        private Jedis jediscli;
        private String callName;
        private Long prepareTime;

        /**
         * 构造函数
         */
        public TransThread(String callName, RedisService redisService, Long prepareTime) {
            jediscli = redisService.getResource();
            this.callName = callName;
            this.prepareTime = prepareTime;
        }

        @Override
        public Void call() {
            /*
             * 此种模式，大量并发下不能保证c1、c2对应的事务"隔离性"
             */
            /*if (Integer.parseInt(jediscli.get("c2")) > 9) {
                jediscli.incrBy("c1", 1);
                jediscli.decrBy("c2", 1);
                LOGGER.info("callerName:{}", callName);
            }*/
            

            try {
                /*
                 * 乐观锁 可保证隔离性,手动实现一致性
                 */
                // TimeUnit.MILLISECONDS.sleep(prepareTime); //分散开请求
                /*jediscli.watch("c1", "c2");
                if (Integer.parseInt(jediscli.get("c2")) > 9) {
                    Transaction trans = jediscli.multi();
                    trans.incrBy("c1", 1);
                    trans.decrBy("c2", 1);
                    List<Object> execed = trans.exec();
                    LOGGER.info("callerName:{},execed result:{}", callName, execed);
                }*/
                
                /*
                 * 抢单
                 */
                /*String orderSn = "BJ:WJ-GM: 23:34:18";
                jediscli.watch(orderSn);
                if (!"1".equals(jediscli.get(orderSn))) {
                    Transaction trans = jediscli.multi();
                    trans.setex(orderSn, 10, "1");
                    trans.lpush("orderSeque", callName + "_" + orderSn);
                    List<Object> execed = trans.exec();
                    LOGGER.info("callerName:{},execed result:{}", callName, execed);
                }
                LOGGER.info("callerName:{},orderSeque:{}", callName, jediscli.rpop("orderSeque"));*/

                /*
                 * 手动取消事务
                 */
                /*jediscli.watch("c1", "c2");
                Transaction trans = jediscli.multi();
                trans.incrBy("c1", 1);
                trans.decrBy("c2", 1);
                if (Integer.parseInt(jediscli.get("c2")) == 0) {
                    trans.discard();
                    return null;
                }
                List<Object> execed = trans.exec();
                LOGGER.info("execed result:{}", execed);
                for (Object object : execed) {
                    LOGGER.info("callerName:{},execd:{}", callName, object.toString());
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                jediscli.unwatch();
                jediscli.close();
            }
            return null;
        }
    }



    public static class ListConsumer implements Runnable {

        private final String consumerName;
        private Jedis jedis;

        /**
         * 构造函数
         */
        public ListConsumer(Jedis jedis, String consumerName) {
            this.consumerName = consumerName;
            this.jedis = jedis;
        }

        /**
         * @Author Ldl
         * @Date 2018年1月4日
         * @since 1.0.0
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            while (true) {
                List<String> result = jedis.brpop(30 * 1000, "listqueue");
                for (String s : result) {
                    LOGGER.info("consumerName:" + consumerName + "-" + s);
                }
                try {
                    if(consumerName.equals("消费者1")){
                        TimeUnit.SECONDS.sleep(2);
                    }else{
                        TimeUnit.SECONDS.sleep(3);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }




    @Test
    public void testCyclicBarrier(){
        cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            public void run() {
                System.out.println("人到齐了,开会吧...");
            }
        });
        for (int i = 0; i < 5; i++) {
            new CyclicBarrierThread().start();
        }
    }

    @Test
    public void testcountDownLatch() {
        new BossThread().start();
        for (int i = 0; i < 5; i++) {
            new EmployeeThread().start();
        }
    }

}
