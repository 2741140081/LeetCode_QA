package com.marks.netty.netty_demo;

import com.marks.netty.nio_common.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: InPipeline </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/9 16:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class InPipeline {
    // 构建3个简单的内部静态类, 继承自抽象类ChannelInboundHandlerAdapter
    static class InHandlerA extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Logger.info("InHandlerA.channelRead() 被调用");
            super.channelRead(ctx, msg);
        }
    }

    static class InHandlerB extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Logger.info("InHandlerB.channelRead() 被调用");
            // 如果不调用父类的channelRead方法, 则不会调用下一个handler的channelRead方法
            super.channelRead(ctx, msg);
        }
    }

    static class InHandlerC extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            Logger.info("InHandlerC.channelRead() 被调用");
            super.channelRead(ctx, msg);
        }
    }

    public static void main(String[] args) {
        final InHandlerA inHandlerA = new InHandlerA();
        final InHandlerB inHandlerB = new InHandlerB();
        final InHandlerC inHandlerC = new InHandlerC();
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(inHandlerA);
                ch.pipeline().addLast(inHandlerB);
                ch.pipeline().addLast(inHandlerC);
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        channel.writeInbound(buf);
    }
}
