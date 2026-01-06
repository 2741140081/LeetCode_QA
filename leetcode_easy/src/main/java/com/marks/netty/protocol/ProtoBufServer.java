package com.marks.netty.protocol;

import com.marks.netty.nio_common.util.NioDemoConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ProtoBufServer </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/5 15:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ProtoBufServer {
    private final int port;
    private ServerBootstrap serverBootstrap = new ServerBootstrap();
    public ProtoBufServer(int port) {
        this.port = port;
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
            serverBootstrap.localAddress(port);
            // 设置通道参数
            serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            // 装配子通道流水线
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    // pipeline管理子通道channel中的Handler
                    // 向子channel流水线添加3个handler处理器

                    // protobufDecoder仅仅负责编码，并不支持读半包，所以在之前，一定要有读半包的处理器。
                    // 有三种方式可以选择：
                    // 使用netty提供ProtobufVarint32FrameDecoder
                    // 继承netty提供的通用半包处理器 LengthFieldBasedFrameDecoder
                    // 继承ByteToMessageDecoder类，自己处理半包

                    // 半包的处理
                    channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                    // 添加ProtoBuf解码器
                    channel.pipeline().addLast(new ProtobufDecoder(MsgProto.Msg.getDefaultInstance()));
                    // 添加业务处理handler
                    channel.pipeline().addLast(new ProtoBufBussinessDecoder());
                }
            });
            // 绑定端口，开始接收进来的连接
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            System.out.println("server start...");

            // 等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭反应器轮询组
            bossLoopGroup.shutdownGracefully();
            workerLoopGroup.shutdownGracefully();
        }
    }

    static class ProtoBufBussinessDecoder extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            MsgProto.Msg msgProto = (MsgProto.Msg) msg;
            System.out.println("接收到消息：" + msgProto.getId() + ":" + msgProto.getContent());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("发生异常" + cause.getMessage());
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) {

        new ProtoBufServer(NioDemoConfig.SOCKET_SERVER_PORT).runServer();
    }
}
