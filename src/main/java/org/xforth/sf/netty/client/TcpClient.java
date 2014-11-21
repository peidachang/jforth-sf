package org.xforth.sf.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xforth.sf.netty.Initializer.TcpClientInitializer;

/**
 * Tcp client
 */
public class TcpClient {
    private static final Logger logger = LoggerFactory.getLogger(TcpClient.class);

    private String localIp = "0.0.0.0";
    private int localPort;
    private String destIp = null;
    private int destPort = -1;
    private Bootstrap bootstrap = new Bootstrap();
    private NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private TcpClientInitializer tcpClientInitializer;
    private Channel channel;

    public void start() {
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .localAddress(localIp, localPort)
                .remoteAddress(destIp, destPort);
        bootstrap.handler(tcpClientInitializer);
        doConnect();
    }

    public void stop(){
        if(channel!=null){
            channel.close().syncUninterruptibly();
            channel = null;
        }
    }

    public void doConnect(){
        if (destIp == null || "".equals(destIp)){
            logger.warn("doConnect fail ,because destIp is not valid:"+destIp);
        }
        //add handlers

        ChannelFuture future = bootstrap.connect();
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                //finish sth
            }
        });
        channel = future.channel();
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public void setDestIp(String destIp) {
        this.destIp = destIp;
    }

    public void setDestPort(int destPort) {
        this.destPort = destPort;
    }
}
