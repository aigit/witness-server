/**
 * 
 */
package com.caiyuna.witness.Listener;

import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.caiyuna.witness.scene.SceneServer;
import com.caiyuna.witness.scene.SecureSceneServer;

import io.netty.channel.ChannelFuture;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {


    /**
     * @Author Ldl
     * @Date 2017年12月13日
     * @since 1.0.0
     * @param event
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        /*try {
            startSecureChatRoomServer();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (SSLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        startChatRoomServer();
    }

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

}
