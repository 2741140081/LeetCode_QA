package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/13 14:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1000 {


    /**
     * @Description:
     * 有 n 堆石头排成一排，第 i 堆中有 stones[i] 块石头。
     * 每次 移动 需要将 连续的 k 堆石头合并为一堆，而这次移动的成本为这 k 堆中石头的总数。
     * 返回把所有石头合并成一堆的最低成本。如果无法合并成一堆，返回 -1 。
     *
     * tips:
     * n == stones.length
     * 1 <= n <= 30
     * 1 <= stones[i] <= 100
     * 2 <= k <= 30
     * @param stones 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/10/13 14:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int mergeStones(int[] stones, int k) {
        int result;
        result = method_01(stones, k);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：stones = [3,2,4,1], K = 2
     * 输出：20
     * 1. 判断是否能够合并为一堆 (k - 1)^x + k = n; ==> x = (n - k) % (k - 1)
     * n = 4, k = 2; x = 2 % 1 = 0; n = 4, k = 3; x = 1 % 2 = 1; n = 5, k = 3; x = 2 % 2 = 0;
     * 2. 采用贪心的思路, 下一次合并会在上一次合并的基础之上, 并且在最后的合并中， 会重复计算已经合并的区域, 所以每次合并需要找到本次最小的合并区域
     * 3. 贪心不可行, 如 stones = [6,4,4,6], k = 2; 如果按照贪心的思路, 结果是 8 + 14 + 20 = 42 > 10 + 10 + 20 = 40
     * 4. 只能使用动态规划来解决 need todo
     * @param stones 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/13 14:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] stones, int k) {
        
        return 0;
    }


}
