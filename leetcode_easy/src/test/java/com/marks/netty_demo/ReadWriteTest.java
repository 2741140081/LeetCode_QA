package com.marks.netty_demo;

import com.marks.nio_common.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import org.junit.jupiter.api.Test;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ReadWriteTest </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/11 15:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class ReadWriteTest {
    @Test
    void testReadWrite() {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("分配ByteBuf(9, 100)", buf);
        buf.writeBytes(new byte[]{1, 2, 3, 4});
        print("写入数据后 [1, 2, 3, 4]", buf);
        Logger.info("===== get =======");
        getByteBuf(buf);
        print("getByteBuf()", buf);
        Logger.info("===== read =======");
        readByteBuf(buf);
        print("readByteBuf()", buf);
        Logger.info("===== release =======");
    }

    private void getByteBuf(ByteBuf buf) {
        for (int i = 0; i < buf.readableBytes(); i++) {
            Logger.info("getByteBuf()", buf.getByte(i));
        }
    }

    private void readByteBuf(ByteBuf buf) {
        while (buf.isReadable()) {
            Logger.info("getByteBuf()", buf.readByte());
        }
    }

    public void print(String action, ByteBuf b) {
        Logger.info("after ===========" + action + "============");
        Logger.info(b);
        Logger.info("1.0 isReadable(): " + b.isReadable());
        Logger.info("1.1 readerIndex(): " + b.readerIndex());
        Logger.info("1.2 readableBytes(): " + b.readableBytes());

        Logger.info("2.0 isWritable(): " + b.isWritable());
        Logger.info("2.1 writerIndex(): " + b.writerIndex());
        Logger.info("2.2 writableBytes(): " + b.writableBytes());

        Logger.info("3.0 capacity(): " + b.capacity());
        Logger.info("3.1 maxCapacity(): " + b.maxCapacity());
        Logger.info("3.2 maxWritableBytes(): " + b.maxWritableBytes());

        Logger.info("4.0 refCnt(): " + b.refCnt());
        Logger.info(ByteBufUtil.prettyHexDump(b));
    }
}
