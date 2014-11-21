package org.xforth.sf.netty.Initializer;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;

/**
 * Tcp server initializer
 */
public class TcpServerInitializer extends BaseInitializer {

    private ChannelHandler tcpRequestDecoder;
    private ChannelHandler tcpResponseEncoder;
    private ChannelHandler tcpRequestHandler;
    @Override
    protected void addCodecHandler(ChannelPipeline pipeline) {
        pipeline.addLast("tcpRequestDecoder", tcpRequestDecoder)
                .addLast("tcpResponseEncoder", tcpResponseEncoder);
    }

    @Override
    protected void addBusinessHandler(ChannelPipeline pipeline) {
        pipeline.addLast("handler", tcpRequestHandler);
    }


    public void setTcpRequestDecoder(ChannelHandler tcpRequestDecoder) {
        this.tcpRequestDecoder = tcpRequestDecoder;
    }


    public void setTcpResponseEncoder(ChannelHandler tcpResponseEncoder) {
        this.tcpResponseEncoder = tcpResponseEncoder;
    }

    public void setTcpRequestHandler(ChannelHandler tcpRequestHandler) {
        this.tcpRequestHandler = tcpRequestHandler;
    }
}
