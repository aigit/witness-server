/**
 * 
 */
package com.caiyuna.witness.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.caiyuna.witness.entity.Scene;
import com.caiyuna.witness.im.client.WebSocketClient;
import com.caiyuna.witness.repository.SceneDao;
import com.caiyuna.witness.service.ISceneService;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@Service
public class SceneServiceImpl implements ISceneService {

    @Autowired
    private WebSocketClient wsClient;
    @Autowired
    private SceneDao sceneDao;

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

}
