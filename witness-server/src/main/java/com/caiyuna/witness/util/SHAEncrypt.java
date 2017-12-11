/**
 * 
 */
package com.caiyuna.witness.util;

import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SHA加密算法
 * @author Ldl 
 * @since 1.0.0
 */
public class SHAEncrypt {

    private static final Logger LOGGER = LoggerFactory.getLogger(SHAEncrypt.class);

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";

    /**
     * 
     * SHAEncrypt.SHA256()
     * @Author Ldl
     * @Date 2017年7月28日
     * @since 1.0.0
     * @param strText 要加密内容
     * @return
     */
    public static String shaLow(final String strText) {
        return shaEncrypt(strText, "SHA-256");
    }

    /**
     * 
     * SHAEncrypt.SHA512()
     * @Author Ldl
     * @Date 2017年7月28日
     * @since 1.0.0
     * @param strText 要加密内容
     * @return
     */
    public static String shaHigh(final String strText) {
        return shaEncrypt(strText, "SHA-512");
    }

    /**
     * SHA加密
     * SHAEncrypt.SHA()
     * @Author Ldl
     * @Date 2017年7月28日
     * @since 1.0.0
     * @param strText
     * @param strType
     * @return
     */
    private static String shaEncrypt(final String strText, String strType) {
        String result = null;
        if (strText != null && strText.length() > 0) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                messageDigest.update(strText.getBytes("UTF-8"));
                byte[] byteBuffer = messageDigest.digest();

                StringBuffer strHex = new StringBuffer();

                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHex.append('0');
                    }
                    strHex.append(hex);
                }
                result = strHex.toString();
            } catch (Exception e) {
                LOGGER.error("SHA加密方法异常：" + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
        return result;
    }
    
    public static byte[] hmacSHA1Encrypt(final String encryptText, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);

        byte[] text = encryptText.getBytes(ENCODING);
        return mac.doFinal(text);
    }

}
