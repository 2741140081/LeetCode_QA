package com.marks.leetcode.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/26 16:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_967 {

    
    /**
     * @Description:
     * 返回所有长度为 n 且满足其每两个连续位上的数字之间的差的绝对值为 k 的 非负整数 。
     * 请注意，除了 数字 0 本身之外，答案中的每个数字都 不能 有前导零。例如，01 有一个前导零，所以是无效的；但 0 是有效的。
     * 你可以按 任何顺序 返回答案。
     *
     * tips:
     * 2 <= n <= 9
     * 0 <= k <= 9
     * @param n 
     * @param k
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/26 16:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] numsSameConsecDiff(int n, int k) {
        int[] result;
        result = method_01(n, k);
        return result;
    }

    
    /**
     * @Description:
     * 输入：n = 3, k = 7
     * 输出：[181,292,707,818,929]
     * 解释：注意，070 不是一个有效的数字，因为它有前导零。
     * 1. index == n, 从 index = 0 开始进行回溯递归, 不能取前导0, 所以第一位不能取0,
     * 2. 后一位字是根据前一位数字进行确定的, int prev = array[index - 1], int curr = prev + k 或者 prev - k, 仅当 curr > 0 && curr < 10 才进行递归
     * 3. 防止 max 和 min 相等, 导致出现重复结果
     * AC: 4ms/41.77MB
     *
     * @param n 
     * @param k 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/26 16:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> ans;
    private int n;
    private int[] method_01(int n, int k) {
        this.n = n;
        ans = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            int[] array = new int[n];
            array[0] = i;
            backtrack(1, k, array);
        }
        int[] result = ans.stream().mapToInt(Integer::intValue).toArray();
        return result;
    }

    private void backtrack(int index, int k, int[] array) {
        if (index == n) {
           StringBuilder sb = new StringBuilder();
           for (int i = 0; i < n; i++) {
               sb.append(array[i]);
           }
           ans.add(Integer.parseInt(sb.toString()));
           return;
        }
        int prev = array[index - 1];

        int max = prev + k;
        if (max >= 0 && max < 10) {
            array[index] = max;
            backtrack(index + 1, k, array);
        }
        int min = prev - k;
        if (min >= 0 && min < 10 && min != max) {
            array[index] = min;
            backtrack(index + 1, k, array);
        }
    }

}
