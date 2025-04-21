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
 * @date 2025/1/8 17:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1814 {
    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description:
     * 给你一个数组 nums ，数组中只包含非负整数。定义 rev(x) 的值为将整数 x 各个数字位反转得到的结果。
     * 比方说 rev(123) = 321 ， rev(120) = 21 。我们称满足下面条件的下标对 (i, j) 是 好的 ：
     *
     * 0 <= i < j < nums.length
     * nums[i] + rev(nums[j]) == nums[j] + rev(nums[i])
     * 请你返回好下标对的数目。由于结果可能会很大，请将结果对 109 + 7 取余 后返回。
     *
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 17:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countNicePairs(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * nums[i] + rev(nums[j]) == nums[j] + rev(nums[i])
     * 调整一下, 改成nums[i] - rev(nums[i]) == nums[j] - rev(nums[j])
     * Map 存储 key = num[i] - rev(nums[i])
     * AC:90ms/58.82MB
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/1/8 17:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        long ans = 0;
        Map<Long, Integer> map = new HashMap<>();
        for (int num : nums) {
            long key = (long) num - rev(num); // 可能会超过Integer.MAX_VALUE
            if (map.containsKey(key)) {
                ans = (ans + map.get(key)) % MOD;
            }
            map.put(key, map.getOrDefault(key, 0) + 1);
        }
        return (int) ans;
    }

    private int revByStringReverse(int num) {
        String str = Integer.toString(num);
        StringBuilder str_rev = new StringBuilder(str).reverse();
        while (str_rev.length() > 1 && str_rev.charAt(0) == '0') {
            str_rev.deleteCharAt(0);
        }
        return Integer.parseInt(String.valueOf(str_rev));
    }

    private int rev(int n) {
        int rev = 0;
        for ( ; n > 0 ; n /= 10) {
            rev = rev * 10 + n % 10;
        }
        return rev;
    }
}
