package com.marks.leetcode.difference_array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_798 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/11 14:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_798 {

    /**
     * @Description:
     * 给你一个数组 nums，我们可以将它按一个非负整数 k 进行轮调，
     * 这样可以使数组变为 [nums[k], nums[k + 1], ... nums[nums.length - 1], nums[0], nums[1], ..., nums[k-1]] 的形式。
     * 此后，任何值小于或等于其索引的项都可以记作一分。
     * 例如，数组为 nums = [2,4,1,3,0]，我们按 k = 2 进行轮调后，它将变成 [1,3,0,2,4]。
     * 这将记为 3 分，因为 1 > 0 [不计分]、3 > 1 [不计分]、0 <= 2 [计 1 分]、2 <= 3 [计 1 分]，4 <= 4 [计 1 分]。
     * 在所有可能的轮调中，返回我们所能得到的最高分数对应的轮调下标 k 。如果有多个答案，返回满足条件的最小的下标 k 。
     * tips:
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] < nums.length
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/06/11 14:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int bestRotation(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 差分数组, 可以通过判断 nums[i] 需要x次轮调后可以获得得分, 经过 y 次轮调后无法获取得分
     * 2. 最后统计差分数组即可得到最高得分对应的轮调下标 k
     * 3. 判断 currNum 与 i 之间的关系, num[i] <= i 是才能得分
     * 3.1 currNum > i, (i - x + n) % n = n - 1; 找到 x 的最小值 x = i + 1 次, 即经过 i + 1 次轮调后, 当前处于n - 1 位置处,
     * 由于 nums[i] <= n - 1, 所以 diff[i + 1] += 1。 然后会继续轮调 i + x = currNum, x = i + 1 + (n - currNum),
     * diff[i + n - currNum] -= 1;
     * 3.2 currNum <= i, diff[0] += 1, diff[i - currNum + 1] -= 1, diff[i + 1] += 1;
     * 4. 由于下标范围可以为 n, 所以防止越界, 创建 n + 1 的数组
     * AC: 6ms/75.48MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/06/11 14:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int[] diff = new int[n + 1];
        for (int i = 0; i < n; i++) {
            int currNum = nums[i];
            if (currNum > i) {
                diff[i + 1] += 1;
                diff[i + 1 + n - currNum] -= 1;
            } else {
                diff[0] += 1;
                diff[i - currNum + 1] -= 1;
                diff[i + 1] += 1;
            }
        }
        int cnt = 0;
        int maxScore = 0;
        int minIndex = -1;
        for (int i = 0; i < n; i++) {
            cnt += diff[i];
            if (cnt > maxScore) {
                maxScore = cnt;
                minIndex = i;
            }
        }

        return minIndex;
    }

}
