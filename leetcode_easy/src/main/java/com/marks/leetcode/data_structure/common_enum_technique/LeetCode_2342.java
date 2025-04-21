package com.marks.leetcode.data_structure.common_enum_technique;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/8 10:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2342 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始的数组 nums ，数组中的元素都是 正 整数。
     * 请你选出两个下标 i 和 j（i != j），且 nums[i] 的数位和 与  nums[j] 的数位和相等。
     *
     * 请你找出所有满足条件的下标 i 和 j ，找出并返回 nums[i] + nums[j] 可以得到的 最大值 。
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * ]
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 10:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumSum(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: 使用Map(Integer, Integer) 存储数位和相同的最大值
     * AC:53ms/61.56MB
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 10:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int ans = -1;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            int sum_key = getDigitalSum(num);
            if (map.containsKey(sum_key)) {
                ans = Math.max(ans, map.get(sum_key) + num);
                map.put(sum_key, Math.max(map.get(sum_key), num));
            }else {
                map.put(sum_key, num);
            }
        }
        return ans;
    }

    /**
     * @Description: 获取数位和
     * @param num
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 10:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public static int getDigitalSum(int num) {
        int sum = 0;
        int mod = 10;
        while (num > 0) {
            sum = sum += num % mod;
            num = num / 10;
        }
        return sum;
    }
}
