package com.marks.kkPlatformGameAuto.service.impl;

import com.marks.kkPlatformGameAuto.config.properties.GameAutoProperties;
import com.marks.kkPlatformGameAuto.service.HeroOperationService;
import com.marks.kkPlatformGameAuto.service.ImageRecognitionService;
import com.marks.kkPlatformGameAuto.util.InputController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: HeroOperationServiceImpl </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/19 17:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Service
public class HeroOperationServiceImpl implements HeroOperationService {
    @Autowired
    private InputController input;

    @Autowired
    private ImageRecognitionService imageRecognitionService;

    @Autowired
    private GameAutoProperties gameAutoProperties;


    @Override
    public boolean useNonTargetedSkillByImage(String skillImagePath) {
        return useNonTargetedSkillByImage(skillImagePath, 1, 0);
    }

    @Override
    public boolean useNonTargetedSkillByImage(String skillImagePath, int executeCount, int intervalMs) {
        // 找到技能中心点, 防止执行多次时, 需要重复查找
        Point skillCenter = imageRecognitionService.findImageCenter(skillImagePath, gameAutoProperties.getDefaultTimeout(), gameAutoProperties.getDefaultWaitTime());
        // 判断技能是否找到
        if (skillCenter == null) {
            return false;
        }
        for (int i = 0; i < executeCount; i++) {
            // 点击技能
            input.click(skillCenter.x, skillCenter.y);
            // 技能释放CD
            input.delay(intervalMs);
        }
        return true;
    }

    @Override
    public boolean useTargetedSkillByImage(String skillImagePath, Point targetPoint) {
        log.info("释放指向性技能（图片识别），技能图片：{}", skillImagePath);
        if (!clickSkillIcon(skillImagePath)) {
            return false;
        }
        releaseSkillAtPoint(targetPoint);
        return true;
    }

    @Override
    public boolean useTargetedSkillByKey(int keyCode, Point targetPoint) {
        log.info("释放指向性技能（按键触发），技能按键：{}", keyCode);
        input.pressKey(keyCode);
        releaseSkillAtPoint(targetPoint);
        return true;
    }

    @Override
    public boolean useNonTargetedSkillByKey(int keyCode) {
        log.info("释放非指向性技能（按键触发），技能按键：{}", keyCode);
        input.pressKey(keyCode);
        return true;
    }

    @Override
    public boolean transferItems(List<String> itemImagePath, Point targetPoint) {
        log.info("移交物品到目标点");
        // 获取配置文件中的默认超时时间
        int timeout = gameAutoProperties.getDefaultTimeout();
        int delayTime = gameAutoProperties.getInterval();
        for (String itemPath : itemImagePath) {
            // 找到物品坐标点
            Point imageCenter = imageRecognitionService.findImageCenter(itemPath, timeout, delayTime);
            // 判断物品是否找到
            if (imageCenter == null) {
                log.warn("未找到物品：{}", itemPath);
                return false;
            }
            // 移动到坐标点并且右键点击
            input.moveToAndRightClick(imageCenter.x, imageCenter.y);
            input.delay(delayTime);
            // 移动到目标点
            input.moveToAndLeftClick(targetPoint.x, targetPoint.y);
        }
        return true;
    }

    @Override
    public int transferAllSameItems(List<String> itemImagePath, Point targetPoint) {
        return 0;
    }

    @Override
    public int transferAllItems(Point targetPoint, String itemLogoImagePath) {
        return 0;
    }

    /**
     * 公共方法：点击技能图标
     * @param skillImagePath 技能图片路径
     * @return true 如果找到并点击成功，否则 false
     */
    private boolean clickSkillIcon(String skillImagePath) {
        int timeout = gameAutoProperties.getDefaultTimeout();
        boolean found = imageRecognitionService.findAndClickImage(skillImagePath, timeout);

        if (!found) {
            log.warn("未找到技能图标：{}", skillImagePath);
            return false;
        }
        return true;
    }

    /**
     * 公共方法：在指定点释放技能
     * @param targetPoint 目标点坐标
     */
    private void releaseSkillAtPoint(Point targetPoint) {
        // 获取技能释放延迟
        int skillDelay = gameAutoProperties.getOperation().getSkillDelay();
        input.delay(skillDelay);
        input.moveToAndLeftClick(targetPoint.x, targetPoint.y);
    }
}
