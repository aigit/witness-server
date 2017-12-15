/**
 * 
 */
package com.caiyuna.witness.im;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.util.CharsetUtil;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public class SecureChatClientInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    private final String peerHost;

    private final int peerPort;

    /**
    * 构造函数
    */
    public SecureChatClientInitializer(SslContext sslCtx, String peerHost, int peerPort) {
        this.sslCtx = sslCtx;
        this.peerHost = peerHost;
        this.peerPort = peerPort;
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

        pipeline.addLast(sslCtx.newHandler(ch.alloc(), peerHost, peerPort));

        pipeline.addLast(new FixedLengthFrameDecoder(10240));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));

        pipeline.addLast(new SecureChatClientHandler());

    }

}
