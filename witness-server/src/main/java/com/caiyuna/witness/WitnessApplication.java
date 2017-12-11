package com.caiyuna.witness;

import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.caiyuna.witness.scene.SecureSceneServer;

import io.netty.channel.ChannelFuture;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;


@SpringBootApplication
public class WitnessApplication 
{
    public static void main(String[] args) {
        SpringApplication.run(WitnessApplication.class, args);
        /*try {
            startChatRoomServer();
        } catch (CertificateException | SSLException e) {
            e.printStackTrace();
        }*/
    }

    private static void startChatRoomServer() throws CertificateException, SSLException {
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



    // @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8011);
        connector.setSecure(false);

        connector.setRedirectPort(8443);
        return connector;
    }
}
