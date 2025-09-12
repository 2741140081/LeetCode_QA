package com.marks.tools.workday.entity;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/10 17:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class UserCourseDetail {
    public String staffId;
    public String itCode;
    public String name;

    public String startDate;
    public String endDate;

    // 包含CourseDetail成员变量
    public CourseDetail courseDetail;

    // 构造函数2：简化构造函数


    public UserCourseDetail(String staffId, String itCode, String name, String startDate, String endDate, CourseDetail courseDetail) {
        this.staffId = staffId;
        this.itCode = itCode;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseDetail = courseDetail;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getItCode() {
        return itCode;
    }

    public void setItCode(String itCode) {
        this.itCode = itCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public CourseDetail getCourseDetail() {
        return courseDetail;
    }

    public void setCourseDetail(CourseDetail courseDetail) {
        this.courseDetail = courseDetail;
    }
}
