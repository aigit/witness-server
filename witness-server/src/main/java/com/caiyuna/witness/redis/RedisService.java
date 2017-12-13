/**
 * 
 */
package com.caiyuna.witness.redis;

import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@Service
public class RedisService {

    private static final Logger LOGGER = Logger.getLogger(RedisService.class);

    @Autowired
    private JedisPool jedisPool;

    /**
     * @Author Ldl
     * @Date 2017年12月4日
     * @since 1.0.0
     * @return
     * @see com.caiyuna.im.demo.redis.IRedisService#getResource()
     */
    private Jedis getResource() {
        return jedisPool.getResource();
    }

    /**
     * @Author Ldl
     * @Date 2017年12月4日
     * @since 1.0.0
     * @param jedis
     * @see com.caiyuna.im.demo.redis.IRedisService#returnResource(redis.clients.jedis.Jedis)
     */
    public void returnResource(Jedis jedis) {
        if (jedis != null) {
            // jedisPool.close();
        }

    }

    /**
     * @Author Ldl
     * @Date 2017年12月4日
     * @since 1.0.0
     * @param key
     * @param value
     * @see com.caiyuna.im.demo.redis.IRedisService#set(java.lang.String, java.lang.String)
     */
    public void hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            jedis.hset(key, field, value);
            LOGGER.info("Redis set success - " + key + ", value:" + value);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Redis set error: " + e.getMessage() + " - " + key + ", value:" + value);
        } finally {
            returnResource(jedis);
        }

    }

    public Set<String> smember(String key) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.zrangeByScore(key, 0d, -1d);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Redis set error: " + e.getMessage() + " - " + key);
            return null;
        } finally {
            returnResource(jedis);
        }

    }

    public void zadd(String key, Map<String, Double> map) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            jedis.zadd(key, map);
            LOGGER.info("Redis set success - " + key + ", value:" + map);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Redis set error: " + e.getMessage() + " - " + key + ", value:" + map);
        } finally {
            returnResource(jedis);
        }

    }

    /**
     * @Author Ldl
     * @Date 2017年12月4日
     * @since 1.0.0
     * @param key
     * @return
     * @see com.caiyuna.im.demo.redis.IRedisService#get(java.lang.String)
     */
    public String get(String key) {
        return null;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月4日
     * @since 1.0.0
     * @param key
     * @return
     * @see com.caiyuna.im.demo.redis.IRedisService#hget(java.lang.String)
     */
    public String hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Redis set error: " + e.getMessage() + " - " + key + ", field:" + field);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

}
