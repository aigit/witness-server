/**
 * 
 */
package com.caiyuna.witness.config;

import io.netty.util.AttributeKey;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public interface Constants {

    String SCENE_LOCATION_KEY = "scene.location";

    AttributeKey<String> NETTY_CHANNEL_KEY = AttributeKey.newInstance(SCENE_LOCATION_KEY);

}
