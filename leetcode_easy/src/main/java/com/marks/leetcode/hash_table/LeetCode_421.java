package com.marks.leetcode.hash_table;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_421 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/19 15:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_421 {

    /**
     * @Description:
     * 给你一个整数数组 nums ，返回 nums[i] XOR nums[j] 的最大运算结果，其中 0 ≤ i ≤ j < n 。
     *
     * tips:
     * 1 <= nums.length <= 2 * 10^5
     * 0 <= nums[i] <= 2^31 - 1
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/19 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findMaximumXOR(int[] nums) {
        int result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    private Tire root = new Tire();
    static final int HIGH_BIT = 30;

    /**
     * @Description:
     * 1. 使用字典树
     * AC: 397ms/156.94MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/19 17:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int i = 1; i < n; i++) {
            add(nums[i - 1]);
            ans = Math.max(ans, check(nums[i]));
        }
        return ans;
    }

    private int check(int num) {
        Tire node = root;
        int ans = 0;
        for (int k = HIGH_BIT; k >= 0; k--) {
            int bit = (num >> k) & 1;
            if (bit == 0) {
                // 需要找到1, 0 ^ 1 = 1
                if (node.right != null) {
                    node = node.right;
                    ans = ans * 2 + 1;
                } else {
                    node = node.left;
                    ans = ans * 2;
                }
            } else {
                // 需要找到0, 1 ^ 0 = 1
                if (node.left != null) {
                    node = node.left;
                    ans = ans * 2 + 1;
                } else {
                    node = node.right;
                    ans = ans * 2;
                }
            }
        }
        return ans;
    }

    private void add(int num) {
        Tire node = root;
        for (int k = HIGH_BIT; k >= 0; k--) {
            int bit = (num >> k) & 1;
            if (bit == 0) {
                if (node.left == null) {
                    node.left = new Tire();
                }
                node = node.left;
            } else {
                if (node.right == null) {
                    node.right = new Tire();
                }
                node = node.right;
            }
        }
    }


    /**
     * @Description:
     * 1. 是否可以对 nums 进行降序排序
     * 2. 执行的是异或操作, 即相同为0, 不同为1; 将 nums[i] 转换为二进制数, 假设 nums[i] 的二进制的第 x 位为1, 那么 1 << x,
     * 使用map存储, key 为 1 << x, value 为 nums[i], Map<Integer, List<Integer>> map;
     * 3.遍历 nums 数组, 对于 nums[i], 如果 nums[i] 的第 x 位为 0, 通过 map.get(1 << x) 获取第 x 位为 1 的数, 需要对 list 进行升序排序
     * 4. 查看题解, 使用字典树进行解决
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/19 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        Map<Integer, List<Integer>> map = new HashMap<>();
        // 先对 nums 进行降序排序
        Arrays.sort(nums);
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            for (int j = 0; j < 32; j++) {
                if ((x & (1 << j)) != 0) {
                    map.computeIfAbsent(1 << j, k -> new ArrayList<>()).add(x);
                }
            }
        }
        int ans = 0;
        // 倒序遍历 nums

        return 0;
    }

}
class Tire {
    // 由于只有 0 和 1, 所以只需要 创建两个节点, left 为 0, right 为 1
    Tire left = null;
    Tire right = null;
}
