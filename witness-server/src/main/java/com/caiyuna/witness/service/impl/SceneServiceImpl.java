/**
 * 
 */
package com.caiyuna.witness.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.caiyuna.witness.im.client.WebSocketClient;
import com.caiyuna.witness.pos.Scene;
import com.caiyuna.witness.redis.RedisService;
import com.caiyuna.witness.service.ISceneService;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@Service
public class SceneServiceImpl implements ISceneService {

    @Value("${netty.websocekt.scene.wsurl}")
    private String wsUrl;

    @Autowired
    private RedisService redisService;

    @Autowired
    private WebSocketClient wsClient;

    /**
     * @Author Ldl
     * @Date 2017年12月25日
     * @since 1.0.0
     * @param message
     * @throws Exception
     * @see com.caiyuna.witness.service.ISceneService#pushSceneDetails(java.lang.String)
     */
    @Override
    public void pushSceneDetails(Scene scene, String wsUrl) throws Exception {
        // TODO 存入mongo


        /*
         * 放入位置信息到缓存
         */
        // redisService.geoAdd(Constants.SCENE_LOCATION_KEY, scene.getLongitude(),
        // scene.getLatitude(), scene.getId());

        String sceneMessage = JSON.toJSONString(scene);
        wsClient.sendMessage(sceneMessage, scene, wsUrl);
        // new WebSocketBroadEcho().send(sceneMessage, wsUrl);

    }

}
