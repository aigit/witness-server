/**
 * 
 */
package com.caiyuna.witness.controller;

import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.caiyuna.witness.im.SecureChatClientInitializer;
import com.caiyuna.witness.im.WebSocketClientBuilder;
import com.caiyuna.witness.pos.Scene;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.CharsetUtil;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@RestController
@RequestMapping("scene")
public class BroadcastSceneController {
    
    @Autowired
    private WebSocketClientBuilder clientBuilder;


    private static final Logger LOGGER = LoggerFactory.getLogger(BroadcastSceneController.class);

    @RequestMapping("broadcast")
    public String broadCastScene(@RequestBody Scene scene) {
        LOGGER.info("scene to string：{},{}", scene.toString());
        String sceneMessage = JSON.toJSONString(scene);
        try {
            pushSceneDetails(sceneMessage);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("发送现场异常:{}", sceneMessage);
            return "系统异常";
        }
        return "已经通知了附近的伙伴^_^";
    }
    
    private void pushSceneDetails(final String message) throws Exception {
        /*Channel channel = clientBuilder.buildChannelFuture();
        ChannelFuture future = channel.writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8)).sync();
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    LOGGER.info("成功广播了一条现场:{}", message);
                } else {
                    LOGGER.info("广播现场失败了:{}", message);
                }

            }
        });*/

        SslContext sslCtx = null;
        try {
            sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } catch (SSLException e) {
            e.printStackTrace();
            LOGGER.error("buildChannelFuture 创建ChannelFuture异常");
        }

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class).handler(new SecureChatClientInitializer(sslCtx, "127.0.0.1", 8012));
        Channel ch = b.connect("127.0.0.1", 8012).sync().channel();
        ChannelFuture future = ch.writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8)).sync();
    }
    


}
