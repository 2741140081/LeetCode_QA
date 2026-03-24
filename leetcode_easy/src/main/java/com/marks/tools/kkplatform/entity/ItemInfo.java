package com.marks.tools.kkplatform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ItemInfo </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/24 10:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemInfo {
    private String name;
    private int count;
}
