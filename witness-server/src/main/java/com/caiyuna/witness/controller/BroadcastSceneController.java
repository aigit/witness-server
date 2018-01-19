/**
 * 
 */
package com.caiyuna.witness.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caiyuna.witness.pos.Scene;
import com.caiyuna.witness.service.ISceneService;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@RestController
@RequestMapping("scene")
public class BroadcastSceneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BroadcastSceneController.class);

    @Autowired
    private ISceneService sceneService;

    @Value("${netty.websocekt.scene.wsurl}")
    private String wsUrl;

    @RequestMapping("broadcast")
    public String broadCastScene(@RequestBody Scene scene) {
        LOGGER.info("scene to string：{}", scene.toString());
        try {
            sceneService.pushSceneDetails(scene, wsUrl);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("发送现场异常:{}", scene);
            return "系统异常";
        }
        return "已经通知了附近的伙伴^_^";
    }

}
