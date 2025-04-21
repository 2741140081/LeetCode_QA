package com.marks.leetcode.data_structure.difference;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/24 16:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1589 {
    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description:
     * 有一个整数数组 nums ，和一个查询数组 requests ，其中 requests[i] = [starti, endi] 。
     * 第 i 个查询求 nums[starti] + nums[starti + 1] + ... + nums[endi - 1] + nums[endi] 的结果 ，starti 和 endi 数组索引都是 从 0 开始 的。
     *
     * 你可以任意排列 nums 中的数字，请你返回所有查询结果之和的最大值。
     *
     * 由于答案可能会很大，请你将它对 10^9 + 7 取余 后返回。
     *
     * tips:
     * n == nums.length
     * 1 <= n <= 10^5
     * 0 <= nums[i] <= 10^5
     * 1 <= requests.length <= 10^5
     * requests[i].length == 2
     * 0 <= starti <= endi < n
     * @param nums
     * @param requests
     * @return int
     * @author marks
     * @CreateDate: 2025/2/24 16:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxSumRangeQuery(int[] nums, int[][] requests) {
        int result;
        result = method_01(nums, requests);
        return result;
    }

    /**
     * @Description:
     * AC: 214ms/71.20MB
     * @param nums
     * @param requests
     * @return int
     * @author marks
     * @CreateDate: 2025/2/24 16:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int[][] requests) {
        TreeMap<Integer, Integer> diff = new TreeMap<>();
        int n = nums.length;
        for (int[] request : requests) {
            diff.merge(request[0], 1, Integer::sum);
            diff.merge(request[1] + 1, -1, Integer::sum);
        }

        long count = 0;
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (diff.containsKey(i)) {
                count += diff.get(i);
            }
            if (count > 0) {
                list.add(count);
            }
        }

        Collections.sort(list, Collections.reverseOrder()); // 降序排序
        Arrays.sort(nums); // 升序排序
        long ans = 0;
        int index = n - 1;
        for (Long value : list) {
            ans += value * nums[index--];
        }
        ans = ans % MOD;

        return (int) ans;
    }
}
