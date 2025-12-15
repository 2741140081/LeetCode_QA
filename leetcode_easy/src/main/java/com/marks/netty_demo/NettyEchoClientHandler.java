package com.marks.netty_demo;

import com.marks.nio_common.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: NettyEchoClientHandler </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/15 16:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@ChannelHandler.Sharable
public class NettyEchoClientHandler extends ChannelInboundHandlerAdapter {
    public static final NettyEchoClientHandler INSTANCE = new NettyEchoClientHandler();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
//        Logger.info("msg type" + (byteBuf.hasArray() ? "堆内存" : "直接内存"));
        int len = byteBuf.readableBytes();
        byte[] arr = new byte[len];
        byteBuf.getBytes(0, arr);
        Logger.info("接收到数据：" + new String(arr, StandardCharsets.UTF_8));
        // 方法1: 直接释放msg
//        byteBuf.release();
        // 方法2: 调用父类的方法, 向后继续传递
        super.channelRead(ctx, msg);
    }
}
