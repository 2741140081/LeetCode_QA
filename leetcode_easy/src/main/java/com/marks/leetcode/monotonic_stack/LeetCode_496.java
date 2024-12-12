package com.marks.leetcode.monotonic_stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/28 10:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_496 {
    /**
     * @Description: [
     * nums1 中数字 x 的 下一个更大元素 是指 x 在 nums2 中对应位置 右侧 的 第一个 比 x 大的元素。
     * 给你两个 没有重复元素 的数组 nums1 和 nums2 ，下标从 0 开始计数，其中nums1 是 nums2 的子集。
     *
     * 对于每个 0 <= i < nums1.length ，找出满足 nums1[i] == nums2[j] 的下标 j ，
     * 并且在 nums2 确定 nums2[j] 的 下一个更大元素 。如果不存在下一个更大元素，那么本次查询的答案是 -1 。
     *
     * 返回一个长度为 nums1.length 的数组 ans 作为答案，满足 ans[i] 是如上所述的 下一个更大元素 。
     *
     * tips:
     * 1 <= nums1.length <= nums2.length <= 1000
     * 0 <= nums1[i], nums2[i] <= 10^4
     * nums1和nums2中所有整数 互不相同
     * nums1 中的所有整数同样出现在 nums2 中
     * ]
     * @param nums1
     * @param nums2
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/28 10:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] result;
        result = method_01(nums1, nums2);
        return result;
    }

    /**
     * @Description: [
     * 输入：nums1 = [4,1,2], nums2 = [1,3,4,2].
     * 输出：[-1,3,-1]
     * 解释：nums1 中每个值的下一个更大元素如下所述：
     * - 4 ，用加粗斜体标识，nums2 = [1,3,4,2]。不存在下一个更大元素，所以答案是 -1 。
     * - 1 ，用加粗斜体标识，nums2 = [1,3,4,2]。下一个更大元素是 3 。
     * - 2 ，用加粗斜体标识，nums2 = [1,3,4,2]。不存在下一个更大元素，所以答案是 -1 。
     *
     * AC:
     * 双重for循环:6ms/42.5MB
     * HashMap:4ms/43.5MB
     * ]
     * @param nums1
     * @param nums2
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/28 10:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] ans = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            int num = nums2[i];
            map.put(num, -1);
            while (!stack.isEmpty() && nums2[stack.peek()] < num) {
                Integer preIndex = stack.poll();
                map.put(nums2[preIndex], num);
            }
            stack.push(i);
        }
        // 这个双重for 循环太耗时, 使用HashMap更快
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < m; j++) {
//                if (nums1[i] == nums2[j]) {
//                    ans[i] = pre[j];
//                }
//            }
//        }
        for (int i = 0; i < n; i++) {
            ans[i] = map.get(nums1[i]);
        }
        return ans;
    }
}
