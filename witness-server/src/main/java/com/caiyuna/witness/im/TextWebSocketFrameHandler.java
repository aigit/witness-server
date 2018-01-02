/**
 * 
 */
package com.caiyuna.witness.im;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.caiyuna.witness.redis.RedisService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import redis.clients.jedis.GeoRadiusResponse;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@Component
@Scope("prototype")
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private RedisService redisService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);

    private static ChannelGroup group;

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
            // group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + "joined"));
            ctx.pipeline().remove(HttpRequestHandler.class);
            String url = ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestUri();
            LOGGER.info("URL param:{}", url);
            String sceneId = url.split("[?]")[1].split("=")[0];
            String location = url.split("[?]")[1].split("=")[1];
            Double longitude = Double.parseDouble(url.split("[?]")[1].split("=")[1].split(",")[0]);
            Double latitude = Double.parseDouble(url.split("[?]")[1].split("=")[1].split(",")[1]);
            GeoRadiusResponse geoRadius = redisService.getNearCenterCity(sceneId, longitude, latitude);

            // Integer groupId = Integer.valueOf(paramVal);

            LOGGER.info("geoRadius member:{},距离:{},坐标:{}", geoRadius.getMemberByString(), geoRadius.getDistance(), geoRadius.getCoordinate());

            group = ChannelGroupFactory.getGroupMap().get(geoRadius.getMemberByString());
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
        broadcastMessage(msg);
        group.remove(ctx.channel());
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

    private void broadcastMessage(TextWebSocketFrame msg) {
        group.writeAndFlush(msg.retain());
        // Scene scene = JSON.parseObject(msg.text(), Scene.class);
    }

}
