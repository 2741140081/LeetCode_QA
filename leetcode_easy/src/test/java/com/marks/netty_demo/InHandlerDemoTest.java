package com.marks.netty_demo;

import com.marks.netty.netty_demo.InHandlerDemo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: InHandlerDemoTest </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/9 15:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class InHandlerDemoTest {
    @Test
    void testInHandlerLifeCycle() {
        final InHandlerDemo inHandlerDemo = new InHandlerDemo();

        // 初始化处理器
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(inHandlerDemo);
            }
        };
        // 创建嵌入式通道
        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        // 模拟入站, 向嵌入式通道写入数据
        channel.writeInbound(buf);
        channel.flush();
        // 再次写入
        buf.writeInt(2);
        channel.writeInbound(buf);
        channel.flush();
        // 关闭通道
        channel.close();
    }

}