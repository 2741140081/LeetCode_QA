package com.marks.tools.workday.entity;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/10 17:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class UserLearningData {
    public String staffId;
    public String itCode;
    public String name;
    public double totalHours;

    public UserLearningData(String staffId, String itCode, String name, double totalHours) {
        this.staffId = staffId;
        this.itCode = itCode;
        this.name = name;
        this.totalHours = totalHours;
    }
}
