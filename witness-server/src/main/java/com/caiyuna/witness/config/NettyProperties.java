/**
 * 
 */
package com.caiyuna.witness.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@Component
@ConfigurationProperties
public class NettyProperties {

    @Value(value = "${netty.securescene.server.port}")
    private Integer secureSceneServerPort;

    @Value(value = "${netty.securescene.server.host}")
    private String secureSceneServerHost;

    public Integer getSecureSceneServerPort() {
        return secureSceneServerPort;
    }

    public String getSecureSceneServerHost() {
        return secureSceneServerHost;
    }

}
