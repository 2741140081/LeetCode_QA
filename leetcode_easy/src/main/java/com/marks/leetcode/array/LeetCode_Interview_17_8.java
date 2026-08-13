package com.marks.leetcode.array;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_Interview_17_8 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/1 14:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_Interview_17_8 {

    /**
     * @Description:
     * 有个马戏团正在设计叠罗汉的表演节目，一个人要站在另一人的肩膀上。
     * 出于实际和美观的考虑，在上面的人要比下面的人矮一点且轻一点。
     * 已知马戏团每个人的身高和体重，请编写代码计算叠罗汉最多能叠几个人。
     * tips:
     * height.length == weight.length <= 10000
     * @param: height
     * @param: weight
     * @return int
     * @author marks
     * @CreateDate: 2026/07/01 14:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int bestSeqAtIndex(int[] height, int[] weight) {
        int result;
        result = method_01(height, weight);
        return result;
    }

    /**
     * @Description:
     * 1. 存在两个变量, 并且肯定需要贪心的选择最佳的方案, 才能使得结果最大
     * 2. 需要结果是严格递增的序列, 所以对于相等的值, 仅能保留一个
     * 3. 将两个数组合并成一个 info[][2] = {height, weight}, 然后对 height 进行升序排序, 如果 height 相等, 则 weight 降序排序
     * 4. 当前 height 已经是升序排列, 现在只需要找到最长的递增序列 weight
     * 5. 由于 n 的范围是 10^4, 使用动态规划复杂度是 O(n^2), 10^8 有点太高了, 所以使用二分查找构建最长的递增序列
     * 6. int[] tails = new int[n]; 使用 int len, 表示当前长度, 存储最长有序递增序列,
     * 7. 对于 info[i][1], 如果 info[i][1] > tails[len - 1], 则 tails[len++] = info[i][1];
     * 否则使用二分法, 找到第一个大于 info[i][1] 的数, 然后替换成 info[i][1].
     * AC: 53ms/48MB
     * @param: height
     * @param: weight
     * @return int
     * @author marks
     * @CreateDate: 2026/07/01 14:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] height, int[] weight) {
        int n = height.length;
        if (n == 0) {
            return 0;
        }

        // 步骤3: 合并成二维数组并按身高升序、体重降序排序
        int[][] info = new int[n][2];
        for (int i = 0; i < n; i++) {
            info[i][0] = height[i];
            info[i][1] = weight[i];
        }

        Arrays.sort(info, (o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o1[0] - o2[0]; // 身高升序
            }
            return o2[1] - o1[1]; // 身高相等时，体重降序
        });

        // 步骤6: 使用二分查找构建最长递增子序列
        int[] tails = new int[n];
        int len = 0;

        // 步骤7: 遍历处理每个体重
        for (int i = 0; i < n; i++) {
            int currentWeight = info[i][1];

            // 如果当前体重大于tails的最后一个元素，直接追加
            if (len == 0 || currentWeight > tails[len - 1]) {
                tails[len++] = currentWeight;
            } else {
                // 否则使用二分查找找到第一个大于等于currentWeight的位置并替换
                int left = 0, right = len - 1;
                while (left < right) {
                    int mid = left + (right - left) / 2;
                    if (tails[mid] < currentWeight) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }
                tails[left] = currentWeight;
            }
        }

        return len;
    }

}
