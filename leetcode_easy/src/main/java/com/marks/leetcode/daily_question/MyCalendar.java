package com.marks.leetcode.daily_question;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: 实现一个 MyCalendar 类来存放你的日程安排。如果要添加的日程安排不会造成 重复预订 ，则可以存储这个新的日程安排。
 * <p>当两个日程安排有一些时间上的交叉时（例如两个日程安排都在同一时间内），就会产生 重复预订 。
 * <p>日程可以用一对整数 startTime 和 endTime 表示，
 * <p>这里的时间是半开区间，即 [startTime, endTime), 实数 x 的范围为，  startTime <= x < endTime 。
 * <p>实现 MyCalendar 类：
 * <p>MyCalendar() 初始化日历对象。
 * <p>boolean book(int startTime, int endTime) 如果可以将日程安排成功添加到日历中而不会导致重复预订，返回 true 。否则，返回 false 并且不要将该日程安排添加到日历中。 </p>
 * <p>tips:
 * 0 <= start < end <= 10^9
 * 每个测试用例，调用 book 方法的次数最多不超过 1000 次
 * @author marks
 * @version v1.0
 * @date 2025/1/2 16:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class MyCalendar {
    private int[] times;
    private MyCalendar left;
    private MyCalendar right;

    public MyCalendar() {
        this.times = new int[]{-1, -1};
        this.left = null;
        this.right = null;
    }

    public MyCalendar(int[] times) {
        this.times = times;
        this.left = null;
        this.right = null;
    }

    public boolean book(int startTime, int endTime) {
        if (this.times[0] == -1) {
            times[0] = startTime;
            times[1] = endTime;
            return true;
        }

        return insertCalendar(new int[]{startTime, endTime});
    }

    /**
     * @Description: [
     * 1. 左子树的所有节点值都小于根节点值，而右子树的所有节点值都大于根节点值。
     *
     * AC:13ms/44.52MB
     * ]
     * @param times
     * @return boolean
     * @author marks
     * @CreateDate: 2025/1/2 17:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean insertCalendar(int[] times) {
        if (this.times[1] <= times[0]) {
            if (this.right == null) {
                this.right = new MyCalendar(times);
                return true;
            } else {
                return this.right.insertCalendar(times); // 递归调用插入
            }
        } else if (this.times[0] >= times[1]) {
            if (this.left == null) {
                this.left = new MyCalendar(times);
                return true;
            } else {
                return this.left.insertCalendar(times);
            }
        }
        return false;
    }
}
