package com.marks.netty.nio_demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: NIODiscardServer </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/3 16:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class NIODiscardServer {
    public static void main(String[] args) {
        try {
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void startServer() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 非阻塞
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(18899));
        System.out.println("服务启动成功");

        // 将通道注册为接收新连接 IO事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 轮询处理 IO事件
        while (selector.select() > 0) {
            // 迭代器
            Iterator<SelectionKey> selectKeys = selector.selectedKeys().iterator();
            while (selectKeys.hasNext()) {
                SelectionKey key = selectKeys.next();
                if (key.isAcceptable()) {
                    // 连接就绪IO事件
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    // 可读 IO事件
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    // 二进制缓存
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    while (socketChannel.read(buffer) > 0) {
                        // 翻转模式改为读模式
                        buffer.flip();
                        System.out.println(new String(buffer.array()));
                        buffer.clear();
                    }
                }
                // 移除处理过的IO事件
                selectKeys.remove();
            }
        }
        // 关闭连接
        serverSocketChannel.close();
    }
}
