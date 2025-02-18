package com.marks.leetcode.data_structure.difference;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/18 10:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_56 {
    
    /**
     * @Description:
     * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
     * 请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
     * @param intervals 
     * @return int[][]
     * @author marks
     * @CreateDate: 2025/2/18 11:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] merge(int[][] intervals) {
        int[][] result;
        result = method_01(intervals);
        return result;
    }

    /**
     * @Description:
     * 对 intervals[][] 进行差分 得到 diff[]
     * int count += diff[i], for循环, 判断count > 0
     * 设置标记位 reStart = true, left = count[i], reStart = false, 如果count = 0, right = count[j], reStart = true
     * 并且将int[] curr_node = {left, right} 存储到List int[]
     *
     * 如[[1,4],[5,6]]，正确结果是[[1,4],[5,6]]，我们的算法输出[1,6]，这是因为我们是整数区间，他会连接两个区间。
     * 解决办法就是我们让flag数组长度乘2，赋值时对应值也乘2，这样做的好处是，如上个案例，[1,4]->[2,8]，[5,6]->[10,12]，中间就会出现断点9未被赋值，从而打破了两个区间不连续的问题。
     *
     * AC: 1ms/45.61MB
     * @param intervals
     * @return int[][]
     * @author marks
     * @CreateDate: 2025/2/18 11:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int[][] intervals) {
        int maxValue = 0;
        for (int[] interval : intervals) {
            maxValue = Math.max(maxValue, interval[1]);
        }

        int[] diff = new int[maxValue * 2 + 2];
        for (int[] interval : intervals) {
            int left = interval[0] * 2, right = interval[1] * 2;
            diff[left]++;
            diff[right + 1]--;
        }

        List<int[]> list = new ArrayList<>();
        int count = 0;
        boolean reStart = true;
        int left = -1, right = -1;
        for (int i = 0; i <= maxValue * 2 + 1; i++) {
            count += diff[i];
            if (count > 0 && reStart) {
                reStart = false;
                left = i / 2;
            } else if (count == 0 && !reStart) {
                reStart = true;
                right = (i - 1) / 2;
                list.add(new int[] {left, right});
            }
        }

        return list.toArray(new int[0][]);
    }
}
