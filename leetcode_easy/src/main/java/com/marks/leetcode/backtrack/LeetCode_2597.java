package com.marks.leetcode.backtrack;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/19 17:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2597 {

    
    /**
     * @Description:
     * 给你一个由正整数组成的数组 nums 和一个 正 整数 k 。
     * 如果 nums 的子集中，任意两个整数的绝对差均不等于 k ，则认为该子数组是一个 美丽 子集。
     *
     * 返回数组 nums 中 非空 且 美丽 的子集数目。
     *
     * nums 的子集定义为：可以经由 nums 删除某些元素（也可能不删除）得到的一个数组。只有在删除元素时选择的索引不同的情况下，两个子集才会被视作是不同的子集。
     * tips:
     * 1 <= nums.length <= 18
     * 1 <= nums[i], k <= 1000
     * @param nums 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/8/19 17:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int beautifulSubsets(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    
    /**
     * @Description:
     * 输入：nums = [2,4,6], k = 2
     * 输出：4
     * 1. 子集中已经存在x个元素 a[], 对于即将添加的 nums[index], |nums[index] - a[i]| <= k 成立, 那么可以添加该元素, 否则无法添加
     * 2. 那么我们如何快速判断 nums[index] 是否可以添加, 想到的一个办法是, 仅存储子集中的最大值 max 和 最小值 min, 如果 Math.abs(nums[index] - min) <= k 并且 Math.abs(nums[index] - max) <= k, 那么可以添加
     * 3. 当子集中不存在元素时? 或者子集大小为1时, max == -1 && min == -1, 表示集合为空, max == -1 && min > 0, 子集大小为1
     * 子集为0时, 直接添加元素, 设置 min = nums[index], max 值还是 -1, 添加成功; 子集大小为1, 判断 Math.abs(nums[index] - min) <= k, 如果符合, 添加, 并且更新子集的max 和 min 值;
     * max = Math.max(max, nums[index]), min = Math.min(min, nums[index]);
     * 4. 回溯 + 递归, 递归的结束条件 index == n, 并且 max != -1 || min != -1, 表示子集不为空, ans++;
     * 5. 我有点担心重复情况? 应该没有重复情况发生? 因为如果某一个子集无法满足条件, 对于不满足条件的子集，无法存在，所以不存在重复情况
     * 7. 题目理解错了, 我以为实在 k 的范围内, 结果题目要求是 Math.abs(nums[index] - a[i]) != k, 我理解成了 Math.abs(nums[index] - a[i]) <= k
     * 如果是不等于 k, 那么对于 a[index] +- k, 需要子集满足 子集使用 map 来存储, 即 map.contains(Math.abs(a[index] +- k)) 不存在, 则添加成功
     * 8. Map 的结构 Map<Integer, Integer> map, key: a[index], value: count 即数量, 看一下 nums[i] 的取值范围, 能否用 int[][2] 进行替代
     * 1000 的大小, 算了, 还是用 map 吧, 当 map.isEmpty() 判断不为空时, index == n, ans++;
     * Math.abs(a[i] - nums[index]) = k,
     * 9. just do it!
     *
     * AC: 1348ms(10.75%)/44.21MB(28.93%)
     * 10. 优化时间复杂度, 将 回溯的方式进行修改, 之前是通过 map 拷贝进行回溯, 需要修改成通过当前 map 的操作来进行回溯
     * AC: 335ms(19.84%)/44.22MB(23.14%)
     * @param nums 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/8/19 17:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int ans;
    private int n;
    private int method_01(int[] nums, int k) {
        n = nums.length;
        ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        backtrack(nums, k, 0, map);
        return ans;
    }

    private void backtrack(int[] nums, int k, int index, Map<Integer, Integer> map) {
        if (index == n) {
            if (!map.isEmpty()) {
                // 子集不为空
                ans++;
            }
            return;
        }
        boolean flag = true;
        // 子集为空时
        if (map.isEmpty()) {
            flag = true;
        } else {
            // 子集不为空时, |a[i] - nums[index]| = k, a[i] = nums[index] +- k, 判断 a[i] 是否存在
            int min = nums[index] - k;
            int max = nums[index] + k;
            if (map.containsKey(min) || map.containsKey(max)) {
                flag = false;
            }
        }
        if (flag) {
            // 可以添加, 拷贝一份 map, 如果我不拷贝map, 而是通过map.put() or map.remove() 来进行回溯, map的拷贝时间复杂度应该是O(n)
//            Map<Integer, Integer> mapCopy = new HashMap<>(map);
//            mapCopy.put(nums[index], mapCopy.getOrDefault(nums[index], 0) + 1);
            map.put(nums[index], map.getOrDefault(nums[index], 0) + 1);
            backtrack(nums, k, index + 1, map);
            if (map.get(nums[index]) > 1) {
                map.put(nums[index], map.get(nums[index]) - 1);
            } else {
                map.remove(nums[index]);
            }
            backtrack(nums, k, index + 1, map);
        } else {
            backtrack(nums, k, index + 1, map);
        }
    }


}
