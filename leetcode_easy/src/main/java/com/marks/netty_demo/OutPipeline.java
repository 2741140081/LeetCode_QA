package com.marks.netty_demo;

import com.marks.nio_common.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: OutPipeline </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/9 16:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class OutPipeline {
    // 输出处理器, 构建3个简单的静态内部类, 继承自 ChannelOutboundHandlerAdapter
    static class OutHandler1 extends ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            Logger.info("OutHandler1.write() 被调用");
            super.write(ctx, msg, promise);
        }
    }
    static class OutHandler2 extends ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            Logger.info("OutHandler2.write() 被调用");
            super.write(ctx, msg, promise);
        }
    }
    static class OutHandler3 extends ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            Logger.info("OutHandler3.write() 被调用");
            super.write(ctx, msg, promise);
        }
    }

    public static void main(String[] args) {
        final OutHandler1 outHandler1 = new OutHandler1();
        final OutHandler2 outHandler2 = new OutHandler2();
        final OutHandler3 outHandler3 = new OutHandler3();
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(outHandler1);
                ch.pipeline().addLast(outHandler2);
                ch.pipeline().addLast(outHandler3);
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        channel.writeOutbound(buf);
    }
}
