/**
 * 
 */
package com.caiyuna.witness.im;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.caiyuna.witness.pos.Scene;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public class SecureChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecureChatServerHandler.class);

    static final Map<Integer, ChannelGroup> channelsMap = new HashMap<>();

    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 构造函数
     */
    public SecureChatServerHandler() {
        /*for (int i = 0; i < 3; i++) {
            channelsMap.put(i + 1, new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
        }*/

    }

    /**
     * @Author Ldl
     * @Date 2017年12月20日
     * @since 1.0.0
     * @param ctx
     * @throws Exception
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /*ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(new GenericFutureListener<Future<Channel>>() {
            @Override
            public void operationComplete(Future<Channel> future) throws Exception {
                
            }
        });*/
        LOGGER.info("channel Active!!");
    }

    /**
     * @Author Ldl
     * @Date 2017年12月20日
     * @since 1.0.0
     * @param ctx
     * @param msg
     * @throws Exception
     * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Scene scene = JSON.parseObject(msg, Scene.class);
        // channelsMap.get(1).add(ctx.channel());
        for (Channel c : channels) {
            if (c != ctx.channel()) {
                c.writeAndFlush("[" + scene.toString() + "] " + msg + '\n');
            } else {
                c.writeAndFlush("[you] " + msg + '\n');
            }
        }
    }

    /**
     * @Author Ldl
     * @Date 2017年12月21日
     * @since 1.0.0
     * @param ctx
     * @param cause
     * @throws Exception
     * @see io.netty.channel.ChannelInboundHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
