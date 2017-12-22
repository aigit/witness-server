/**
 * 
 */
package com.caiyuna.witness.im;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public class WebSocketBroadEcho extends WebSocketListener {


    private String message;


    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketBroadEcho.class);

    public void send(String message, String wsUrl) {
        this.message = message;
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(0, TimeUnit.MILLISECONDS).build();
        Request request = new Request.Builder().url(wsUrl).build();
        client.newWebSocket(request, this);
        client.dispatcher().executorService().shutdown();

    }

    /**
     * @Author Ldl
     * @Date 2017年12月22日
     * @since 1.0.0
     * @param webSocket
     * @param response
     * @see okhttp3.WebSocketListener#onOpen(okhttp3.WebSocket, okhttp3.Response)
     */
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        if (!StringUtils.isEmpty(message)) {
            webSocket.send(message);
        }
        webSocket.close(1000, "再见,world");
    }


    /**
     * @Author Ldl
     * @Date 2017年12月22日
     * @since 1.0.0
     * @param webSocket
     * @param text
     * @see okhttp3.WebSocketListener#onMessage(okhttp3.WebSocket, java.lang.String)
     */
    @Override
    public void onMessage(WebSocket webSocket, String text) {
        LOGGER.info("MESSAGE：{}", text);
    }

    /**
     * @Author Ldl
     * @Date 2017年12月22日
     * @since 1.0.0
     * @param webSocket
     * @param bytes
     * @see okhttp3.WebSocketListener#onMessage(okhttp3.WebSocket, okio.ByteString)
     */
    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        LOGGER.info("MESSAGE：{}", bytes.base64());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        System.out.println("CLOSE: " + code + " " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }

}
