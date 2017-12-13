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

    @Value(value = "${netty.securescene.server.port:8012}")
    private Integer secureSceneServerPort;

    public Integer getSecureSceneServerPort() {
        return secureSceneServerPort;
    }

}
