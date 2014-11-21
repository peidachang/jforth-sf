package org.xforth.sf.netty.Initializer;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;

/**
 * Tcp client Initializer
 */
public class TcpClientInitializer extends BaseInitializer {
    private ChannelHandler tcpRequestEncoder;
    private ChannelHandler tcpResponseDecoder;

    @Override
    protected void addCodecHandler(ChannelPipeline pipeline) {
        pipeline.addLast("tcpResponseDecoder", tcpResponseDecoder)
                .addLast("tcpRequestEncoder", tcpRequestEncoder);
    }

    @Override
    protected void addBusinessHandler(ChannelPipeline pipeline) {

    }

    public void setTcpRequestEncoder(ChannelHandler tcpRequestEncoder) {
        this.tcpRequestEncoder = tcpRequestEncoder;
    }

    public void setTcpResponseDecoder(ChannelHandler tcpResponseDecoder) {
        this.tcpResponseDecoder = tcpResponseDecoder;
    }
}
