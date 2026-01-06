package com.marks.netty.protocol;

import com.marks.netty.nio_common.util.Logger;
import com.marks.netty.nio_common.util.NioDemoConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ProtoBufSendClient </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/6 15:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ProtoBufSendClient {
    private static String content = "send some thing by ww";
    private int port;
    private String serverIp;
    private Bootstrap bootstrap = new Bootstrap();

    public ProtoBufSendClient(String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = port;
    }

    public void runClient() {
        // 创建反应器轮询组
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 设置反应器轮询组
            bootstrap.group(workerGroup);
            // 设置NIO 类型
            bootstrap.channel(NioSocketChannel.class);
            // 监听端口
            bootstrap.remoteAddress(serverIp, port);
            // 设置通道参数
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            // 装配通道流水线
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                    channel.pipeline().addLast(new ProtobufEncoder()); // 添加protobuf编码器
                }
            });

            // 连接
            bootstrap.connect().sync(); // 阻塞方法，等待连接成功
            System.out.println("client start...");
            // 发送数据
            Channel channel = bootstrap.connect().channel();

            for (int i = 0; i < 1000; i++) {
                MsgProto.Msg msg = buildMsg(i, i + " -> " + content);
                channel.writeAndFlush(msg).addListener(future -> {
                    if (!future.isSuccess()) {
                        Logger.error("发送失败: " + future.cause());
                    }
                });
                Logger.info("send msg: " + i);

                // 控制发送频率
                if (i % 50 == 0 && i > 0) { // 每50条消息后暂停
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
            channel.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭
            workerGroup.shutdownGracefully();
        }
    }

    private MsgProto.Msg buildMsg(int id, String content) {
        MsgProto.Msg.Builder builder = MsgProto.Msg.newBuilder();
        builder.setId(id);
        builder.setContent(content);
        return builder.build();
    }

    public static void main(String[] args) {
        int port = NioDemoConfig.SOCKET_SERVER_PORT;
        String serverIp = NioDemoConfig.SOCKET_SERVER_IP;
        new ProtoBufSendClient(serverIp, port).runClient();
    }
}
