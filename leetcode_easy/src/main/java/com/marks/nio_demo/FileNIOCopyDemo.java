package com.marks.nio_demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: FileNIOCopyDemo </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/2 17:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class FileNIOCopyDemo {

    public static void main(String[] args) {
        nioCopyFile();
    }

    private static void nioCopyFile() {
        String sourceFile = NIOFileConfig.FILE_RESOURCE_SRC_PATH;
        String destFile = NIOFileConfig.FILE_RESOURCE_DEST_PATH;

        File source = new File(sourceFile);
        File dest = new File(destFile);
        if (!source.exists()) {
            return;
        }
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            // 使用FileChannel 进行复制文件
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            // 新建一个ByteBuffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (sourceChannel.read(buffer) != -1) {
                // 将buffer的写模式改成读模式
                buffer.flip();
                destChannel.write(buffer);
                // 清空buffer, 由读模式改成写模式
                buffer.clear();
            }
            // 强制刷新到磁盘
            destChannel.force(true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                if (sourceChannel != null) sourceChannel.close();  // 添加空指针检查
                if (destChannel != null) destChannel.close();      // 添加空指针检查
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

}
