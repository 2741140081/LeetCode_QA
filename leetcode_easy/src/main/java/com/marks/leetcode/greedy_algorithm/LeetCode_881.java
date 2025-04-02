package com.marks.leetcode.greedy_algorithm;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/2 17:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_881 {
    public int numRescueBoats(int[] people, int limit) {
        int result;
        result = method_01(people, limit);
        return result;
    }

    /**
     * @Description:
     * 1. 先排序, + 双指针
     *
     * AC: 16ms/52.43MB
     * @param people p[i] 表示第 i 个人的重量
     * @param limit 船的最大载重量
     * @return int
     * @author marks
     * @CreateDate: 2025/4/2 17:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] people, int limit) {
        int n = people.length;
        int ans = 0;
        int left = 0, right = n - 1;
        Arrays.sort(people);

        while (left <= right) {
            if (people[right] + people[left] <= limit) {
                ans++;
                right--;
                left++;
            } else {
                ans++;
                right--;
            }
        }

        return ans;
    }
}
