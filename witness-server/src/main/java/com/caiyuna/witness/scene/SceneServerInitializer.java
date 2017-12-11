/**
 * 
 */
package com.caiyuna.witness.scene;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public class SceneServerInitializer extends ChannelInitializer<Channel> {

    private final ChannelGroup group;

    /**
     * 构造函数
     */
    public SceneServerInitializer(ChannelGroup group) {
        this.group = group;
    }

    /**
     * @Author Ldl
     * @Date 2017年10月12日
     * @since 1.0.0
     * @param ch
     * @throws Exception
     * @see io.netty.channel.ChannelInitializer#initChannel(io.netty.channel.Channel)
     */
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(64*1024));
        pipeline.addLast(new HttpRequestHandler("/ws"));
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler(group));

    }

}
