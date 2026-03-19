package com.marks.kkPlatformGameAuto.handler.commonHandler;


import com.marks.kkPlatformGameAuto.config.ThiefGameConfig;
import com.marks.kkPlatformGameAuto.config.properties.GameAutoProperties;
import com.marks.kkPlatformGameAuto.service.ImageRecognitionService;
import com.marks.kkPlatformGameAuto.util.InputController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: NumberingHandler </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/19 15:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component
public class NumberingHandler {
    @Autowired
    private InputController input;

    @Autowired
    private ImageRecognitionService imageRecognitionService;

    @Autowired
    private GameAutoProperties gameAutoProperties;

    /**
     * 方式 1: 对已选中的目标进行编号
     * 前提：已经通过 Ctrl + 数字键选中了人物或建筑
     * @param number 编号数字 (1-9)
     * @return true 如果编号成功，否则 false
     */
    public boolean numbering(int number) {
        log.info("开始对已选中的目标进行编号：{}", number);

        // 参数校验
        if (number < 1 || number > 9) {
            log.warn("编号必须在 1-9 之间，当前编号：{}", number);
            return false;
        }

        try {
            // 按下 Ctrl + number 进行编组
            input.selectUnit(number);
            log.info("编号完成：Ctrl+{}", number);
            return true;
        } catch (Exception e) {
            log.error("编号失败：{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 方式 2: 查找目标并对其进行编号
     * 流程：通过图片识别找到目标 -> 点击选中 -> 调用方式 1 进行编号
     * @param imagePath 目标图片完整路径 (已包含完整目录和扩展名)
     * @param number 编号数字 (1-9)
     * @return true 如果编号成功，否则 false
     */
    public boolean numbering(String imagePath, int number) {
        log.info("开始查找并编号：图片路径={}, 编号={}", imagePath, number);

        try {
            // 1. imagePath 已是完整路径，直接使用, 查找图片并点击
            int timeout = gameAutoProperties.getDefaultTimeout(); // 5 秒超时
            boolean found = imageRecognitionService.findAndClickImage(imagePath, timeout);

            if (!found) {
                log.warn("未找到目标图片：{}", imagePath);
                return false;
            }
            log.info("找到并点击目标成功，图片路径：{}", imagePath);
            // 4. 调用方式 1 进行编号
            return numbering(number);

        } catch (Exception e) {
            log.error("查找并编号失败：{}", e.getMessage(), e);
            return false;
        }
    }

}
