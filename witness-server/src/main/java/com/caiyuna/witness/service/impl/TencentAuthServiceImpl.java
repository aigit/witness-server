/**
 * 
 */
package com.caiyuna.witness.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.caiyuna.witness.config.WitnessServiceProperties;
import com.caiyuna.witness.service.ITencentAuthService;
import com.caiyuna.witness.util.SHAEncrypt;

/**
 * @author Ldl 
 * @since 1.0.0
 */
@Service
public class TencentAuthServiceImpl implements ITencentAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TencentAuthServiceImpl.class);

    @Autowired
    private WitnessServiceProperties properties;

    /**
     * @Author Ldl
     * @Date 2017年11月10日
     * @since 1.0.0
     * @param isreusable 是否多次有效
     * @param isbinddoc 是否绑定文件
     * @return
     * @see com.caiyuna.witness.service.ITencentAuthService#getCosAuthSign(boolean, boolean)
     */
    @Override
    public String getCosAuthSign(boolean isreusable, boolean isbinddoc) {
        LOGGER.info("系统属性:{}", properties.toString());
        long currentTime = System.currentTimeMillis()/1000l;
        long expireTime = currentTime+properties.getAuthSignValidity()*60l;
        int randomi = new Random().nextInt(10000);
        StringBuilder orignalsb = new StringBuilder();
        orignalsb.append("a").append("=").append(properties.getAppId());
        orignalsb.append("&").append("b").append("=").append(properties.getBucketName());
        orignalsb.append("&").append("k").append("=").append(properties.getSecretId());
        if (isreusable) {
            orignalsb.append("&").append("e").append("=").append(expireTime);
        } else {
            orignalsb.append("&").append("e").append("=").append(0);
        }
        orignalsb.append("&").append("t").append("=").append(currentTime);
        orignalsb.append("&").append("r").append("=").append(randomi);
        orignalsb.append("&").append("u").append("=").append(0);
        orignalsb.append("&").append("f").append("=");
        String orignalText = orignalsb.toString();
        LOGGER.error("SHA加密前原始内容:{}", orignalText);
        byte[] signTmp = null;
        try {
            signTmp = SHAEncrypt.hmacSHA1Encrypt(orignalText, properties.getSecretKey());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("获取cos鉴权签名异常");
            return null;
        }
        byte[] origbyte = null;
        try {
            origbyte = orignalText.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("获取cos鉴权签名异常");
            return null;
        }
        int signTempLength = signTmp.length;
        int origlength = origbyte.length;
        int signByteLength = signTempLength + origlength;
        byte[] signbuffer = new byte[signByteLength];
        System.arraycopy(signTmp, 0, signbuffer, 0, signTempLength);
        System.arraycopy(origbyte, 0, signbuffer, signTempLength, origlength);
        return Base64Utils.encodeToString(signbuffer);
    }

}
