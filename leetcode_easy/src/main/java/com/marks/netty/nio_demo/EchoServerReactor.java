package com.marks.netty.nio_demo;

import com.marks.netty.nio_common.util.NioDemoConfig;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: EchoServerReactor </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/5 15:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class EchoServerReactor implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public EchoServerReactor() throws IOException {
        // 获取选择器
        selector = Selector.open();
        // 获取通道
        serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(NioDemoConfig.SOCKET_SERVER_IP,
                NioDemoConfig.SOCKET_SERVER_PORT);

        serverSocketChannel.configureBlocking(false);
        // 分步处理, 第一步, 接收accept 事件
        SelectionKey sk = serverSocketChannel.register(selector, 0, new AcceptorHandler());
        serverSocketChannel.socket().bind(address);
        System.out.println("服务器启动成功, 监听:" + address);
        // 注册就绪事件
        sk.interestOps(SelectionKey.OP_ACCEPT);
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                if (null == selectionKeys || selectionKeys.isEmpty()) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    dispatch(key);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void dispatch(SelectionKey key) {
        Runnable handler = (Runnable) key.attachment();
        if (null != handler) {
            handler.run();
        }
    }

    private class AcceptorHandler implements Runnable {
        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                System.out.println("接收到连接");
                if (socketChannel != null) {
                    new EchoHandler(selector, socketChannel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            new Thread(new EchoServerReactor()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
