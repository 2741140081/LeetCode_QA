package com.marks.kkPlatformGameAuto.service.impl;

import com.marks.kkPlatformGameAuto.config.PrepareRoomConfig;
import com.marks.kkPlatformGameAuto.config.properties.GameAutoProperties;
import com.marks.kkPlatformGameAuto.service.ImageRecognitionService;
import com.marks.kkPlatformGameAuto.service.PrepareRoomService;
import com.marks.kkPlatformGameAuto.service.WindowSwitcherService;
import com.marks.kkPlatformGameAuto.util.ImagePathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: PrepareRoomServiceImpl </p>
 * <p>描述: [准备房间服务实现类] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/18 15:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Service
public class PrepareRoomServiceImpl implements PrepareRoomService {
    @Autowired
    private WindowSwitcherService windowSwitcherService;

    @Autowired
    private PrepareRoomConfig prepareRoomConfig;

    @Autowired
    private ImagePathUtils imagePathUtils;

    @Autowired
    private ImageRecognitionService imageRecognitionService;

    @Autowired
    private GameAutoProperties gameAutoProperties;

    @Override
    public boolean clickStartGameButton() {
        // 切换到准备房间界面
        if (!windowSwitcherService.switchToPrepareRoom()) {
            return false;
        }
        // 获取准备房间开始游戏按钮名称
        String startGameButtonImage = prepareRoomConfig.getStartGameButtonImage();
        // 获取图片目录
        String imageDir = prepareRoomConfig.getImageDir();
        String imagePath = imagePathUtils.buildImagePathWithExtension(imageDir, startGameButtonImage);
        // 从配置中获取查找图片超时时间
        int timeout = gameAutoProperties.getDefaultTimeout();
        // 识别图片并点击
        if (!imageRecognitionService.findAndClickImage(imagePath, timeout)) {
            return false;
        }
        log.info("点击开始游戏按钮成功");
        return true;
    }
}
