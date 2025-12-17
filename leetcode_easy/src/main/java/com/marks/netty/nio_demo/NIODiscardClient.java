package com.marks.netty.nio_demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: NIODiscardClient </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/3 17:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class NIODiscardClient {
    public static void main(String[] args) {
        try {
            startClient();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void startClient() throws IOException, InterruptedException {
        // 构建连接
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 18899);

        // 获取通道
        SocketChannel socketChannel = SocketChannel.open(address);
        // 设置为非阻塞
        socketChannel.configureBlocking(false);

        // 自旋等待连接完成
        while (!socketChannel.finishConnect()) {
            System.out.println("正在连接...");
            Thread.sleep(1000);
        }
        System.out.println("连接成功");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("hello world".getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        socketChannel.shutdownOutput();
        socketChannel.close();
        System.out.println("数据发送完毕");
    }
}
