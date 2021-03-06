/**
 * 
 */
package com.caiyuna.witness.im;

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


    /**
     * 构造函数
     */
    public SceneServerInitializer(ChannelGroup channelGroup) {
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
        pipeline.addLast(new HttpRequestHandler("/wss"));
        pipeline.addLast(new WebSocketServerProtocolHandler("/wss", true));
        pipeline.addLast(new TextWebSocketFrameHandler());

    }

}
