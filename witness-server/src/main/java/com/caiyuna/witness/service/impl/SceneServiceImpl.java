/**
 * 
 */
package com.caiyuna.witness.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.caiyuna.witness.entity.Scene;
import com.caiyuna.witness.im.client.WebSocketClient;
import com.caiyuna.witness.redis.RedisService;
import com.caiyuna.witness.repository.SceneDao;
import com.caiyuna.witness.service.ISceneService;

import redis.clients.jedis.GeoCoordinate;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@Service
public class SceneServiceImpl implements ISceneService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SceneServiceImpl.class);

    @Autowired
    private WebSocketClient wsClient;
    @Autowired
    private SceneDao sceneDao;
    @Autowired
    private RedisService redisService;

    /**
     * @Author Ldl
     * @Date 2017年12月25日
     * @since 1.0.0
     * @param message
     * @throws Exception
     * @see com.caiyuna.witness.service.ISceneService#pushSceneDetails(java.lang.String)
     */
    @Override
    public String pushSceneDetails(Scene scene, String wsUrl) throws Exception {
        // TODO 存入mongo


        /*
         * 放入位置信息到缓存
         */
        // redisService.geoAdd(Constants.SCENE_LOCATION_KEY, scene.getLongitude(),
        // scene.getLatitude(), scene.getId());
        Map<String, GeoCoordinate> memberCoordinateMap = new HashMap<>();
        memberCoordinateMap.put("systemPoint", new GeoCoordinate(scene.getLongitude(), scene.getLatitude()));
        memberCoordinateMap.put("customPoint", new GeoCoordinate(scene.getCustomLocation().getLongitude(), scene.getCustomLocation().getLatitude()));
        Double dist = redisService.geoDistance(memberCoordinateMap);
        LOGGER.info("实际位置 偏差:{}", dist);
        if (dist > 20d) {
            return "1";
        }
        String sceneMessage = JSON.toJSONString(scene);
        wsClient.sendMessage(sceneMessage, scene, wsUrl);
        return "0";
        // new WebSocketBroadEcho().send(sceneMessage, wsUrl);

    }

    /**
     * @Author Ldl
     * @Date 2018年1月23日
     * @since 1.0.0
     * @param scene
     * @throws Exception
     * @see com.caiyuna.witness.service.ISceneService#saveSceneDetails(com.caiyuna.witness.entity.Scene)
     */
    @Override
    public void saveSceneDetails(Scene scene) throws Exception {
        sceneDao.save(scene);

    }

    /**
     * @Author Ldl
     * @Date 2018年2月8日
     * @since 1.0.0
     * @param sceneId
     * @return
     * @throws Exception
     * @see com.caiyuna.witness.service.ISceneService#findSceneById(java.lang.String)
     */
    @Override
    public Scene findSceneById(String sceneId) throws Exception {
        return sceneDao.findSceneById(sceneId);
    }

    /**
     * @Author Ldl
     * @Date 2018年2月23日
     * @since 1.0.0
     * @param scene
     * @return
     * @throws Exception
     * @see com.caiyuna.witness.service.ISceneService#measureDist(com.caiyuna.witness.entity.Scene)
     */
    @Override
    public String measureDist(Scene scene) throws Exception {
        Map<String, GeoCoordinate> memberCoordinateMap = new HashMap<>();
        memberCoordinateMap.put("systemPoint", new GeoCoordinate(scene.getLongitude(), scene.getLatitude()));
        memberCoordinateMap.put("customPoint", new GeoCoordinate(scene.getCustomLocation().getLongitude(), scene.getCustomLocation().getLatitude()));
        Double dist = redisService.geoDistance(memberCoordinateMap);
        LOGGER.info("实际位置 偏差:{}", dist);
        if (dist > 20d) {
            return "1";
        }
        return "0";
    }

}
