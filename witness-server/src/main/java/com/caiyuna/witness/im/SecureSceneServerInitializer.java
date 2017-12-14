/**
 * 
 */
package com.caiyuna.witness.im;

import javax.net.ssl.SSLEngine;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public class SecureSceneServerInitializer extends SceneServerInitializer {

    private final SslContext context;

    /**
     * 构造函数
     * @param group
     */
    public SecureSceneServerInitializer(ChannelGroup group, SslContext context) {
        super(group);
        this.context = context;
    }

    /**
     * @Author Ldl
     * @Date 2017年11月28日
     * @since 1.0.0
     * @param ch
     * @throws Exception
     * @see com.caiyuna.witness.im.SceneServerInitializer#initChannel(io.netty.channel.Channel)
     */
    @Override
    protected void initChannel(Channel ch) throws Exception {
        super.initChannel(ch);
        SSLEngine engine = context.newEngine(ch.alloc());
        engine.setUseClientMode(false);
        ch.pipeline().addFirst(new SslHandler(engine));
    }

}
