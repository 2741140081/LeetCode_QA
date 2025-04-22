package com.marks.tools;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/22 11:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class TemplateMatcher {
    static {
        // 加载OpenCV本地库（注意路径需根据实际情况调整）
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        try {
            // 1. 加载图像
            Mat template = Imgcodecs.imread("D:\\images\\Pulse_Sign_1.png");  // 71x71模板图像
            Mat source = Imgcodecs.imread("D:\\images\\All.png");    // 3840x2160源图像

            // 检查图像是否加载成功
            if(template.empty() || source.empty()) {
                throw new Exception("图像加载失败，请检查路径");
            }

            // 2. 转换为灰度图（提升匹配速度）
            Mat grayTemplate = new Mat();
            Mat graySource = new Mat();
            Imgproc.cvtColor(template, grayTemplate, Imgproc.COLOR_BGR2GRAY);
            Imgproc.cvtColor(source, graySource, Imgproc.COLOR_BGR2GRAY);


            // 多尺度匹配参数设置
            double[] scales = {0.8, 0.9, 1.0, 1.1, 1.2};  // 缩放比例范围:ml-citation{ref="2" data="citationList"}
            double maxVal = 0;
            Point maxLoc = new Point();
            Mat result = new Mat();

            // 多尺度循环匹配
            double currentScale = 1.0;
            Point matchLoc = null;
            for (double scale : scales) {
                // 缩放模板图像
                Mat resizedTemplate = new Mat();
                Imgproc.resize(template, resizedTemplate,
                        new Size(template.cols()*scale, template.rows()*scale));

                // 执行模板匹配:ml-citation{ref="1" data="citationList"}
                Imgproc.matchTemplate(source, resizedTemplate, result,
                        Imgproc.TM_CCOEFF_NORMED);

                // 4. 获取最佳匹配位置
                Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
                if (mmr.maxVal > maxVal) {
                    maxVal = mmr.maxVal;
                    maxLoc = mmr.maxLoc;
                    // 记录当前最佳缩放比例
                    currentScale = scale;
                    matchLoc = mmr.maxLoc;
                }
            }
            
            // 5. 绘制绿色矩形框
            if (maxVal >= 0.8) {
                // 计算实际匹配区域尺寸
                Size matchedSize = new Size(
                        template.cols() * currentScale,
                        template.rows() * currentScale
                );

                // 绘制绿色矩形框（线宽3px）:ml-citation{ref="1" data="citationList"}
                Imgproc.rectangle(
                        source,
                        maxLoc,
                        new Point(maxLoc.x + matchedSize.width, maxLoc.y + matchedSize.height),
                        new Scalar(0, 255, 0),
                        3
                );

                // 6. 保存结果
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
                String outputPath = "D:\\images\\Image"+ LocalDateTime.now().format(formatter) + ".png";
                boolean success = Imgcodecs.imwrite(outputPath, source);
                if (success) {
                    System.out.println("处理成功！结果已保存至: " + outputPath);
                    System.out.println("匹配位置: X=" + (int)matchLoc.x + " Y=" + (int)matchLoc.y);
                    System.out.println("匹配置信度: " + maxVal);
                }
            } else {
                System.out.println("未找到置信度≥0.8的匹配区域");
            }

        } catch (Exception e) {
            System.err.println("处理出错: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
