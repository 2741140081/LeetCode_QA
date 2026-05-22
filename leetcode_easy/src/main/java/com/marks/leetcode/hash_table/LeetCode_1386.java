package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1386 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/22 10:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1386 {

    /**
     * @Description:
     * 如上图所示，电影院的观影厅中有 n 行座位，行编号从 1 到 n ，且每一行内总共有 10 个座位，列编号从 1 到 10 。
     * 给你数组 reservedSeats ，包含所有已经被预约了的座位。比如说，reservedSeats[i]=[3,8] ，它表示第 3 行第 8 个座位被预约了。
     * 请你返回 最多能安排多少个 4 人家庭 。4 人家庭要占据 同一行内连续 的 4 个座位。
     * 隔着过道的座位（比方说 [3,3] 和 [3,4]）不是连续的座位，但是如果你可以将 4 人家庭拆成过道两边各坐 2 人，这样子是允许的。
     *
     * tips:
     * 1 <= n <= 10^9
     * 1 <= reservedSeats.length <= min(10*n, 10^4)
     * reservedSeats[i].length == 2
     * 1 <= reservedSeats[i][0] <= n
     * 1 <= reservedSeats[i][1] <= 10
     * 所有 reservedSeats[i] 都是互不相同的。
     * @param: n
     * @param: reservedSeats
     * @return int
     * @author marks
     * @CreateDate: 2026/05/22 10:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        int result;
        result = method_01(n, reservedSeats);
        return result;
    }

    /**
     * @Description:
     * 1. reservedSeats 是座位已经被占了的, 无法安排4人家庭到座位, 安排只能选择空余的座位.
     * 2. 每一行最多安排2个4人家庭. 最少0个4人家庭.
     * 3. 对于每一行是否可以用一个前缀和来判断区间是否被占据。
     * 4. 前面分析没有考虑具体数据, 由于 reservedSeats 远小于 n, 所以只需要考虑 将被占据的座位信息列出
     * 5. 即同一行放入一个 map 中, 假设座位全空的情况下, 最多安排 2 * n 个4人家庭. 现在只需要处理占据的座位信息.
     * 6. 假设第 i 行中有 list 个座位被占据
     * AC:15ms/49.16MB
     * @param: n
     * @param: reservedSeats
     * @return int
     * @author marks
     * @CreateDate: 2026/05/22 10:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] reservedSeats) {
        Map<Integer, int[]> map = new HashMap<>();
        for (int[] reservedSeat : reservedSeats) {
            int row = reservedSeat[0];
            int col = reservedSeat[1];
            int[] list = map.getOrDefault(row, new int[10]);
            list[col - 1] = 1;
            map.put(row, list);
        }
        // 最大4人家庭数
        int max = 2 * n;
        // 处理 map 中的数据
        for (int[] list : map.values()) {
            int count = -2;
            // 构建前缀和, list[i] - list[j] 这判断的是[j + 1 ~ i] 之间是否有座位被占据
            for (int i = 1; i < 10; i++) {
                list[i] += list[i - 1];
            }
            if (list[8] - list[0] == 0) {
                count += 2;
            } else if (list[8] - list[4] == 0 || list[4] - list[0] == 0) {
                count += 1;
            } else if (list[6] - list[2] == 0) {
                count += 1;
            }
            max += count;
        }

        return max;
    }

}
