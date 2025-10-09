package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/9 10:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1521 {

    /**
     * @Description:
     * Winston 构造了一个如上所示的函数 func 。
     * 他有一个整数数组 arr 和一个整数 target ，他想找到让 |func(arr, l, r) - target| 最小的 l 和 r 。
     *
     * 请你返回 |func(arr, l, r) - target| 的最小值。
     *
     * 请注意， func 的输入参数 l 和 r 需要满足 0 <= l, r < arr.length 。
     * @param arr 
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2025/10/9 10:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int closestToTarget(int[] arr, int target) {
        int result;
        result = method_01(arr, target);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入：arr = [9,12,3,7,15], target = 5
     * 输出：2
     * 1. 差不多看懂题目意思了, 即找到一个连续的子数组, 使得子数组的相与的结果 ans, 并且 Math.abs(ans - target) 最小
     * 2. 在子数组不断相与的过程中, ans 是不断的递减的过程, 对于与运算, 我们只需要关注于位次上面 "0" 的个数即可, 无需关心 "1" 的个数,
     * 使用一个 int[] count 存储 "0" 的个数。
     * 3. 另外一个要求是, 子数组不能是空数组, 因为如果为空数组, 返回的ans = 10^9 > (10^6 + 10^7), 即不可能是最小的结果。
     * 4. 10^6 转为二进制需要多少位, 减少高位存储的"0", 因为这些0不是必须得, 2^17 > 10^6, 所以 int[] count = new int[17];
     * 5. 因为 int res; 表示子数组的相与结果, res 是不断减少的, 当 res < target 时, 需要移动滑动窗口, 即删除 arr[left], 使得res >= target
     * 6. 还有一个需要注意的点是, 有可能arr[l, r] 中只有一个元素, 并且他是小于target, 那么此时移动left, 会导致当前子数组为空数组,
     * 并且由于我们只计算了 "0" 的个数, 所以此时 res = 2^18 - 1, 即所有位全部为 "1", 需要为这种特殊情况做特殊处理, 具体怎么做还没想到
     *
     * AC: 47ms/54.99MB
     * @param arr 
     * @param target 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/9 10:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr, int target) {
        int n = arr.length;
        int[] count = new int[20];
        int left = 0;

        int ans = Math.abs(arr[0] - target);
        int res = arr[0];
        for (int i = 19; i >= 0; i--) {
            if ((res & (1 << i)) == 0) {
                count[i]++;
            }
        }
        for (int right = 1; right < n; right++) {
            int num = arr[right];
            for (int i = 19; i >= 0; i--) {
                if ((num & (1 << i)) == 0) {
                    count[i]++;
                }
            }
            res = 0;
            for (int i = 19; i >= 0; i--) {
                if (count[i] == 0) {
                    res |= (1 << i);
                }
            }

            ans = Math.min(ans, Math.abs(res - target));
            while (left < right && res < target) {
                num = arr[left];
                for (int i = 19; i >= 0; i--) {
                    if ((num & (1 << i)) == 0) {
                        count[i]--;
                    }
                    if (count[i] == 0) {
                        res |= (1 << i);
                    }
                }
                left++;
                ans = Math.min(ans, Math.abs(res - target));
            }
            if (ans == 0) {
                // 提前返回
                return ans;
            }
        }
        return ans;
    }

}
