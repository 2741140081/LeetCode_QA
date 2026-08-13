package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1007 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/28 10:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1007 {


    /**
     * @Description:
     * 在一排多米诺骨牌中，tops[i] 和 bottoms[i] 分别代表第 i 个多米诺骨牌的上半部分和下半部分。
     * （一个多米诺是两个从 1 到 6 的数字同列平铺形成的 —— 该平铺的每一半上都有一个数字。）
     * 我们可以旋转第 i 张多米诺，使得 tops[i] 和 bottoms[i] 的值交换。
     * 返回能使 tops 中所有值或者 bottoms 中所有值都相同的最小旋转次数。
     * 如果无法做到，返回 -1.
     * @param: tops
     * @param: bottoms
     * @return int
     * @author marks
     * @CreateDate: 2026/07/28 10:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minDominoRotations(int[] tops, int[] bottoms) {
        int result;
        result = method_01(tops, bottoms);
        return result;
    }

    /**
     * @Description:
     * 1. 遍历 n, 获取 cnt[7], 是否满足 cnt[i] == n, 如果不存在, 则返回-1
     * 2. 如果存在多个满足条件, 遍历满足条件的 i, 最多只会存在两组, 对于 i, 将所有 i 置于 top, 所需的交换次数,
     * 以及将 i 置于 bottoms, 所需的交换次数, 另外一组不需要处理.
     * AC: 5ms/50.58MB
     * @param: tops
     * @param: bottoms
     * @return int
     * @author marks
     * @CreateDate: 2026/07/28 10:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] tops, int[] bottoms) {
        int n = tops.length;
        int[] cnt = new int[7];
        for (int i = 0; i < n; i++) {
            cnt[tops[i]]++;
            if (tops[i] != bottoms[i]) {
                cnt[bottoms[i]]++;
            }
        }
        int id = -1;
        for (int i = 1; i < 7; i++) {
            if (cnt[i] == n) {
                id = i;
                break;
            }
        }
        if (id == -1) {
            return -1;
        }
        int up = 0, down = 0; // 记录将 i 放在 tops 和 bottoms 分别所需的交换次数
        for (int i = 0; i < n; i++) {
            if (tops[i] != id) {
                up++;
            }
            if (bottoms[i] != id) {
                down++;
            }
        }

        return Math.min(up, down);
    }

}
