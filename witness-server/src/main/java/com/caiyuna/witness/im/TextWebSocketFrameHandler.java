/**
 * 
 */
package com.caiyuna.witness.im;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Import;

import com.alibaba.fastjson.JSON;
import com.caiyuna.witness.config.Constants;
import com.caiyuna.witness.entity.Scene;
import com.caiyuna.witness.redis.RedisService;
import com.caiyuna.witness.service.ISceneService;
import com.caiyuna.witness.service.impl.SceneServiceImpl;
import com.caiyuna.witness.util.SpringUtil;
import com.caiyuna.witness.util.ThreadPoolUtil;
import com.caiyuna.witness.vo.SceneView;

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
// @Service
// @Scope("prototype")
@Import(value = { SpringUtil.class })
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
            // group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + "joined"));
            ctx.pipeline().remove(HttpRequestHandler.class);
            String url = ((WebSocketServerProtocolHandler.HandshakeComplete) evt).requestUri();
            LOGGER.info("URL param:{}", url);
            String sceneId = url.split("[?]")[1].split("=")[0];
            String location = url.split("[?]")[1].split("=")[1];
            Double longitude = Double.parseDouble(location.split(",")[0]);
            Double latitude = Double.parseDouble(location.split(",")[1]);
            RedisService redisService = SpringUtil.getApplicationContext().getBean(RedisService.class);
            GeoRadiusResponse geoRadius = redisService.getNearCenterCity(sceneId, longitude, latitude);
            if (geoRadius == null) {
                return;
            }
            LOGGER.info("geoRadius member:{},距离:{},坐标:{}", geoRadius.getMemberByString(), geoRadius.getDistance(), geoRadius.getCoordinate());
            group = ChannelGroupFactory.getGroupMap().get(Integer.parseInt(geoRadius.getMemberByString()));
            group.add(ctx.channel());
            LOGGER.info("group info:{}", group);
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
        ctx.close();
        // group.remove(ctx.channel());
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

    private void broadcastMessage(TextWebSocketFrame msg) throws Exception {
        LOGGER.info("group info:{}", group);
        group.writeAndFlush(msg.retain());
        LOGGER.info("group info:{}", group);
        /*
         * 为发布者自己保存一份
         */
        final Scene scene = JSON.parseObject(msg.text(), Scene.class);
        SceneView sv = new SceneView(scene.getId(), scene.getPublisher(), scene.getAvatarUrl(), scene.getLocationAddress(), scene.getImagedesclist());
        RedisService redisService = SpringUtil.getApplicationContext().getBean(RedisService.class);
        redisService.set(Constants.SCENE_SELF_SHOW_KEY + scene.getId(), JSON.toJSONString(sv));

        final ISceneService sceneService = SpringUtil.getApplicationContext().getBean(SceneServiceImpl.class);

        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    sceneService.saveSceneDetails(scene);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("保存场景明细失败", e);
                }
            }
        });
        
    }


}
