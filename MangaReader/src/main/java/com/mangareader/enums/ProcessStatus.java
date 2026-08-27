package com.mangareader.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ProcessStatus </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/27 16:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@AllArgsConstructor
@Getter
public enum ProcessStatus {
    PENDING(0, "待处理"),
    PROCESSING(1, "正在处理"),
    COMPLETED(2, "已完成"),
    FAILED(3, "处理失败");

    private final int code;
    private final String desc;

    // 静态 Map 用于快速查找
    private static final Map<Integer, ProcessStatus> CODE_MAP = new HashMap<>();

    /**
     * 根据状态码获取枚举
     * @param code 状态码
     * @return 对应的枚举值
     * @throws IllegalArgumentException 如果状态码无效
     */
    public static ProcessStatus fromCode(int code) {
        ProcessStatus status = CODE_MAP.get(code);
        if (status == null) {
            throw new IllegalArgumentException("无效的漫画状态码: " + code);
        }
        return status;
    }


}
