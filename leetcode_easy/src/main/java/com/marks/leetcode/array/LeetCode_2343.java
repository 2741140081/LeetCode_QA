package com.marks.leetcode.array;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2343 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/14 11:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2343 {


    /**
     * @Description:
     * 给你一个下标从 0 开始的字符串数组 nums ，其中每个字符串 长度相等 且只包含数字。
     * 再给你一个下标从 0 开始的二维整数数组 queries ，其中 queries[i] = [ki, trimi] 。对于每个 queries[i] ，你需要：
     * 将 nums 中每个数字 裁剪 到剩下 最右边 trimi 个数位。
     * 在裁剪过后的数字中，找到 nums 中第 ki 小数字对应的 下标 。如果两个裁剪后数字一样大，那么下标 更小 的数字视为更小的数字。
     * 将 nums 中每个数字恢复到原本字符串。
     * 请你返回一个长度与 queries 相等的数组 answer，其中 answer[i]是第 i 次查询的结果。
     * 提示：
     * 裁剪到剩下最右边 x 个数位的意思是不断删除最左边的数位，直到剩下 x 个数位。
     * nums 中的字符串可能会有前导 0 。
     *
     * tips:
     * 1 <= nums.length <= 100
     * 1 <= nums[i].length <= 100
     * nums[i] 只包含数字。
     * 所有 nums[i].length 的长度 相同 。
     * 1 <= queries.length <= 100
     * queries[i].length == 2
     * 1 <= ki <= nums.length
     * 1 <= trimi <= nums[0].length
     * @param: nums
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/07/14 11:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] smallestTrimmedNumbers(String[] nums, int[][] queries) {
        int[] result;
        result = method_01(nums, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 先对 queries 进行间接排序, 按照 trimi 进行降序排序, 这样的好处是不需要对 nums 进行还原操作, 只需要一直剪裁数组即可.
     * 2. 需要得到 第 ki 小数字对应的下标, 使用优先队列存储 k 个 Pair ={String, idx}, Pair 是当前类的一个内部类, 实现Comparable 接口,
     * 3. 这个 Comparable 接口的实现, a. 先进行字符串比较, 另外剪裁方法是将 nums[i] 的第 j 为数字变成 0, b. 如果 a.equals(b), 则比较 idx.
     * 4. 由于 String 是不可变的, 所以需要将 String 转换成 char[], 然后进行剪裁操作.
     * AC: 445ms/47.36MB
     * 5. 使用优先队列的复杂度还是太高了, 根据提示建议使用基数排序, 减少优先队列的复杂度.
     * @param: nums
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/07/14 11:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(String[] nums, int[][] queries) {
        // 将 String[] nums 转换成 Pair[]
        int n = nums.length, m = queries.length, len = nums[0].length();
        Pair[] pairs = new Pair[n];
        for (int i = 0; i < n; i++) {
            pairs[i] = new Pair(nums[i], i);
        }
        // 通过 Integer[] index 对 queries 进行间接排序, 排序方式是降序排序, 按照trimi
        Integer[] index = new Integer[m];
        for (int i = 0; i < m; i++) {
            index[i] = i;
        }
        Arrays.sort(index, (i, j) -> queries[j][1] - queries[i][1]);
        int[] ans = new int[m];
        int prev = -1; // 记录前一次 len - trim 值
        for (Integer id : index) {
            int k = queries[id][0], trim = queries[id][1];
            // 对所有 pairs[] 进行剪裁操作, 范围是 prev + 1 ~ len - trim
            for (Pair pair : pairs) {
                int j = prev + 1;
                int end = len - trim; // 不包含 end
                while (j < end) {
                    pair.cArr[j] = '0';
                    j++;
                }
            }
            // 将 prev 进行更新
            prev = len - trim - 1;
            // 创建优先队列, 存入 k 个 Pair
            PriorityQueue<Pair> pq = new PriorityQueue<>();
            for (Pair pair : pairs) {
                pq.offer(pair);
                if (pq.size() > k) {
                    pq.poll();
                }
            }
            ans[id] = pq.peek().idx;
        }

        return ans;
    }

    class Pair implements Comparable<Pair>{
        char[] cArr;
        int idx;
        public Pair(String str, int idx) {
            this.cArr = str.toCharArray();
            this.idx = idx;
        }

        @Override
        public int compareTo(Pair o) {
            int n = this.cArr.length;
            for (int i = 0; i < n; i++) {
                if (this.cArr[i] != o.cArr[i]) {
                    return o.cArr[i] - this.cArr[i]; // 降序
                }
            }

            return o.idx - this.idx; // idx 降序, 因为栈顶元素是第 k 大的
        }
    }


}
