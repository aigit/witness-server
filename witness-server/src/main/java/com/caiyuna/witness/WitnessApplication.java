package com.caiyuna.witness;

import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.caiyuna.witness.im.SceneServer;
import com.caiyuna.witness.im.SecureSceneServer;

import io.netty.channel.ChannelFuture;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

@SpringBootApplication
public class WitnessApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(WitnessApplication.class);

    public static void main(String[] args) {
        /*SpringApplication springApplycation = new SpringApplication(WitnessApplication.class);
        springApplycation.addListeners(new StartupListener());
        springApplycation.run(args);*/

        SpringApplication.run(WitnessApplication.class, args);
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

    private static void startSecureChatRoomServer() throws Exception {
        LOGGER.info("呵呵呵");
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

    private static void startChatRoomServer() {
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
