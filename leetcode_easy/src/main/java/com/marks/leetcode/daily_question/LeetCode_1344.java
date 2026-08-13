package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1344 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/18 11:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1344 {

    /**
     * @Description:
     * 给你两个数 hour 和 minutes 。请你返回在时钟上，由给定时间的时针和分针组成的较小角的角度（60 单位制）。
     * @param: hour
     * @param: minutes
     * @return double
     * @author marks
     * @CreateDate: 2026/06/18 11:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double angleClock(int hour, int minutes) {
        double result;
        result = method_01(hour, minutes);
        return result;
    }

    /**
     * @Description:
     * 1. 360/12 = 30, 即每一个小时对于 30度, 360/60 = 6, 每分钟是6度
     * 2. double leave = 30 * (mins/60.0)
     * 3. 分别计算时针和分针于 12 点的夹角a, b, int sub = Math.abs(a - b), Math.min(sub, 360 - sub);
     * AC: 0ms/44.37MB
     * @param: hour
     * @param: minutes
     * @return double
     * @author marks
     * @CreateDate: 2026/06/18 11:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double method_01(int hour, int minutes) {
        double hd = 30 * hour + 0.5 * minutes;
        double md = 6 * minutes;
        double sub = Math.abs(hd - md);
        return Math.min(sub, 360 - sub);
    }

}
