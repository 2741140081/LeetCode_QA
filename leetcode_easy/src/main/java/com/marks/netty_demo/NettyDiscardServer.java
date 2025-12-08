package com.marks.netty_demo;

import com.marks.nio_common.util.Logger;
import com.marks.nio_common.util.NioDemoConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: NettyDiscardServer </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/8 16:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class NettyDiscardServer {
    private final int PORT;
    private ServerBootstrap serverBootstrap = new ServerBootstrap();
    public NettyDiscardServer(int port) {
        this.PORT = port;
    }
    public void runServer() {
        // 创建反应器轮询组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 设置反应器轮询组
            serverBootstrap.group(bossGroup, workerGroup);
            // 设置 NIO类型 的通道
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 设置监听端口
            serverBootstrap.localAddress(PORT);
            // 设置通道参数
            serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);

            // 装配子通道流水线
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                // 有链接到达时会创建一个新的通道
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new NettyDiscardHandler());
                }
            });

            // 开始绑定服务
            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
            Logger.info("服务启动成功, 监听端口:" + channelFuture.channel().localAddress());
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new NettyDiscardServer(NioDemoConfig.SOCKET_SERVER_PORT).runServer();
    }
}
