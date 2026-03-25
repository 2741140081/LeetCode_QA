package com.marks.kkPlatformGameAuto.service;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ClickerService </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/25 15:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface ClickerService {
    /**
     * 启动左键连点器
     */
    void startLeftClicker();

    /**
     * 停止左键连点器
     */
    void stopLeftClicker();

    /**
     * 检查连点器是否正在运行
     * @return true 如果正在运行
     */
    boolean isRunning();

    /**
     * 设置点击间隔
     * @param interval 间隔时间 (毫秒)
     */
    void setInterval(int interval);

    /**
     * 启动物品栏自动使用
     */
    void startItemBarAutoUse();

    /**
     * 停止物品栏自动使用
     */
    void stopItemBarAutoUse();

    /**
     * 设置物品栏使用间隔
     * @param interval 间隔时间 (毫秒)
     */
    void setItemBarInterval(int interval);
}
