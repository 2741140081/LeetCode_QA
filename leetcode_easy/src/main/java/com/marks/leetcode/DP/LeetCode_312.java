package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_312 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/17 15:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_312 {

    /**
     * @Description:
     * 有 n 个气球，编号为0 到 n - 1，每个气球上都标有一个数字，这些数字存在数组 nums 中。
     *
     * 现在要求你戳破所有的气球。戳破第 i 个气球，你可以获得 nums[i - 1] * nums[i] * nums[i + 1] 枚硬币。
     * 这里的 i - 1 和 i + 1 代表和 i 相邻的两个气球的序号。如果 i - 1或 i + 1 超出了数组的边界，那么就当它是一个数字为 1 的气球。
     *
     * 求所能获得硬币的最大数量。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/11/17 15:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxCoins(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 输入：nums = [3,1,5,8]
     * 输出：167
     * 解释：
     * nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
     * coins =  3*1*5    +   3*5*8   +  1*3*8  + 1*8*1 = 167
     * 1. 假设有两个气球, 上面的数字分别是x, y, 计算可能得到的分数: x * y + y 和 x * y + x
     * 2. 假设有3个气球, 上面的数字分别是x, y, z, 3个气球的分数为:
     * a. x * y + y * z + Math.max(y, z),
     * b. x * y * z + x * z + Math.max(x, z)
     * c. y * z + x * y + Math.max(x, y)
     * b 方案 > Math.max(a, c)
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/11/17 15:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        
        return 0;
    }

}
