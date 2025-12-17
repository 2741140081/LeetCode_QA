package com.marks.netty.nio_demo;

import com.marks.netty.nio_common.util.NioDemoConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: NIOReceiveServer </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/4 15:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class NIOReceiveServer {
    public static void main(String[] args) throws IOException {
        NIOReceiveServer server = new NIOReceiveServer();
        server.startServer();
    }
    private Charset charset = StandardCharsets.UTF_8;
    private final String RECEIVE_PATH = NioDemoConfig.SOCKET_RECEIVE_PATH;

    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    private Map<SelectableChannel, Client> clientMap = new HashMap<>();

    // 静态内部类
    static class Client {
        private String fileName;
        private long fileLength;
        private long startTime;
        private InetSocketAddress inetAddress;
        private FileChannel outChannel;
        private long recevedLength;

        public Client() {
        }

        public boolean isFinished() {
            return recevedLength >= fileLength;
        }
    }

    private void startServer() throws IOException {
        // 获取选择器
        Selector selector = Selector.open();
        // 获取通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverChannel.socket();
        // 设置为非阻塞
        serverChannel.configureBlocking(false);
        // 绑定端口
        serverSocket.bind(new InetSocketAddress(18899));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务启动成功");
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel =(ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    socketChannel.configureBlocking(false);
                    SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
                    Client client = new Client();
                    client.inetAddress =(InetSocketAddress) socketChannel.getRemoteAddress();
                    clientMap.put(socketChannel, client);
                    System.out.println("客户端连接成功:" + client.inetAddress.getHostName());
                } else if (key.isReadable()) {
                    processData(key);
                }
            }
            iterator.remove();
        }
    }

    private void processData(SelectionKey key) {
        Client client = clientMap.get(key.channel());
        SocketChannel socketChannel = (SocketChannel) key.channel();
        try {
            buffer.clear();
            while (socketChannel.read(buffer) > 0) {
                buffer.flip();
                if (null == client.fileName) {
                    int fileNameLength = buffer.getInt();
                    byte[] fileNameBytes = new byte[fileNameLength];
                    buffer.get(fileNameBytes);
                    String fileName = new String(fileNameBytes, charset);
                    File file = new File(RECEIVE_PATH + fileName);
                    client.fileName = fileName;
                    FileChannel fileChannel = new FileOutputStream(file).getChannel();
                    client.outChannel = fileChannel;

                    // 文件长度
                    client.fileLength = buffer.getLong();
                    client.startTime = System.currentTimeMillis();
                    System.out.println("接收文件:" + client.fileName + " 文件大小:" + client.fileLength);
                    client.recevedLength += buffer.capacity();
                    if (buffer.capacity() > 0) {
                        client.outChannel.write(buffer);
                    }
                    if (client.isFinished()) {
                        System.out.println("接收文件:" + client.fileName + " 接收完成");
                        finished(key, client);
                    }
                    buffer.clear();
                } else {
                    // 文件内容
                    client.recevedLength += buffer.capacity();
                    System.out.println("接收文件:" + client.fileName + " 已接收:" + client.recevedLength);
                    if (buffer.capacity() > 0) {
                        client.outChannel.write(buffer);
                    }
                    if (client.isFinished()) {
                        finished(key, client);
                    }
                    buffer.clear();
                }
            }
            key.cancel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void finished(SelectionKey key, Client client) throws IOException {
        client.outChannel.close();
        key.cancel();
        long endTime = System.currentTimeMillis();
        System.out.println("接收文件:" + client.fileName + " 接收完成,耗时:" + (endTime - client.startTime) + "ms");
    }

}
