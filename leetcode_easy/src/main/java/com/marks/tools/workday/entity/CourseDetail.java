package com.marks.tools.workday.entity;

/**
 * <p>项目名称: 课程详细信息 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/11 11:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class CourseDetail {
    public String growCourseId;
    public String courseTitle;
    public double courseHours;

    public CourseDetail(String growCourseId, String courseTitle, double courseHours) {
        this.growCourseId = growCourseId;
        this.courseTitle = courseTitle;
        this.courseHours = courseHours;
    }

    public String getGrowCourseId() {
        return growCourseId;
    }

    public void setGrowCourseId(String growCourseId) {
        this.growCourseId = growCourseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public double getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(double courseHours) {
        this.courseHours = courseHours;
    }
}
