/**
 * 
 */
package com.caiyuna.witness.Listener;

import java.net.InetSocketAddress;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

import com.caiyuna.witness.im.SceneServer;
import com.caiyuna.witness.im.SecureSceneServer;
import com.caiyuna.witness.redis.RedisService;

import io.netty.channel.ChannelFuture;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * @author Ldl 
 * @since 1.0.0
 */

public class StartupListener implements ApplicationListener<ApplicationReadyEvent> {

    private void startSecureChatRoomServer() throws Exception {
        SelfSignedCertificate cert = new SelfSignedCertificate();
        SslContext context = SslContextBuilder.forServer(cert.certificate(), cert.privateKey()).build();;
        final SecureSceneServer endpoint = new SecureSceneServer(context);
        ChannelFuture future = endpoint.start(new InetSocketAddress(8012));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            /**
             * @Author Ldl
             * @Date 2017年10月12日
             * @since 1.0.0
             * @see java.lang.Thread#run()
             */
            @Override
            public void run() {
                System.out.println("主动关闭连接");
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }

    private void startChatRoomServer() {
        final SceneServer endpoint = new SceneServer();
        ChannelFuture future = endpoint.start(new InetSocketAddress(8012));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            /**
             * @Author Ldl
             * @Date 2017年10月12日
             * @since 1.0.0
             * @see java.lang.Thread#run()
             */
            @Override
            public void run() {
                System.out.println("主动关闭连接");
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent arg0) {
        ApplicationContext cxt = arg0.getApplicationContext();
        RedisService redisService = cxt.getBean(RedisService.class);
        String mukv = redisService.get("fok");
        System.out.println("mukv..." + mukv);
    }

}
