/**
 * 
 */
package com.caiyuna.witness.im;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.caiyuna.witness.config.Constants;
import com.caiyuna.witness.pos.Scene;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);

    private ChannelGroup group;

    /**
     * 构造函数
     */
    public TextWebSocketFrameHandler() {
    }


    /**
     * @Author Ldl
     * @Date 2017年10月12日
     * @since 1.0.0
     * @param ctx
     * @param evt
     * @throws Exception
     * @see io.netty.channel.ChannelInboundHandlerAdapter#userEventTriggered(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            ctx.pipeline().remove(HttpRequestHandler.class);
            HttpHeaders headers = ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestHeaders();
            // group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + "joined"));
            LOGGER.info("groupId:{}", headers.get(Constants.SCENE_LOCATION_KEY));
            Integer groupId = Integer.valueOf(headers.get(Constants.SCENE_LOCATION_KEY));
            group = ChannelGroupFactory.getGroupMap().get(groupId);
            group.add(ctx.channel());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * @Author Ldl
     * @Date 2017年10月12日
     * @since 1.0.0
     * @param ctx
     * @param msg
     * @throws Exception
     * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        LOGGER.info("服务器收到消息内容:{}", msg.text().toString());
        group.writeAndFlush(msg.retain());
        broadcastMessage(msg.retain().toString());

    }


    /**
     * @Author Ldl
     * @Date 2017年10月13日
     * @since 1.0.0
     * @param ctx
     * @param cause
     * @throws Exception
     * @see io.netty.channel.ChannelInboundHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    private void broadcastMessage(String message) {
        Scene scene = JSON.parseObject(message, Scene.class);
    }

}
