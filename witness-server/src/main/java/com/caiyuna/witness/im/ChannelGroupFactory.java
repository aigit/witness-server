/**
 * 
 */
package com.caiyuna.witness.im;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public class ChannelGroupFactory {

    private static final Map<Integer, ChannelGroup> groupMap;

    static {
        groupMap = new HashMap<>();
        init();
    }

    static void init(){
        groupMap.put(1, new DefaultChannelGroup("beijing", new GroupEventExecutor(), false));
        groupMap.put(2, new DefaultChannelGroup("tianjin", new GroupEventExecutor(), false));
        groupMap.put(3, new DefaultChannelGroup("nanle", new GroupEventExecutor(), false));
    }

    public static Map<Integer, ChannelGroup> getGroupMap() {
        if (groupMap == null) {
            init();
        }
        return groupMap;
    }

}
