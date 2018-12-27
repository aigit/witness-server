/**
 * 
 */
package com.caiyuna.witness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.caiyuna.witness.redis.RedisService;

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
    private static final Long RELEASE_SUCCESS = 1L;

    private static CyclicBarrier cyclicBarrier;
    private static CountDownLatch countDownLatch = new CountDownLatch(5);
    // private volatile int countUser = 0;
    private Lock counterLock = new ReentrantLock();
    private AtomicInteger countUser = new AtomicInteger(0);



    @Autowired
    private RedisService redisService;

    /**
     * 发红包
     */
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


    /**
     * 并发原子操作--抢红包
     * @author Ldl
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
     * 原子操作--抢红包
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
        // jedis.set("c1", "0");// 订单商品数
        // jedis.set("c2", "10");// 库存数
        // jedis.set("c2", "20");

        String orderSn = "BJ:WJ-GM: 23:34:17";
        jedis.set(orderSn, "0");// 未抢订单
        // LOGGER.info("订单商品数 c1值:{}", jedis.get("c1"));

        ExecutorService exeservice = Executors.newCachedThreadPool();
        List<Callable<Void>> callers = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            callers.add(new TransThread(i, i + "-" + System.currentTimeMillis(), redisService, (i % 5) * 10L));
        }
        try {
            exeservice.invokeAll(callers);
            exeservice.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // LOGGER.info("订单商品数量c1 最后的值:{},库存数量c2 最后的值:{}", jedis.get("c1"), jedis.get("c2"));
        LOGGER.info("订单号{},成功抢单的司机 SUCCESS_DRIVER:{}", orderSn, jedis.get(orderSn + "_DRIVER"));
        jedis.close();
    }

    public class TransThread implements Callable<Void> {
        private Jedis jediscli;
        private final RedisService redisService;
        private String callName;
        private Long prepareTime;
        private int callNo;

        /**
         * 构造函数
         */
        public TransThread(int callNo, String callName, RedisService redisService, Long prepareTime) {
            jediscli = redisService.getResource();
            this.callName = callName;
            this.prepareTime = prepareTime;
            this.callNo = callNo;
            this.redisService = redisService;
        }

        @Override
        public Void call() {
            /*
             * 此种模式，大量并发下不能保证c1、c2对应的事务隔离性、一致性、“原子性”
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

                // TimeUnit.MILLISECONDS.sleep(prepareTime); // 分散开请求
                // jediscli.watch("c1", "c2");
                /*Transaction trans = jediscli.multi();
                trans.incrBy("c1", 1);// 增加订单商品数
                TimeUnit.SECONDS.sleep(1);
                trans.decrBy("c2", 1);// 减少库存量
                LOGGER.info("C1:{}", redisService.getResource().get("c1"));
                List<Object> execed = trans.exec();
                LOGGER.info("callerName:{},execed result:{}", callName, execed);*/
                
                /*
                 * 抢单
                 */
                /*String orderSn = "BJ:WJ-GM: 23:34:22";
                jediscli.watch(orderSn);
                
                while (!"1".equals(jediscli.get(orderSn))) {
                    Transaction trans = jediscli.multi();
                    trans.set(orderSn, "1");
                    trans.set(orderSn + "_DRIVER", callName);
                    if (callNo > 13) {
                        List<Object> execed = trans.exec();
                        LOGGER.info("callerName:{},execed result:{}", callName, execed);
                    } else {
                        trans.discard();
                    }
                
                }*/


                /*
                 * 分布式锁
                 */
                String orderSn = "BJ:WJ-GM: 23:34:17";
                String lockKey=orderSn+"|lock";
                while (!"1".equals(jediscli.get(orderSn))) {
                    if (!tryLock(jediscli, lockKey, callName, 10000)) {
                        continue;
                    }
                    try {
                        jediscli.set(orderSn, "1");
                        jediscli.set(orderSn + "_DRIVER", callName);
                        TimeUnit.SECONDS.sleep(15);
                    } finally {
                        releaseLock(jediscli, lockKey, callName);
                    }
                
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                jediscli.unwatch();
                jediscli.close();
            }
            return null;
        }
    }

    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间 单位毫秒
     *        第一个为key，我们使用key来当锁，因为key是唯一的。
     *        第二个为value，我们传的是requestId，分布式锁要满足解铃还须系铃人，通过给value赋值为requestId，我们就知道这把锁是哪个请求加的了，在解锁的时候就可以有依据。
     *        第三个为nxxx，这个参数我们填的是NX，意思是SET IF NOT EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
     *        第四个为expx，这个参数我们传的是PX，意思是我们要给这个key加一个过期的设置，具体时间由第五个参数决定。
     *        第五个为time，与第四个参数相呼应，代表key的过期时间。
     * @return 是否获取成功
     */
    public static boolean tryLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseLock(Jedis jedis, String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

    public static void wrongGetLock1(Jedis jedis, String lockKey, String requestId, int expireTime) {
        Long result = jedis.setnx(lockKey, requestId);

        if (result == 1) {
            // 若在这里程序突然崩溃，则无法设置过期时间，将发生死锁
            jedis.expire(lockKey, expireTime);
        }
    }

    public static void wrongReleaseLock2(Jedis jedis, String lockKey, String requestId) {

        // 判断加锁与解锁是不是同一个客户端
        if (requestId.equals(jedis.get(lockKey))) {
            // 若在此时，这把锁突然不是这个客户端的，则会误解锁
            jedis.del(lockKey);
        }

    }

}
