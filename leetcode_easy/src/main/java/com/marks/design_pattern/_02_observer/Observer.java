package com.marks.design_pattern._02_observer;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: Observer </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/13 15:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface Observer {
    void update(float temp, float humidity, float pressure);
}
