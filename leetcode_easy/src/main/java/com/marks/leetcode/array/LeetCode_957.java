package com.marks.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_957 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/7 16:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_957 {

    /**
     * @Description:
     * 监狱中 8 间牢房排成一排，每间牢房可能被占用或空置。
     * 每天，无论牢房是被占用或空置，都会根据以下规则进行变更：
     * 如果一间牢房的两个相邻的房间都被占用或都是空的，那么该牢房就会被占用。
     * 否则，它就会被空置。
     * 注意：由于监狱中的牢房排成一行，所以行中的第一个和最后一个牢房不存在两个相邻的房间。
     * 给你一个整数数组 cells ，用于表示牢房的初始状态：如果第 i 间牢房被占用，则 cell[i]==1，否则 cell[i]==0 。另给你一个整数 n 。
     * 请你返回 n 天后监狱的状况（即，按上文描述进行 n 次变更）。
     *
     * tips:
     * cells.length == 8
     * cells[i] 为 0 或 1
     * 1 <= n <= 10^9
     * @param: cells
     * @param: n
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/07 16:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] prisonAfterNDays(int[] cells, int n) {
        int[] result;
        result = method_01(cells, n);
        return result;
    }

    /**
     * @Description:
     * 1. 不能用for n 的方式进行强行模拟, n 值过大
     * 2. n 值过大, 感觉必定存在规律可言
     * 3. 当 n >= 1 后, 两个端点的位置永远是 0,
     * E1:
     * cells = [0,1,0,1,1,0,0,1], n = 7
     * 4. 先进行第一次变更, 获取结果, 0, 1, 1, 0, 0, 0, 0, 0
     * 5. 使用一个map存储变更结果和下标值, 例如 0, 0, 1, 1, 0, 0, 0, 0 对应的索引为 1
     * 6. 当进行 x 次变更后, int[] res; 将 res 转换为 int 类型 存储到 map 中, 存储的索引为 x。
     * 7. 转换方法: res[i] * 2^(8 - i), 然后map 判断值是否已经存在, 如果已经存在, 说明找到了循环, 循环的大小是 mod = x - map.get(res)
     * 8. 然后通过 n = n - map.get(res); int rest = n % mod;
     * 9. 如果 cells[0] || cells[7] == 1, 则不能在循环中找到, 只能找到 变更后的 res[1], cells[0] = cells[1] = 0;
     * 10. 需要用到map, 因为重复必定是 list.get(0)
     * AC: 1ms/43.39MB
     * @param: cells
     * @param: n
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/07 16:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] cells, int n) {
        int count = 0;
        int[] curr = new int[8];
        List<int[]> list = new ArrayList<>();

        if (cells[0] == 1 || cells[7] == 1) {
            count = 1;
            // 需要进行一次变更,
            for (int i = 1; i < 7; i++) {
                curr[i] = cells[i - 1] == cells[i + 1] ? 1 : 0;
            }
        } else {
            curr = cells;
        }
        list.add(curr);
        while (true) {
            int[] next = new int[8];
            // update next
            for (int i = 1; i < 7; i++) {
                next[i] = curr[i - 1] == curr[i + 1] ? 1 : 0;
            }
            if (check(list.get(0), next)) {
                break;
            }
            list.add(next);
            curr = next;
        }
        int rest = (n - count) % list.size();
        return list.get(rest);
    }

    private boolean check(int[] start, int[] next) {
        for (int i = 1; i < 7; i++) {
            if (start[i] != next[i]) {
                return false;
            }
        }
        return true;
    }

}
