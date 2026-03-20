package com.marks.kkPlatformGameAuto.service;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ModifierService </p>
 * <p>描述: 修改器服务接口 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/18 10:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface ModifierService {
    /**
     * 修改经验值
     * @param experienceValue 经验值
     * @return 是否成功
     */
    boolean modifyExperience(int experienceValue);

    /**
     * 修改物品
     * @param itemNames 物品名称列表
     * @return 是否成功
     */
    boolean modifyItems(List<String> itemNames);

    /**
     * 锁定攻击间隔
     * @return 是否成功
     */
    boolean lockAttackInterval();
}
