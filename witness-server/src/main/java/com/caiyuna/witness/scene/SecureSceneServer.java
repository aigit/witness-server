/**
 * 
 */
package com.caiyuna.witness.scene;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public class SecureSceneServer extends SceneServer {

    private final SslContext context;

    /**
     * 构造函数
     */
    public SecureSceneServer(SslContext context) {
        this.context = context;
    }

    /**
     * @Author Ldl
     * @Date 2017年11月28日
     * @since 1.0.0
     * @param group
     * @return
     * @see com.caiyuna.witness.scene.SceneServer#createInitializer(io.netty.channel.group.ChannelGroup)
     */
    @Override
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        return new SecureSceneServerInitializer(group, context);
    }

}
