/**
 * 
 */
package com.caiyuna.witness.im;

import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caiyuna.witness.config.NettyProperties;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@Component
public class WebSocketClientBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketClientBuilder.class);

    @Autowired
    private NettyProperties nettyProperties;

    private static Channel channel;

    public Channel getChannel() {
        if (channel != null) {
            return channel;
        }
        channel = buildChannelFuture();
        return channel;
    }

    public Channel buildChannelFuture() {
        SslContext sslCtx;
        try {
            sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } catch (SSLException e) {
            e.printStackTrace();
            LOGGER.error("buildChannelFuture 创建ChannelFuture异常");
            return null;
        }

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class).handler(new SecureChatServerInitializer(sslCtx));
        Channel ch = b.connect(nettyProperties.getSecureSceneServerHost(), nettyProperties.getSecureSceneServerPort()).channel();
        return ch;
    }

}
