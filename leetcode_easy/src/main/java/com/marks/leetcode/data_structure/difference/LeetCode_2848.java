package com.marks.leetcode.data_structure.difference;

import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/17 15:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2848 {
    /**
     * @Description:
     * 给你一个下标从 0 开始的二维整数数组 nums 表示汽车停放在数轴上的坐标。对于任意下标 i，nums[i] = [starti, endi] ，
     * 其中 starti 是第 i 辆车的起点，endi 是第 i 辆车的终点。
     *
     * 返回数轴上被车 任意部分 覆盖的整数点的数目。
     * tips:
     * 1 <= nums.length <= 100
     * nums[i].length == 2
     * 1 <= starti <= endi <= 100
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/2/17 15:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfPoints(List<List<Integer>> nums) {
        int result;
//        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    /**
     * @Description:
     * 差分, 查看官解
     * AC: 1ms/43.63MB
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/2/17 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(List<List<Integer>> nums) {
        int maxValue = 0;
        for (List<Integer> num : nums) {
            maxValue = Math.max(maxValue, num.get(1));
        }

        // 初始化差分数组
        int[] diff = new int[maxValue + 2];

        for (List<Integer> num : nums) {
            diff[num.get(0)]++;
            diff[num.get(1) + 1]--; // 关键点: diff[i], diff[j], 即[i, j]之间的count > 0
        }
        int ans = 0, count = 0;
        for (int i = 1; i <= maxValue; i++) {
            count += diff[i];
            if (count > 0) {
                ans++;
            }
        }
        return ans;
    }

    /**
     * @Description:
     * 暴力(说好听一点是 模拟, 其实就是暴力破解)
     * AC: 5ms/43.40MB
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/2/17 15:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<List<Integer>> nums) {
        int[] pre = new int[100];
        for (List<Integer> num : nums) {
            for (int i = num.get(0); i <= num.get(1); i++) {
                if (pre[i] == 0) {
                    pre[i] = 1;
                }
            }
        }
        int ans = Arrays.stream(pre).sum();
        return ans;
    }
}
