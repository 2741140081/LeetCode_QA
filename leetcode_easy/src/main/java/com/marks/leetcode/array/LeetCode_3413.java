package com.marks.leetcode.array;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3413 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/29 10:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3413 {

    /**
     * @Description:
     * 在一条数轴上有无限多个袋子，每个坐标对应一个袋子。其中一些袋子里装有硬币。
     * 给你一个二维数组 coins，其中 coins[i] = [li, ri, ci] 表示从坐标 li 到 ri 的每个袋子中都有 ci 枚硬币。
     * 数组 coins 中的区间互不重叠。
     * 另给你一个整数 k。
     *
     * 返回通过收集连续 k 个袋子可以获得的 最多 硬币数量。
     * @param: coins
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/06/29 10:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maximumCoins(int[][] coins, int k) {
        long result;
//        result = method_01(coins, k);
        result = method_02(coins, k);
        return result;
    }

    /**
     * @Description:
     * 1. 将 区间的 0 显示出来, 构建初始化的窗口
     * 2. 既然窗口中最多只能有 k 个元素, 在创建 list 时, 即规定 cnt 最大值为 k.
     * AC: 108ms/184.81MB
     * @param: coins
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/06/29 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_02(int[][] coins, int k) {
        // 先排序  coins
        Arrays.sort(coins, Comparator.comparingInt(a -> a[1]));
        // 使用 List<int[]> 存储 [0] 表示硬币数量, [1] 表示袋子数量
        List<long[]> list = new ArrayList<>();
        list.add(new long[]{0, k}); // 初始添加 k 个 0;
        for (int i = 0; i < coins.length; i++) {
            int[] c = coins[i];
            if (i > 0) {
                // 得到 0 的个数, 这个应该在之前处理
                int zeroCnt = coins[i][0] - coins[i - 1][1] - 1;
                if (zeroCnt > 0) {
                    list.add(new long[]{0, Math.min(zeroCnt, k)});
                }
            }
            long cnt = Math.min(c[1] - c[0] + 1, k);
            list.add(new long[]{c[2], cnt});
        }

        int left = 0, right = 1, n = list.size();
        long ans = 0;
        long sum = 0;

        while (right < n) {
            long cnt = list.get(right)[1]; // 最多只能添加 k 个硬币
            long key = list.get(right)[0];
            // 添加 cnt 硬币, 通过移除 left 侧的元素
            while (cnt > 0) {
                long leftKey = list.get(left)[0];
                long leftCnt = list.get(left)[1]; // 最多移除 k 个元素.
                if (leftCnt > cnt) {
                    // 移除 left 可以使得 right 侧元素完全添加
                    long add = cnt * (key - leftKey);
                    sum += add;
                    ans = Math.max(ans, sum);
                    list.get(left)[1] -= cnt;
                    cnt = 0;
                } else {
                    // 移除 left 侧的元素
                    long add = leftCnt * (key - leftKey);
                    sum += add;
                    ans = Math.max(ans, sum);
                    left++; // left++ 即不需要再赋值 list.get(left)[1] = 0
                    cnt -= leftCnt;
                }
            }
            right++;
        }

        return ans;
    }


    /**
     * @Description:
     * 1. 对 coins 进行排序, 由于区间不重叠, 所以可以使用 ri 进行排序, 对于 c[i], c[j], i < j, 则排序后必定存在 c[i][0] < c[i][1] < c[j][0] < c[j][1]
     * 2. 对于 k 而言, 相当于是一个滑动窗口, 窗口大小为 k, c[i] 有多少数量, c[i][1] - c[i][0] + 1 的数量, 硬币数量是 c[i][2]
     * 3. 使用map 存储 Map<Integer, Integer>, key 为 c[i][2], value 为 cnt 数量, 并且使用 sum 存储当前窗口中硬币的总数
     * 4. 需要那些数据可以进行窗口初始化和移动操作. int left 为左侧边界, int i 为右侧边界, int sum 为窗口中硬币总数, int currCnt 为窗口中硬币数量， currCnt <= k
     * 5. 当需要向窗口添加 c[i] 时, 需要判断 c[i][2] 与 c[left][2] 的大小关系, 如果 c[i][2] >= c[left][2]
     * 6. 忘记了中间存在空的袋子, 例如 c[i], 和 c[j], c[i][1] < c[j][0], 中间存在的空的袋子有 int zero = c[j][0] - c[i][1] - 1;
     * 7. 忘记移除窗口与窗口之间包含的 0 的袋子.
     * 8. 当前 currCnt 数量大于等于 k 时, 由于无法将 left 变道 right, 所以需要取最大值吗?
     * 9. 算了, 看题解吧, 从新梳理以下, 需要根据 left 的值来贪心的移动窗口, 我觉得应该吧 区间之间的0给列出来, 而不是放在 arr[i][2], 重新写一个 method_02
     * @param: coins
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/06/29 10:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[][] coins, int k) {
        int n = coins.length;
        // 排序
        Arrays.sort(coins, Comparator.comparingInt(a -> a[1]));
        // 将 coins 转为 long[n][2] arr的数组, arr[0] = coins[2], arr[1] = coins[i][1] - coins[i][0] + 1
        long[][] arr = new long[n + 1][3];
        arr[0][0] = 0;
        arr[0][1] = k;
        for (int i = 0; i < n; i++) {
            arr[i + 1][0] = coins[i][2];
            arr[i + 1][1] = coins[i][1] - coins[i][0] + 1;
            if (i > 0) {
                // 计算与前一个坐标之间的空袋子数量, 存入 arr[i][2]
                arr[i + 1][2] = coins[i][0] - coins[i - 1][1] - 1;
            }
        }
        // 创建滑动窗口
        Map<Long, Long> map = new HashMap<>();
        map.put(0L, (long) k); // 填入 k 个空袋子
        long ans = 0L;
        int left = 0, right = 1;
        long sum = 0;
        while (right < n + 1) {
            long currkey = arr[right][0];
            long currCnt = arr[right][1];
            if (currCnt >= k) {
                sum = currkey * k;
                ans = Math.max(ans, sum);
                map.clear();
                map.merge(currkey, (long) k, Long::sum);
                left = right;
            } else {
                long zeroKey = 0L;
                long zeroCnt = arr[right][2]; // 空袋子数量
                long leftZeroCnt = arr[left][2];
                while (zeroCnt > 0 && left < right) {
                    if (leftZeroCnt > zeroCnt) {
                        arr[left][2] -= zeroCnt;
                        break;
                    } else {
                        arr[left][2] = 0;
                        zeroCnt -= leftZeroCnt;
                    }
                    // 移除 leftCnt, 需要在 arr 上直接修改值, 而不是通过赋值的方式获取值, 这个值会每次赋值重置为k, 所以发生错误.
                    long leftCnt = arr[left][1];
                    long leftKey = arr[left][0];
                    if (leftCnt > zeroCnt) {
                        map.merge(leftKey, -zeroCnt, Long::sum);
                        map.merge(zeroKey, zeroCnt, Long::sum);
                        sum += (zeroKey - leftKey) * zeroCnt;
                        ans = Math.max(ans, sum);
                        arr[left][1] -= zeroCnt;
                        zeroCnt = 0;
                    } else {
                        map.merge(leftKey, -leftCnt, Long::sum);
                        map.merge(zeroKey, leftCnt, Long::sum);
                        sum += (zeroKey - leftKey) * leftCnt;
                        ans = Math.max(ans, sum);
                        left++;
                        zeroCnt -= leftCnt;
                    }
                }

                // 此时窗口已经是 k 个元素, 需要继续添加剩余的 currCnt - cnt 个元素
                while (currCnt > 0 && left < right) {
                    leftZeroCnt = arr[left][2]; // 从新获取 leftZeroCnt
                    // 先移除左侧的 leftZeroCnt
                    if (leftZeroCnt > currCnt) {
                        arr[left][2] -= currCnt;
                        map.merge(currkey, currCnt, Long::sum);
                        sum += (currkey - zeroKey) * currCnt;
                        ans = Math.max(ans, sum);
                        break;
                    } else {
                        arr[left][2] = 0;
                        currCnt -= leftZeroCnt;
                        map.merge(currkey, leftZeroCnt, Long::sum);
                        sum += (currkey - zeroKey) * leftZeroCnt;
                        ans = Math.max(ans, sum);
                    }
                    // 移除 leftCnt
                    long leftCnt = arr[left][1];
                    long leftKey = arr[left][0];
                    if (leftCnt > currCnt) {
                        map.merge(leftKey, -currCnt, Long::sum);
                        map.merge(currkey, currCnt, Long::sum);
                        sum += (currkey - leftKey) * currCnt;
                        ans = Math.max(ans, sum);
                        arr[left][1] -= currCnt;
                        currCnt = 0;
                    } else {
                        map.merge(leftKey, -leftCnt, Long::sum);
                        map.merge(currkey, leftCnt, Long::sum);
                        sum += (currkey - leftKey) * leftCnt;
                        ans = Math.max(ans, sum);
                        left++;
                        currCnt -= leftCnt;
                    }
                }
            }

            right++;
        }

        return ans;
    }

}
