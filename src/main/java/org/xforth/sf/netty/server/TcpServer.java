package org.xforth.sf.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xforth.sf.netty.Initializer.TcpServerInitializer;


/**
 * Tcp server
 */
public class TcpServer {
    private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);
    private int MAX_RETRY = 20;
    private int RETRY_TIMEOUT = 30 * 1000;

    private String accessIp = "0.0.0.0";
    private int accessPort = 8888;
    private Channel channel;

    private TcpServerInitializer tcpServerInitializer;

    public void start(){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(tcpServerInitializer)
                .localAddress(this.accessIp, this.accessPort)
                .option(ChannelOption.SO_BACKLOG, 10240)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_RCVBUF, 8 * 1024)//不宜太小,否则会有性能问题,默认为8K,超过64K时需要在bind前设置
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_LINGER, -1);
        boolean binded = false;
        int retryCount = 0;
        do {
            try {
                channel = bootstrap.bind().syncUninterruptibly().channel();
                binded = true;
            }catch(Exception e){
                logger.warn("start failed : " + e + ", and retry...");
                if (++retryCount >= MAX_RETRY) {
                    throw e;
                }
                try {
                    Thread.sleep(RETRY_TIMEOUT);
                } catch (InterruptedException ignored) {
                }
            }
        }while(!binded);
        logger.info("started successfully in ip:" + this.accessIp + " port:" + this.accessPort);
    }

    public void stop(){
        if(channel!=null){
            channel.close().syncUninterruptibly();
            channel = null;
        }
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }

    public void setAccessPort(int accessPort) {
        this.accessPort = accessPort;
    }
}