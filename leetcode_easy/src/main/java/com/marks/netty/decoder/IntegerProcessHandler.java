package com.marks.netty.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: IntegerProcessHandler </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/16 15:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class IntegerProcessHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Integer num = (Integer) msg;
        System.out.println("IntegerProcessHandler: " + num);
    }
}
