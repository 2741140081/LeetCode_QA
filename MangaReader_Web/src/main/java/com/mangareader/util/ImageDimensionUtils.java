package com.mangareader.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

/**
 * 图片尺寸读取工具类
 * 使用 ImageReader 流式读取头部信息，避免加载整张图片到内存
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
public class ImageDimensionUtils {

    private ImageDimensionUtils() {
    }

    /**
     * 读取图片宽高
     *
     * @param imageFile 图片文件
     * @return int[]{width, height}，读取失败返回 null
     */
    public static int[] readDimensions(File imageFile) {
        if (imageFile == null || !imageFile.exists()) {
            return null;
        }

        // 方式1: 使用 ImageReader 流式读取（只读 header，不加载整张图片）
        try (ImageInputStream iis = ImageIO.createImageInputStream(imageFile)) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                try {
                    reader.setInput(iis, true, true);
                    int width = reader.getWidth(0);
                    int height = reader.getHeight(0);
                    return new int[]{width, height};
                } finally {
                    reader.dispose();
                }
            }
        } catch (Exception e) {
            log.debug("ImageReader 读取失败: {}, 原因: {}", imageFile.getAbsolutePath(), e.getMessage());
        }

        // 方式2: WebP 格式回退 - 通过文件头魔数检测并解析 RIFF 容器
        if (isWebPFile(imageFile)) {
            return readWebPDimensions(imageFile);
        }

        return null;
    }

    /**
     * 检测文件是否为 WebP 格式（RIFF....WEBP 魔数）
     */
    private static boolean isWebPFile(File file) {
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            if (raf.length() < 12) return false;
            byte[] header = new byte[12];
            raf.readFully(header);
            // RIFF????WEBP
            return header[0] == 'R' && header[1] == 'I' && header[2] == 'F' && header[3] == 'F'
                    && header[8] == 'W' && header[9] == 'E' && header[10] == 'B' && header[11] == 'P';
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 从 WebP 文件中读取宽高
     * WebP 文件结构: RIFF[4] + size[4] + WEBP[4] + chunk
     * VP8 有损: chunk 以 "VP8 " 开头，宽高在固定偏移
     * VP8L 无损: chunk 以 "VP8L" 开头，宽高编码在 4 字节中
     * VP8X 扩展: chunk 以 "VP8X" 开头，宽高在 12 字节中
     */
    private static int[] readWebPDimensions(File file) {
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            // 跳过 RIFF header (12 bytes: RIFF + size + WEBP)
            raf.seek(12);
            byte[] chunkId = new byte[4];
            raf.readFully(chunkId);

            if (chunkId[0] == 'V' && chunkId[1] == 'P' && chunkId[2] == '8' && chunkId[3] == ' ') {
                // VP8 有损格式
                // 跳过 chunk size (4 bytes) + frame tag (3 bytes) + start code (3 bytes: 0x9D 0x01 0x2A)
                raf.seek(12 + 4 + 4 + 3); // chunkId(4) + chunkSize(4) + frameTag(3)
                byte[] startCode = new byte[3];
                raf.readFully(startCode);
                if (startCode[0] == (byte) 0x9D && startCode[1] == (byte) 0x01 && startCode[2] == (byte) 0x2A) {
                    int width = readLittleEndian16(raf) & 0x3FFF;
                    int height = readLittleEndian16(raf) & 0x3FFF;
                    return new int[]{width, height};
                }
            } else if (chunkId[0] == 'V' && chunkId[1] == 'P' && chunkId[2] == '8' && chunkId[3] == 'L') {
                // VP8L 无损格式
                raf.seek(12 + 4 + 4); // 跳过 chunkId(4) + chunkSize(4) + signature(1 byte: 0x2F)
                raf.read(); // signature byte 0x2F
                int b0 = raf.read() & 0xFF;
                int b1 = raf.read() & 0xFF;
                int b2 = raf.read() & 0xFF;
                int b3 = raf.read() & 0xFF;
                // 14 bits width, 14 bits height packed in 4 bytes
                int width = ((b0 | (b1 << 8)) & 0x3FFF) + 1;
                int height = (((b1 >> 6) | (b2 << 2) | (b3 << 10)) & 0x3FFF) + 1;
                return new int[]{width, height};
            } else if (chunkId[0] == 'V' && chunkId[1] == 'P' && chunkId[2] == '8' && chunkId[3] == 'X') {
                // VP8X 扩展格式
                raf.seek(12 + 4 + 4 + 4); // 跳过 chunkId(4) + chunkSize(4) + flags(4)
                int b0 = raf.read() & 0xFF;
                int b1 = raf.read() & 0xFF;
                int b2 = raf.read() & 0xFF;
                int width = (b0 | (b1 << 8) | (b2 << 16)) + 1;
                int b3 = raf.read() & 0xFF;
                int b4 = raf.read() & 0xFF;
                int b5 = raf.read() & 0xFF;
                int height = (b3 | (b4 << 8) | (b5 << 16)) + 1;
                return new int[]{width, height};
            }
        } catch (Exception e) {
            log.debug("WebP 尺寸解析失败: {}, 原因: {}", file.getAbsolutePath(), e.getMessage());
        }
        return null;
    }

    private static int readLittleEndian16(RandomAccessFile raf) throws IOException {
        int b0 = raf.read() & 0xFF;
        int b1 = raf.read() & 0xFF;
        return b0 | (b1 << 8);
    }
}
