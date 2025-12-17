package com.marks.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: Byte2IntegerDecoderTest </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/16 15:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class Byte2IntegerDecoderTest {

    @Test
    void testByte2IntegerDecoder() {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<>() {
            @Override
            protected void initChannel(EmbeddedChannel channel) throws Exception {
//                channel.pipeline().addLast(new Byte2IntegerDecoder());
                channel.pipeline().addLast(new Byte2IntegerReplayDecoder());
                channel.pipeline().addLast(new IntegerProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        for (int i = 0; i < 100; i++) {
            ByteBuf buffer = Unpooled.buffer();
            buffer.writeInt(i);
            channel.writeInbound(buffer);
        }
    }
}