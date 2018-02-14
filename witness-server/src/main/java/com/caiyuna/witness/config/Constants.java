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

    public static final String SCENE_NEARCITY_LOCATION_KEY = "scene.location.nearcity:";

    public static final Map<String, GeoCoordinate> CENTER_CITY_COORDINATE_MAP = new HashMap<>();

    static {
        CENTER_CITY_COORDINATE_MAP.put("1", new GeoCoordinate(116.1172d, 39.9385d));// 北京
        CENTER_CITY_COORDINATE_MAP.put("2", new GeoCoordinate(117.1891d, 39.1289d));// 天津
        CENTER_CITY_COORDINATE_MAP.put("3", new GeoCoordinate(115.20491d, 36.06979d));// 天津
    }

    public static final String SCENE_SELF_SHOW_KEY = "scene:userself:";

}
