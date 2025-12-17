package com.marks.netty.nio_demo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: EchoHandler </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/5 17:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class EchoHandler implements Runnable {
    final SocketChannel channel;
    final SelectionKey sk;
    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    static final int RECIEVING = 0, SENDING = 1;
    int state = RECIEVING;

    public EchoHandler(Selector selector, SocketChannel c) throws IOException {
        channel = c;
        // 设置为非阻塞
        c.configureBlocking(false);

        // 注册读事件
        sk = channel.register(selector, 0);

        // 绑定handler
        sk.attach(this);

        // 注册Read就绪事件
        sk.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }
    @Override
    public void run() {
        try {
            if (state == SENDING) {
                // 发送数据
                channel.write(byteBuffer);
                byteBuffer.clear();
                // 注册读事件
                sk.interestOps(SelectionKey.OP_READ);
                state = RECIEVING;
            } else if (state == RECIEVING) {
                // 接收数据
                int len = 0;
                while ((len = channel.read(byteBuffer)) > 0) {
                    // 打印接收的数据
                    String msg = new String(byteBuffer.array(), 0, len, StandardCharsets.UTF_8);
                    System.out.println("接收到数据:" + msg);
                }
                // 注册写事件
                byteBuffer.flip();
                sk.interestOps(SelectionKey.OP_WRITE);
                state = SENDING;
            }
            //处理结束了, 这里不能关闭select key，需要重复使用
            //sk.cancel()
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
