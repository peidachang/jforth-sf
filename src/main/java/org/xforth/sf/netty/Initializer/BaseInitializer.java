package org.xforth.sf.netty.Initializer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

/**
 * Base initializer
 * impl of ApplicationContextAware or lookup
 *
 * 可以统计流量和日志
 */
public abstract class BaseInitializer extends ChannelInitializer {

    @Override
    public void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //这里可以统计流量和日志
        addCodecHandler(pipeline);
        addBusinessHandler(pipeline);
    }

    protected abstract void addCodecHandler(ChannelPipeline pipeline);

    protected abstract void addBusinessHandler(ChannelPipeline pipeline);

}
