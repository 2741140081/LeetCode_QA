package com.marks.tools.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoWriter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/12 15:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class OpenCVVideoCreate {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private String imagePathPrefixName = "D:\\images\\opencv\\videos\\output\\output_";

    private int index = 0;

    private String imageSuffixName = ".jpg";

    private int imagesCount = 97; // 0 ~ 96

    public void createVideoByImage() {
        // 获取第一张图片大小
        String firstImage = imagePathPrefixName + index + imageSuffixName;

        String outputVideoPath = "D:\\images\\opencv\\videos\\result\\outputVideo_"+ LocalDateTime.now().format(formatter) + ".avi";
        int frameRate = 24; // 帧率

        // 读取图片, 为Mat对象
        Mat firstImageMat = Imgcodecs.imread(firstImage);
        int width = firstImageMat.cols(), height = firstImageMat.rows();

        // 我想看看 width(), height(), cols(), rows() 有什么区别
        System.out.printf("图片大小如下, width = %d, height = %d, cols = %d, rows = %d%n",
                firstImageMat.width(), firstImageMat.height(), firstImageMat.cols(), firstImageMat.rows());

        // 初始化 VideoWriter
        VideoWriter videoWriter = new VideoWriter(outputVideoPath,
                VideoWriter.fourcc('M', 'J', 'P', 'G'),
                frameRate,
                new Size(width, height)
        );

        // 写入所有图片
        for (int i = 0; i < imagesCount; i++) {
            String imgPath = imagePathPrefixName + index + imageSuffixName;
            Mat imgMat = Imgcodecs.imread(imgPath);
            videoWriter.write(imgMat);
            index++;
        }

        videoWriter.release();
    }
}
