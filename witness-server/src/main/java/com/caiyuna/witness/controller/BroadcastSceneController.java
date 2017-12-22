/**
 * 
 */
package com.caiyuna.witness.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.caiyuna.witness.im.WebSocketBroadEcho;
import com.caiyuna.witness.pos.Scene;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@RestController
@RequestMapping("scene")
public class BroadcastSceneController {

    @Value("${netty.websocekt.scene.wsurl}")
    private String wsUrl;


    private static final Logger LOGGER = LoggerFactory.getLogger(BroadcastSceneController.class);

    @RequestMapping("broadcast")
    public String broadCastScene(@RequestBody Scene scene) {
        LOGGER.info("scene to string：{}", scene.toString());
        String sceneMessage = JSON.toJSONString(scene);
        try {
            pushSceneDetails(sceneMessage);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("发送现场异常:{}", sceneMessage);
            return "系统异常";
        }
        return "已经通知了附近的伙伴^_^";
    }
    
    private void pushSceneDetails(String message) throws Exception {
        new WebSocketBroadEcho().send(message, wsUrl);
    }
    


}
