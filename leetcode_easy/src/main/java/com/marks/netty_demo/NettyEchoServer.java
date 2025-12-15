package com.marks.netty_demo;

import com.marks.nio_common.util.NioDemoConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: NettyEchoServer </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/15 15:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class NettyEchoServer {
    private final int PORT;
    private ServerBootstrap serverBootstrap = new ServerBootstrap();

    public NettyEchoServer(int port) {
        this.PORT = port;
    }
    public void runServer() {
        NioEventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        try {
            // 设置反应器轮询组
            serverBootstrap.group(bossLoopGroup, workerLoopGroup);
            // 设置 NIO类型 的通道
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 监听端口
            serverBootstrap.localAddress(PORT);
            // 设置通道参数
            serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);

            // 装配子通道流水线
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(NettyEchoServerHandler.INSTANCE);
                }
            });
            // 绑定服务器，开始接受客户请求
            ChannelFuture sync = serverBootstrap.bind().sync();
            // 等待, 直到服务器关闭
            sync.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossLoopGroup.shutdownGracefully();
            workerLoopGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) {
        new NettyEchoServer(NioDemoConfig.SOCKET_SERVER_PORT).runServer();
    }
}
