/**
 * 
 */
package com.caiyuna.witness.config;

import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.GeoCoordinate;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public class Constants {

    public static final String SCENE_LOCATION_KEY = "scene.location";

    public static final Map<String, GeoCoordinate> CENTER_CITY_COORDINATE_MAP = new HashMap<>();

    static {
        CENTER_CITY_COORDINATE_MAP.put("1", value)
    }

}
