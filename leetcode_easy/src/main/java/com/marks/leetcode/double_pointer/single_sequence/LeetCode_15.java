package com.marks.leetcode.double_pointer.single_sequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/5 10:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_15 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    private List<List<Integer>> method_02(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        // 枚举 a
        for (int first = 0; first < n; ++first) {
            // 需要和上一次枚举的数不相同
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            // c 对应的指针初始指向数组的最右端
            int third = n - 1;
            int target = -nums[first];
            // 枚举 b
            for (int second = first + 1; second < n; ++second) {
                // 需要和上一次枚举的数不相同
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                // 需要保证 b 的指针在 c 的指针的左侧
                while (second < third && nums[second] + nums[third] > target) {
                    --third;
                }
                // 如果指针重合，随着 b 后续的增加
                // 就不会有满足 a+b+c=0 并且 b<c 的 c 了，可以退出循环
                if (second == third) {
                    break;
                }
                if (nums[second] + nums[third] == target) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(nums[first]);
                    list.add(nums[second]);
                    list.add(nums[third]);
                    ans.add(list);
                }
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * 输入：nums = [-1,0,1,2,-1,-4]
     * 输出：[[-1,-1,2],[-1,0,1]]
     * 超时
     * ]
     * @param nums
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2024/11/5 10:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(int[] nums) {
        int n = nums.length;
        ArrayList<List<Integer>> ans = new ArrayList<>();

        Arrays.sort(nums);

        for (int i = 0; i < n - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int temp = -nums[i];
            int left = i + 1;
            int right = n - 1;

            if (temp < 0) {
                break;
            }
            // nums[first] + nums[n-2] + nums[n-1] < 0
            if (nums[i] + nums[n - 2] + nums[n - 1] < 0) {
                continue;
            }

            while (left < right) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(nums[i]);

                if (nums[left] + nums[right] == temp) {
                    list.add(nums[left]);
                    list.add(nums[right]);
                    right--;
                }else if (nums[left] + nums[right] > temp) {
                    right--;
                }else {
                    left++;
                }

                if (list.size() == 3 && !ans.contains(list)) {
                    ans.add(list);
                }
            }

        }

        return ans;
    }
}
