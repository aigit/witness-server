/**
 * 
 */
package com.caiyuna.witness.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caiyuna.witness.config.Constants;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.geo.GeoRadiusParam;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@Service
public class RedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private JedisPool jedisPool;

    /**
     * @Author Ldl
     * @Date 2017年12月4日
     * @since 1.0.0
     * @return
     * @see com.caiyuna.im.demo.redis.IRedisService#getResource()
     */
    public Jedis getResource() {
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
            jedis.close();
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
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Redis set error: " + e.getMessage() + " - " + key);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    public String set(String key, String val) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.set(key, val);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Redis set error: " + e.getMessage() + " - " + key);
            return null;
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

    public Long geoAdd(String key, double longitude, double latitude, String member) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.geoadd(key, longitude, latitude, member);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("geoAdd error,key:{},longitude:{},latitude:{},member:{},e:{} ", key, longitude, latitude, member);
            return null;
        } finally {
            returnResource(jedis);
        }
    }

    public Double geoDistance(Map<String, GeoCoordinate> memberCoordinateMap) {
        Jedis jedis = null;
        String tempDistKey = Constants.TEMP_DIST_CALCULATE + System.currentTimeMillis();
        LOGGER.info("tempDistKey：{}", tempDistKey);
        try {
            jedis = getResource();
             jedis.geoadd(tempDistKey, memberCoordinateMap);
            Object[] memberArr = memberCoordinateMap.keySet().toArray();
            LOGGER.info("memberArr：{},{}", memberArr[0], memberArr[1]);
            return jedis.geodist(tempDistKey, memberArr[0].toString(), memberArr[1].toString());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("geoDistance error,key:{},memberCoordinateMap:{},e:{} ", Constants.TEMP_DIST_CALCULATE, memberCoordinateMap, e);
            return null;
        } finally {
            jedis.del(tempDistKey);
            returnResource(jedis);
        }
    }

    public GeoRadiusResponse getNearCenterCity(String sceneId, double longitude, double latitude) {
        Jedis jedis = null;
        String locationKey = Constants.SCENE_NEARCITY_LOCATION_KEY + sceneId;
        try {
            jedis = getResource();
            jedis.geoadd(locationKey, Constants.CENTER_CITY_COORDINATE_MAP);
            jedis.geoadd(locationKey, longitude, latitude, sceneId);
            List<GeoRadiusResponse> geoRadius = jedis.georadiusByMember(locationKey, sceneId, 150d, GeoUnit.KM,
                                                                        GeoRadiusParam.geoRadiusParam().sortAscending().withDist().withCoord());
            if(geoRadius==null || geoRadius.size()<2){
                return null;
            }
            for (GeoRadiusResponse geoRadiusResponse : geoRadius) {
                if(geoRadiusResponse.getMemberByString().equals(sceneId)){
                    continue;
                }
                return geoRadiusResponse;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("geoAdd error,sceneId:{},longitude:{},latitude:{},e:{} ", sceneId, longitude, latitude, e);
            return null;
        } finally {
            jedis.del(locationKey);
            returnResource(jedis);
        }
    }

}
