/**
 * 
 */
package com.caiyuna.witness.im;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public class SecureSceneServerInitializer extends SceneServerInitializer {

    private final SslContext context;
    private final ChannelGroup group;

    /**
     * 构造函数
     * @param group
     */
    public SecureSceneServerInitializer(ChannelGroup group, SslContext context) {
        super(group);
        this.group = group;
        this.context = context;
    }


    /**
     * @Author Ldl
     * @Date 2017年11月28日
     * @since 1.0.0
     * @param ch
     * @throws Exception
     * @see com.caiyuna.witness.im.SceneServerInitializer#initChannel(io.netty.channel.Channel)
     */
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(context.newHandler(ch.alloc()));

        pipeline.addLast(new FixedLengthFrameDecoder(1 << 8));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());
        pipeline.addLast(new SecureChatServerHandler());

    }

}
