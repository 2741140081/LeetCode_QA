package com.marks.leetcode.backtrack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/15 16:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3556 {

    /**
     * @Description:
     * 给你一个整数数组 nums，其中包含的正整数 互不相同 ，另给你一个整数 target。
     *
     * 请判断是否可以将 nums 分成两个 非空、互不相交 的 子集 ，并且每个元素必须  恰好 属于 一个 子集，使得这两个子集中元素的乘积都等于 target。
     *
     * 如果存在这样的划分，返回 true；否则，返回 false。
     *
     * 子集 是数组中元素的一个选择集合。
     *
     * tips:
     * 3 <= nums.length <= 12
     * 1 <= target <= 10^15
     * 1 <= nums[i] <= 100
     * nums 中的所有元素互不相同。
     *
     * @param nums 
     * @param target
     * @return boolean
     * @author marks
     * @CreateDate: 2025/8/15 16:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean checkEqualPartitions(int[] nums, long target) {
        boolean result;
        result = method_01(nums, target);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入： nums = [3,1,6,8,4], target = 24
     * 输出： true
     * 1. 递归结束条件: 找到符合条件的nums元素 或者 index == n
     * 2. 递归逻辑: 找到符合条件的nums元素, long s1, s2 分别表示两个子集的元素乘积
     * 3. 当前元素添加到s1中, 递归调用, index + 1, s1, s2, 当前元素放入 s2, 递归调用, index + 1, s1, s2
     * 4. index == n && ans is true, 否则判断 s1 == target && s2 == target, 满足 ans = true
     *
     * AC: 2ms(72.18%)/41.14MB(51.38%)
     * @param nums 
     * @param target 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/8/15 16:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean ans = false;
    private int n;
    private boolean method_01(int[] nums, long target) {
        n = nums.length;
        long s1 = 1, s2 = 1;
        dfs(nums, 0, s1, s2, target);
        return ans;
    }

    private void dfs(int[] nums, int index, long s1, long s2, long target) {
        if (ans) {
            return;
        }
        if (index == n) {
            if (s1 == target && s2 == target) {
                ans = true;
            }
            return;
        }
        dfs(nums, index + 1, s1 * nums[index], s2, target);
        dfs(nums, index + 1, s1, s2 * nums[index], target);
    }
}
