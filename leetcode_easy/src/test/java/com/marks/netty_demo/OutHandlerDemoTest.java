package com.marks.netty_demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: OutHandlerDemoTest </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/9 16:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class OutHandlerDemoTest {
    @Test
    void testOutHandlerLifeCycle() {
        final OutHandlerDemo outHandlerDemo = new OutHandlerDemo();

        // 初始化处理器
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<EmbeddedChannel>() {

            @Override
            protected void initChannel(EmbeddedChannel embeddedChannel) throws Exception {
                embeddedChannel.pipeline().addLast(outHandlerDemo);
            }
        };
        // 创建嵌入式通道
        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        // 模拟出站
        channel.writeOutbound(buf);

        buf.writeInt(2);
        channel.writeOutbound(buf);

        channel.close();
    }

}