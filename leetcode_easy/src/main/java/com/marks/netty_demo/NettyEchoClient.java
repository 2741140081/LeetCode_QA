package com.marks.netty_demo;

import com.marks.nio_common.util.Dateutil;
import com.marks.nio_common.util.NioDemoConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: NettyEchoClient </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/15 16:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class NettyEchoClient {
    private final int PORT;
    private final String HOST;
    private Bootstrap bootstrap = new Bootstrap();
    public NettyEchoClient(int port, String host) {
        this.PORT = port;
        this.HOST = host;
    }
    public void runClient() {
        // 创建反应器轮询组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 设置反应器轮询组
            bootstrap.group(workerGroup);
            // 设置 NIO类型 的通道
            bootstrap.channel(NioSocketChannel.class);
            // 监听端口
            bootstrap.remoteAddress(HOST, PORT);
            // 设置通道参数
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            // 装配子通道流水线
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    channel.pipeline().addLast(NettyEchoClientHandler.INSTANCE);
                }
            });
            // 连接服务器
            ChannelFuture connect = bootstrap.connect();
            connect.addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("连接服务器成功");
                } else {
                    System. out.println("连接服务器失败");
                }
            });
            // 等待, 直到连接成功
            connect.sync();
            Channel channel = connect.channel();
            // 发送数据
            Scanner in = new Scanner(System.in);
            System.out.println("请输入要发送的数据：");
            while (in.hasNext()) {
                String line = in.nextLine();
                byte[] bytes = (Dateutil.getNow() + ">>" + line).getBytes(StandardCharsets.UTF_8);
                // 发送ByteBuf
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes(bytes);
                channel.writeAndFlush(buffer);
                System.out.println("请输入要发送的数据：");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyEchoClient(NioDemoConfig.SOCKET_SERVER_PORT, NioDemoConfig.SOCKET_SERVER_IP).runClient();
    }

}
