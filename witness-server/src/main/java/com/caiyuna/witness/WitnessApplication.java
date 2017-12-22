package com.caiyuna.witness;

import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.caiyuna.witness.im.SceneServer;
import com.caiyuna.witness.im.SecureChatServerInitializer;
import com.caiyuna.witness.im.SecureSceneServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
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
       /* try {
            startSecureChatRoomServerInGroup();
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
                LOGGER.info("主动关闭连接");
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
                LOGGER.info("主动关闭连接");
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }

    private static void startSecureChatRoomServerInGroup() throws Exception {
        SelfSignedCertificate ssc = new SelfSignedCertificate();
        SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new SecureChatServerInitializer(sslCtx));
            b.bind(8012).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

}
