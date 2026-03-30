package com.marks.auto_script.model;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ExecutionMode </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/30 15:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public enum ExecutionMode {
    FIXED_TIMES("固定次数"),
    INFINITE("无限循环");

    private final String description;

    ExecutionMode(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
