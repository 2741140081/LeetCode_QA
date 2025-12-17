package com.marks.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: Byte2IntegerReplayDecoder </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/16 15:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class Byte2IntegerReplayDecoder extends ReplayingDecoder {

    /**
     * ReplayingDecoder 进行长度判断的原理:
     * 1. 内部定义一个二进制缓冲区类型 ReplayingDecoderByteBuf, 对ByteBuf 进行装饰
     * 2. 该装饰器的特点时: 在缓冲区真正读数据之前先进行长度判断, 如果长度合格, 就读取数据, 否则就抛出ReplayError
     * 3. ReplayingDecoder 捕获到ReplayError后, 会保留着数据, 等待下一次IO事件到来时在读取.
     * 4. ReplayingDecoder 更常见的应用场景是: 数据进行分包传输时.
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // ReplayingDecoder 类继承 ByteToMessageDecoder 类，
        // 作用是在读取ByteBuf时, 会自动检查缓冲区是否有足够的字节
        // 如果有, 则会正常读取, 如果没有, 则停止解码
        int i = byteBuf.readInt();
        list.add(i);
        System.out.println("decode ReplayingDecoder:" + i);
    }
}
