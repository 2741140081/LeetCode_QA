package com.marks.leetcode.array;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1093 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/27 17:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1093 {

    /**
     * @Description:
     * 我们对 0 到 255 之间的整数进行采样，并将结果存储在数组 count 中：count[k] 就是整数 k 在样本中出现的次数。
     * 计算以下统计数据:
     * minimum ：样本中的最小元素。
     * maximum ：样品中的最大元素。
     * mean ：样本的平均值，计算为所有元素的总和除以元素总数。
     * median ：
     * 如果样本的元素个数是奇数，那么一旦样本排序后，中位数 median 就是中间的元素。
     * 如果样本中有偶数个元素，那么中位数median 就是样本排序后中间两个元素的平均值。
     * mode ：样本中出现次数最多的数字。保众数是 唯一 的。
     * 以浮点数数组的形式返回样本的统计信息 [minimum, maximum, mean, median, mode] 。与真实答案误差在 10^-5 内的答案都可以通过。
     *
     * tips:
     * count.length == 256
     * 0 <= count[i] <= 10^9
     * 1 <= sum(count) <= 10^9
     *  count 的众数是 唯一 的
     * @param: count
     * @return double[]
     * @author marks
     * @CreateDate: 2026/07/27 17:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double[] sampleStats(int[] count) {
        double[] result;
        result = method_01(count);
        return result;
    }

    /**
     * @Description:
     * [minimum, maximum, mean, median, mode]
     * AC: 1ms/47.84MB
     * @param: count
     * @return double[]
     * @author marks
     * @CreateDate: 2026/07/27 17:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double[] method_01(int[] count) {
        // 1. 计算 cnt 和 mode
        double sum = 0, cnt = 0;
        int n = count.length;
        int mode = 0, modeId = -1;
        int min = n - 1, max = 0;
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (count[i] != 0) {
                // 处理平均数
                cnt += count[i];
                sum += ((long) count[i] * i);
                if (count[i] > mode) {
                    // 处理众数
                    mode = count[i];
                    modeId = i;
                }

                // 处理最大值和最小值
                min = Math.min(min, i);
                max = Math.max(max, i);

                list.add(i);
            }
        }
        double[] ans = new double[5];
        ans[0] = min;
        ans[1] = max;
        ans[2] = sum / cnt;
        ans[4] = modeId;
        // 偶数求中位数
        int targetIdx = (int) (cnt / 2);
        double left = -1.0, right = -1.0;
        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i);
            if (targetIdx >= count[id]) {
                targetIdx -= count[id];
            } else {
                if (targetIdx > 0) {
                    left = id;
                    right = id;
                } else {
                    left = list.get(i - 1);
                    right = id;
                }
                break;
            }
        }
        if (cnt % 2 == 0) {
            ans[3] = (left + right) / 2;
        } else {
            ans[3] = right;
        }

        return ans;
    }

}
