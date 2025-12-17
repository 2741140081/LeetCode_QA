package com.marks.netty.netty_demo;

import com.marks.netty.nio_common.util.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: InHandlerDemo </p>
 * <p>描述: 入站处理器 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/9 15:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class InHandlerDemo extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Logger.info("handlerAdded() 被调用");
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Logger.info("handlerRemoved() 被调用");
        super.handlerRemoved(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Logger.info("channelRegistered() 被调用");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        Logger.info("channelUnregistered() 被调用");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Logger.info("channelActive() 被调用");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Logger.info("channelInactive() 被调用");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Logger.info("channelRead() 被调用");
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        Logger.info("channelReadComplete() 被调用");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        Logger.info("userEventTriggered() 被调用");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        Logger.info("channelWritabilityChanged() 被调用");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Logger.info("exceptionCaught() 被调用");
        super.exceptionCaught(ctx, cause);
    }
}
