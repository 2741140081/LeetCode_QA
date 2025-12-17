package com.marks.netty.netty_demo;

import com.marks.netty.nio_common.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: NettyDiscardHandler </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/8 16:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class NettyDiscardHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        try {
            // 丢弃接收到的数据
            Logger.info("丢弃接收到的数据, 数据如下:");
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
            }
            System.out.println();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
