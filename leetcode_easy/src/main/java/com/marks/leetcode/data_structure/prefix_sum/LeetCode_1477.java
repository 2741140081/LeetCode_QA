package com.marks.leetcode.data_structure.prefix_sum;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/14 15:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1477 {
    /**
     * @Description:
     * 给你一个整数数组 arr 和一个整数值 target 。
     * 请你在 arr 中找 两个互不重叠的子数组 且它们的和都等于 target 。可能会有多种方案，请你返回满足要求的两个子数组长度和的 最小值 。
     * 请返回满足要求的最小长度和，如果无法找到这样的两个子数组，请返回 -1 。
     *
     * 1 <= arr.length <= 10^5
     * 1 <= arr[i] <= 1000
     * 1 <= target <= 10^8
     * @param arr
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2025/1/14 15:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minSumOfLengths(int[] arr, int target) {
        int result;
//        result = method_01(arr, target);
        result = method_02(arr, target);
        return result;
    }

    private int method_02(int[] arr, int target) {
        int n = arr.length;
        int[] dp = new int[n];
        // 注意不能设置为最大值，因为相加会溢出
        Arrays.fill(dp, Integer.MAX_VALUE / 2);

        int ans = Integer.MAX_VALUE;
        for(int i = 0, j = 0, sum = 0; j < n; j++){
            sum += arr[j];
            while(i <= j && sum > target){
                sum -= arr[i++];
            }
            // 找到满足条件的一个区间
            if(sum == target){
                dp[j] = j - i + 1;
                if(i != 0){
                    ans = Math.min(ans, dp[i-1] + j - i + 1);
                }
            }
            if(j != 0)
                dp[j] = Math.min(dp[j], dp[j-1]);
        }

        return ans > arr.length ? -1 : ans;
    }

    /**
     * @Description:
     * 假设我们找出所有的arr[i, j]
     * TLE(超时:57/61)
     * @param arr
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2025/1/14 15:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        List<int[]> list = new ArrayList<>();
        // list.add(new int[]{3, 1, 2});
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            int key = sum - target;
            if (map.containsKey(key)) {
                int len = i - map.get(key);
                list.add(new int[]{len, map.get(key) + 1, i});
            }
            map.put(sum, i);
        }

        Collections.sort(list, Comparator.comparingInt(o -> o[0]));
        int n = list.size();
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n - 1; i++) {
            int[] one = list.get(i);
            int one_len = one[0];
            int one_i = one[1];
            int one_j = one[2];
            for (int j = i + 1; j < n; j++) {
                int[] two = list.get(j);
                int two_len = two[0];
                int two_i = two[1];
                int two_j = two[2];
                if (two_i > one_j || one_i > two_j) {
                    ans = Math.min(ans, one_len + two_len);
                    break;
                }
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
