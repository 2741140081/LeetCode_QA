package com.marks.tools.kkplatform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: AEEInfoDAO </p>
 * <p>描述: [存档装备强化信息数据访问对象] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/11 15:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AEEInfoDAO {
    private int aeId;
    private int aeNumber;
    private String aeName;
    private String aeImgName;
    private String aeImgPath;
    private int aePageNumber;
    private int aeEnhancedCount;
    private int aeMaxEnhancementCount;
    private Timestamp updateTime;
    private String aeDescription;
}
