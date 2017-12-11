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
public class WitnessServiceProperties {

    // private static final Integer AREA_RANGE_THRESHOLD = 100;

    @Value("${system.sceneDistanceThreshold}")
    private Integer sceneDistanceThreshold;
    @Value("${tencent.cloud.appId}")
    private String appId;
    @Value("${tencent.cos.bucketName}")
    private String bucketName;
    @Value("${tencent.ci.SecretId}")
    private String secretId;
    @Value("${tencent.ci.SecretKey}")
    private String secretKey;
    @Value("${tencent.auth.sign.validity}")
    private Integer authSignValidity;

    public Integer getSceneDistanceThreshold() {
        return sceneDistanceThreshold;
    }

    public void setSceneDistanceThreshold(Integer sceneDistanceThreshold) {
        this.sceneDistanceThreshold = sceneDistanceThreshold;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }


    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getAuthSignValidity() {
        return authSignValidity;
    }

    public void setAuthSignValidity(Integer authSignValidity) {
        this.authSignValidity = authSignValidity;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public String toString() {
        return "WitnessServiceProperties [sceneDistanceThreshold=" + sceneDistanceThreshold + ", appId=" + appId + ", bucketName=" + bucketName
                + ", secretId=" + secretId + ", secretKey=" + secretKey + ", authSignValidity=" + authSignValidity + "]";
    }


}
