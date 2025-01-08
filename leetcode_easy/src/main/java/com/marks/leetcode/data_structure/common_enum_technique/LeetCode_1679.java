package com.marks.leetcode.data_structure.common_enum_technique;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/8 14:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1679 {
    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k 。
     * 每一步操作中，你需要从数组中选出和为 k 的两个整数，并将它们移出数组。
     * 返回你可以对数组执行的最大操作数。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 1 <= k <= 10^9
     * @param nums 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 14:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxOperations(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }

    /**
     * @Description: 官解: 排序 + 双指针
     * AC:21ms/56.93MB
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 14:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int k) {
        int n = nums.length;
        int ans = 0;
        Arrays.sort(nums);
        int left = 0;
        int right = n - 1;
        while (left < right) {
            if (nums[left] + nums[right] > k) {
                right--;
            } else if (nums[left] + nums[right] < k) {
                left++;
            } else {
                left++;
                right--;
                ans++;
            }
        }

        return ans;
    }

    /**
     * @Description:
     * 1. 将num 存储在Map中, 记录num 的数量,
     * 2. 遍历num, 并且map.contains(k - num), 如果存在, 同时减少map中num1 和 num2的数量, ans++;
     * AC:42ms/56.33MB
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 14:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (int num1 : nums) {
            int num2 = k - num1;
            if (map.containsKey(num2) && map.containsKey(num1)) {
                if (num1 == num2 && map.get(num1) > 1) {
                    if (map.get(num1) == 2) {
                        map.remove(num1);
                    }else {
                        map.put(num1, map.get(num1) - 2);
                    }
                    ++ans;
                } else if (num1 != num2){
                    if (map.get(num1) == 1) {
                        map.remove(num1);
                    } else {
                        map.put(num1, map.get(num1) - 1);
                    }

                    if (map.get(num2) == 1) {
                        map.remove(num2);
                    } else {
                        map.put(num2, map.get(num2) - 1);
                    }
                    ++ans;
                }
            }
        }
        return ans;
    }
}
