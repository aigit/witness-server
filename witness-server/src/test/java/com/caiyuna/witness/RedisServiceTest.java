/**
 * 
 */
package com.caiyuna.witness;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.caiyuna.witness.redis.RedisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceTest.class);

    @Autowired
    private RedisService redisService;

    @Test
    public void testlist() {
        Jedis jedis = redisService.getResource();
        /*for (int i = 0; i < 10; i++) {
            jedis.lpush("mylist", "a" + i);
        }*/
        List<String> mylist = jedis.lrange("mylist", 0, -1);
        
        for (String string : mylist) {
            System.out.println(string);
        }
        
        // jedis.ltrim("mylist", 0, 2);
        List<String> myPagelist = jedis.lrange("mylist", 3, -1);
        for (String string : myPagelist) {
            System.out.println("page ele:" + string);
        }
        jedis.select(0);

        jedis.set("b2", "0");
        for (int i = 0; i < 5; i++) {
            LOGGER.info("当前时间:" + System.currentTimeMillis());
            /*
             * jianew Thread( new Runnable() {
             * public void run() {
             * Jedis jedis = redisService.getResource();
             * Transaction trans = jedis.multi();
             * if (trans.get("b2").equals("1")) {
             * LOGGER.info("后来的放弃了");
             * trans.discard();
             * }
             * trans.set("b2", "1");
             * trans.exec();
             * }
             * }).start();
             */
            Transaction trans = jedis.multi();

            if (trans.get("b2").equals("1")) {
                LOGGER.info("后来的放弃了");
                trans.discard();
            }
            trans.set("b2", "1");
            Response<String> b2 = trans.get("b2");
            trans.exec();

            System.out.println("trans get:" + b2.get());
        }

        /*String rpop = jedis.rpop("mylist");
        Assert.assertEquals("a2", rpop);*/


    }

}
