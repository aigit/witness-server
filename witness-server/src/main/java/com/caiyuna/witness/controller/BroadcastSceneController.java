/**
 * 
 */
package com.caiyuna.witness.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caiyuna.witness.entity.Scene;
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
            String result = sceneService.pushSceneDetails(scene, wsUrl);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("发送现场异常:{}", scene);
            return "99";
        }
    }

    @RequestMapping("/picture/detail")
    public List<String> getPictureDetail(String sceneId) {
        LOGGER.info("getPictureDetail,sceneId：{}", sceneId);
        try {
            Scene scene = sceneService.findSceneById(sceneId);
            return scene.getImagedesclist();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("getPictureDetail exception", e);
        }
        return null;
    }

}
