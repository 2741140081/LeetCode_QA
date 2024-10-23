package com.marks.leetcode.sliding_window.random_length;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/22 10:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2271 {
    /**
     * @Description: [
     * 给你一个二维整数数组 tiles ，其中 tiles[i] = [li, ri] ，表示所有在 li <= j <= ri 之间的每个瓷砖位置 j 都被涂成了白色。
     * 同时给你一个整数 carpetLen ，表示可以放在 任何位置 的一块毯子的长度。
     * 请你返回使用这块毯子，最多 可以盖住多少块瓷砖。
     *
     * tips:
     * 1 <= tiles.length <= 5 * 10^4
     * tiles[i].length == 2
     * 1 <= li <= ri <= 10^9
     * 1 <= carpetLen <= 10^9
     * tiles 互相 不会重叠 。
     * ]
     * @param tiles
     * @param carpetLen
     * @return int
     * @author marks
     * @CreateDate: 2024/10/22 10:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumWhiteTiles(int[][] tiles, int carpetLen) {
        int result = 0;
        result = method_01(tiles, carpetLen);
        result = method_02(tiles, carpetLen);
        return result;
    }

    private int method_02(int[][] tiles, int carpetLen) {
        Arrays.sort(tiles, Comparator.comparingInt(o -> o[0]));
        int n = tiles.length, l = 0, r = 0, sum = 0, max = 0;
        while (l <= r && r < n) {
            int leftBoundary = tiles[l][0], rightBoundary = leftBoundary + carpetLen - 1;
            //未覆盖完
            if (tiles[r][1] <= rightBoundary) {
                sum += tiles[r][1] - tiles[r][0] + 1;
                r++;
                max = Math.max(sum, max);
            } else {
                //覆盖部分
                if(rightBoundary >= tiles[r][0]){
                    max = Math.max(sum + rightBoundary - tiles[r][0] + 1, max);
                }
                //调整到下一个区间开头
                sum -= tiles[l][1] - tiles[l][0] + 1;
                l++;
            }
        }

        return max;
    }

    /**
     * @Description: [
     * E1:
     * 输入：tiles = [[1,5],[10,11],[12,18],[20,25],[30,32]], carpetLen = 10
     *
     * 输出：9
     * 解释：将毯子从瓷砖 10 开始放置。
     * 总共覆盖 9 块瓷砖，所以返回 9 。
     * 注意可能有其他方案也可以覆盖 9 块瓷砖。
     * 可以看出，瓷砖无法覆盖超过 9 块瓷砖。
     *
     * 超时后修改错误, 不太想继续改, 正确查看method_02
     * ]
     * @param tiles
     * @param carpetLen
     * @return int
     * @author marks
     * @CreateDate: 2024/10/22 10:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] tiles, int carpetLen) {
        // 对tiles 数组进行排序, 由于tiles中元素互相不会重叠
        Arrays.sort(tiles, Comparator.comparingInt(o -> o[0]));
        int n = tiles.length;
        int ans = 0;
        int left = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += tiles[i][1] - tiles[i][0] + 1;
            // 特殊情况, 存在某一段白色大于等于毯子的长度
            if ((tiles[i][1] - tiles[i][0] + 1) >= carpetLen) {
                return carpetLen;
            }

            // temp < carpetLen, 此时毯子超过当前块, 并且可能会覆盖前一个白色块的全部or部分
            int index = tiles[i][1] - carpetLen + 1; // the sliding windows left index

            // 需要找到index 是否存在于tiles[left][0] ~ tiles[left][1] 之中
            while (tiles[left][1] < index) {
                sum -= (tiles[left][1] - tiles[left][0] + 1);
                left++;
            }

            if (tiles[left][0] < index) {
                // index 位于白色块中
                sum = sum - (index - tiles[left][0]);
            }

            ans = Math.max(ans, sum);
        }
        return ans;
    }
}
