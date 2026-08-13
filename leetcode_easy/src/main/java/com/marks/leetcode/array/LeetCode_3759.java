package com.marks.leetcode.array;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3759 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/3 10:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3759 {

    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 nums 和一个整数 k。
     * 如果数组 nums 中的某个元素满足以下条件，则称其为 合格元素：存在 至少 k 个元素 严格大于 它。
     * 返回一个整数，表示数组 nums 中合格元素的总数。
     * tips:
     * 1 <= n == nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 0 <= k < n
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/07/03 10:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countElements(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 先对 nums[] 进行降序排序
     * 2. 对于 i, i > 0, 如果 nums[i] != nums[i - 1], 此时判断 cnt 与 k 的关系
     * 3. 如果 cnt >= k, 则返回[i, n - 1] 的长度 n - i, 否则 cnt++
     * 4. 如果 nums[i] == nums[i - 1], cnt++, 处理下一个
     * AC: 46ms/131.26MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/07/03 10:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int k) {
        int n = nums.length;
        if (k == 0) {
            return n;
        }
        Arrays.sort(nums);
        int cnt = 1;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] != nums[i + 1]) {
                if (cnt >= k) { // [i + 1, n - 1] 是非法元素, 但是[0, i] 是合法个数, 所以返回 i + 1
                    return i + 1;
                }
            }
            cnt++;
        }
        return 0;
    }

    /**
     * @Description:
     * 1. 使用 map 存储数组中每个元素出现的次数
     * 2. 将 key 转为 List, 并且降序排序
     * 3. 使用 cnt 记录前[0, i - 1] 的总元素个数 即cnt += map.get(j), 0 <= j <= i - 1
     * 4. 如果 cnt >= k, 则 int n 为 list 大小, 则 result = n - i
     * 5. 找到问题所在了, 返回值错误, 返回的是 n - i, 则完全错误, 这种仅仅计算了list中单个元素的合法个数, 忘记了 map 中这些元素可能存在多个
     * AC: 296ms/180.93MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/07/03 10:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        if (k == 0) {
            return n;
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        List<Integer> list = new ArrayList<>(map.keySet());
        list.sort(Collections.reverseOrder());

        int cnt = 0;
        int m = list.size();
        for (int i = 0; i < m; i++) {
            if (cnt >= k) {
                return n - cnt;
            }
            cnt += map.get(list.get(i));
        }

        return 0;
    }

}
