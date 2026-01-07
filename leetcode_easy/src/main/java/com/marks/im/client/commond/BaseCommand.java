package com.marks.im.client.commond;

import java.util.Scanner;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: BaseCommand </p>
 * <p>描述: [接口类] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/7 17:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface BaseCommand {

    // 获取命令的 key
    String getKey();

    // 获取命令的提示信息
    String getTips();

    // 从控制台提取业务数据
    void exec(Scanner scanner);
}
