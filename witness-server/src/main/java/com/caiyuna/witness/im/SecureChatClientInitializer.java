/**
 * 
 */
package com.caiyuna.witness.im;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.FastLzFrameDecoder;
import io.netty.handler.ssl.SslContext;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public class SecureChatClientInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    /**
    * 构造函数
    */
    public SecureChatClientInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    /**
     * @Author Ldl
     * @Date 2017年12月13日
     * @since 1.0.0
     * @param ch
     * @throws Exception
     * @see io.netty.channel.ChannelInitializer#initChannel(io.netty.channel.Channel)
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // pipeline.addLast(sslCtx.newHandler(ch.alloc(), peerHost, peerPort));

        pipeline.addLast(new FastLzFrameDecoder());

    }

}
