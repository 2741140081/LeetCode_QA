package com.marks.tools.kkplatform.service;

import com.marks.tools.kkplatform.entity.AEEInfoDAO;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ArchiveEquipmentService </p>
 * <p>描述: 存档装备接口类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/11 15:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface ArchiveEquipmentService {
    // 返回最小可强化的装备
    AEEInfoDAO selectMinEnhanceableEquipment();
    // 返回所有可强化的装备
    List<AEEInfoDAO> selectAllEnhanceableEquipments();

    // 根据id返回装备
    AEEInfoDAO findArchiveEquipmentById(int aeId);

    // 更新装备强化次数
    boolean updateArchiveEquipment(AEEInfoDAO aeeInfoDAO);

    // 添加装备
    int insertArchiveEquipment(AEEInfoDAO aeeInfoDAO);

    // 删除装备
    boolean deleteArchiveEquipment(int aeId);

    // 更新装备强化次数
    boolean updateEnhancedCount(int aeId, int newCount);
}
