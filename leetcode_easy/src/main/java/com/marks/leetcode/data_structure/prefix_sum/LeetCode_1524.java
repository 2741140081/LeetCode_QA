package com.marks.leetcode.data_structure.prefix_sum;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/13 15:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1524 {
    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description:
     * 给你一个整数数组 arr 。请你返回和为 奇数 的子数组数目。
     *
     * 由于答案可能会很大，请你将结果对 10^9 + 7 取余后返回。
     *
     * 1 <= arr.length <= 10^5
     * 1 <= arr[i] <= 100
     *
     * @param arr
     * @return int
     * @author marks
     * @CreateDate: 2025/1/13 15:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numOfSubarrays(int[] arr) {
        int result;
        result = method_01(arr);
        return result;
    }
    
    /**
     * @Description:
     * E1:
     * 输入：arr = [1,2,3,4,5,6,7]
     * Map(1, count_odd), Map(0, count_even)
     * 依靠当前nums[i] 来计算
     * 如果nums[i] % 2 == 0, 偶数, 偶数 + 奇数 = 奇数
     * 如果nums[i] % 2 != 0, 奇数, 奇数 + 偶数 = 奇数
     * count = 0
     * num = 1, map(1, 1), count = 1
     * num = 2, map(0, 1), map(1, 2), count = 2
     * num = 3, map(1, 3), map(0, 1) => map(1, 4), count = 4
     * 忘记了一个条件，我们是一个子数组
     * AC:8ms/56.86MB
     * @param arr
     * @return int
     * @author marks
     * @CreateDate: 2025/1/13 15:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {
        int n = arr.length;
        int sum = 0;
        int ans = 0;
        int even = 1;
        int odd = 0;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
            ans = (ans + (sum % 2 == 0 ? odd : even)) % MOD;
            if (sum % 2 == 0) {
                even++;
            }else {
                odd++;
            }

        }
        return ans;
    }
}
