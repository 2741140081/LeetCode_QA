package com.marks.leetcode.data_structure.heap_advance;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/12 17:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_33 {

    /**
     * @Description:
     * 给定 N 个无限容量且初始均空的水缸，每个水缸配有一个水桶用来打水，第 i 个水缸配备的水桶容量记作 bucket[i]。小扣有以下两种操作：
     *
     * 升级水桶：选择任意一个水桶，使其容量增加为 bucket[i]+1
     * 蓄水：将全部水桶接满水，倒入各自对应的水缸
     * 每个水缸对应最低蓄水量记作 vat[i]，返回小扣至少需要多少次操作可以完成所有水缸蓄水要求。
     *
     * 注意：实际蓄水量 达到或超过 最低蓄水量，即完成蓄水要求。
     *
     * tips:
     * 1 <= bucket.length == vat.length <= 100
     * 0 <= bucket[i], vat[i] <= 10^4
     *
     * @param bucket
     * @param vat
     * @return int
     * @author marks
     * @CreateDate: 2025/2/12 17:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int storeWater(int[] bucket, int[] vat) {
        int result;
        result = method_01(bucket, vat);
        result = method_02(bucket, vat);
        return result;
    }

    /**
     * @Description:
     * AC: 4ms/40.63MB
     * @param bucket
     * @param vat
     * @return int
     * @author marks
     * @CreateDate: 2025/2/14 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] bucket, int[] vat) {
        int n = bucket.length;
        int maxk = Arrays.stream(vat).max().getAsInt();
        if (maxk == 0) {
            return 0;
        }
        int res = Integer.MAX_VALUE;
        for (int k = 1; k <= maxk && k < res; ++k) {
            int t = 0;
            for (int i = 0; i < n; ++i) {
                t += Math.max(0, (vat[i] + k - 1) / k - bucket[i]);
            }
            res = Math.min(res, t + k);
        }
        return res;
    }

    /**
     * @Description:
     * E1:
     * bucket = [1,3], vat = [6,8]
     *
     * 思路:
     * 1. 计算取水的次数 (vat[i] + bucket[i] - 1) / bucket[i]
     * 2. 升级水桶 max 次, 会使总次数最小, y = (5 + x) / x
     * 数学方法如下:
     * a = bucket[i], c = vat[i], x 为升级次数
     * Math.sqrt(2 * c) <= 2 * a - 1; x = 0
     * Math.sqrt(2 * c) > 2 * a - 1; x = (int) (Math.sqrt(2 * c) + 1 - 2 * a) / 2
     *
     * 3. 我们其实可以不用考虑 Math.sqrt(2 * c) <= 2 * a - 1; 因为升级水桶次数反而变多, 所以找到x = 0时, 最大的取水的次数 countMax
     * 4. 我们需要poll()出 那些需要升级, 但是目前为升级, 且次数小于countMax的 bucket[i]
     * 5. 剩余queue 中剩余的是次数大于countMax, 那么需要升级这些水桶
     * 6. 假设给每一个需要升级的水桶, 选择最合适的升级次数, 然后计算此时的count, 如果count > countMax, countMax = count; 并且poll() 那些次数小于这个的
     * @param bucket
     * @param vat
     * @return int
     * @author marks
     * @CreateDate: 2025/2/14 9:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] bucket, int[] vat) {
        int ans = Integer.MAX_VALUE;
        int maxK = Arrays.stream(vat).max().getAsInt(); // 最大蓄水量
        Queue<int[]> upQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0])); // 按照次数降序排序
        int countMax = 0;
        int n = bucket.length;

        int count_0 = 0;
        // 初始化
        for (int i = 0; i < n; i++) {
            int a = bucket[i];
            int c = vat[i];
            if (c == 0) {
                // 最低蓄水位0, 则直接跳过
                continue;
            }

            if (a == 0) {
                // 水桶容量为0, 则必须升级水桶 + 1
                count_0++;
                a++;
            }

            int count = (c + a - 1) / a; // 最大取水次数
            double res = Math.sqrt(2 * c);
            if (res > (2 * a - 1)) {
                if (count > countMax) {
                    upQueue.offer(new int[]{count, a, c}); // 需要升级的水桶
                }
            } else {
                countMax = Math.max(countMax, count);
            }
        }

        while (!upQueue.isEmpty() && upQueue.peek()[0] <= countMax) {
            upQueue.poll();
        }

        int size = upQueue.size();
        int[][] array = new int[size][2];
        for (int i = 0; i < size; i++) {
            int[] curr_node = upQueue.poll();
            array[i][0] = curr_node[1];
            array[i][1] = curr_node[2];
        }

        for (int k = 1; k <= maxK; k++) {
            int t = 0;
            for (int i = 0; i < size; ++i) {
                t += Math.max(0, (array[i][1] + k - 1) / k - array[i][0]);
            }
            ans = Math.min(ans, t + k);
        }
        //

        return ans + count_0;
    }


}
