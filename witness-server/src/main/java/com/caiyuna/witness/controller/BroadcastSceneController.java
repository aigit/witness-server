/**
 * 
 */
package com.caiyuna.witness.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caiyuna.witness.pos.Scene;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@RestController
@RequestMapping("scene")
public class BroadcastSceneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BroadcastSceneController.class);

    @RequestMapping("broadcast")
    public String broadCastScene(@RequestBody Scene scene) {
        LOGGER.info("scene to string：{},{}", scene.toString());
        return "已经通知了附近的伙伴^_^";
    }

}
