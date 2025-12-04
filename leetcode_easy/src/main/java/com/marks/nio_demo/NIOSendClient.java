package com.marks.nio_demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: NIOSendClient </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/4 15:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class NIOSendClient {
    private Charset charset = StandardCharsets.UTF_8;

    public static void main(String[] args) {
        NIOSendClient client = new NIOSendClient();
        client.sendFile();
    }

    private void sendFile() {
        String sourceFile = NIOFileConfig.FILE_RESOURCE_SRC_PATH;
        String destFileName = "SendTest.txt";
        System.out.println("sourceFile:" + sourceFile);
        File file = new File(sourceFile);
        if (!file.exists()) {
            System.out.println("文件不存在");
            return;
        }
        try {
            FileChannel channel = new FileInputStream(file).getChannel();
            SocketChannel socketChannel = SocketChannel.open();
            // 设置 Socket选项
            socketChannel.socket().setSendBufferSize(64 * 1024); // 增加发送缓冲区大小
            socketChannel.socket().setSoTimeout(60000); // 超时设置
            socketChannel.socket().setKeepAlive(true); // 启用keep-alive

            socketChannel.connect(new InetSocketAddress("127.0.0.1", 18899));
            System.out.println("Client 成功连接服务端");
            while (!socketChannel.finishConnect()) {
                System.out.println("正在连接服务端...");
                Thread.sleep(1000);
            }
            // 发送文件 start

            // 发送文件名称和长度
            ByteBuffer buffer = sendFileNameAndLength(destFileName, file, socketChannel);

            // 发送文件内容
            int count = sendFileContent(channel, socketChannel, buffer, file);
            if (count == -1) {
                System.out.println("数据发送完毕");
                channel.close();
                socketChannel.shutdownOutput();
                socketChannel.close();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int sendFileContent(FileChannel channel, SocketChannel socketChannel, ByteBuffer buffer, File file) throws IOException, InterruptedException {
        System.out.println("开始发送文件内容...");
        int count = 0;
        long progress = 0;
        long totalBytes = file.length();
        // 检查连接状态
        if (!socketChannel.isOpen() || !socketChannel.isConnected()) {
            throw new IOException("Socket connection is not available");
        }
        while ((count = channel.read(buffer)) > 0) {
            buffer.flip();
            // 检查连接是否仍然有效
            if (!socketChannel.isOpen() || !socketChannel.isConnected()) {
                throw new IOException("Connection lost during file transfer");
            }
            while (buffer.hasRemaining()) {
                int write = socketChannel.write(buffer);
                progress += write;
                if (write == 0) {
                    Thread.sleep(100);
                }
            }
            buffer.clear();
            System.out.println("发送进度:" + progress * 100 / totalBytes + "%");
        }
        return count;
    }

    private ByteBuffer sendFileNameAndLength(String destFile, File file, SocketChannel socketChannel) throws IOException {
        ByteBuffer fileNameBuffer = charset.encode(destFile);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int fileNameLength = fileNameBuffer.limit();
        buffer.putInt(fileNameLength);
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
        System.out.println("发送文件名长度:" + fileNameLength);
        socketChannel.write(fileNameBuffer);
        System.out.println("发送文件名:" + destFile);
        buffer.putLong(file.length());
        buffer.flip();
        socketChannel.write(buffer);
        System.out.println("发送文件长度:" + file.length());
        buffer.clear();
        return buffer;
    }

}
