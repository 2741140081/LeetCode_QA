package com.marks.leetcode.data_structure.binary_indexed_tree;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/25 10:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1850 {

    /**
     * @Description:
     * 给你一个表示大整数的字符串 num ，和一个整数 k 。
     *
     * 如果某个整数是 num 中各位数字的一个 排列 且它的 值大于 num ，则称这个整数为 妙数 。可能存在很多妙数，但是只需要关注 值最小 的那些。
     *
     * 例如，num = "5489355142" ：
     * 第 1 个最小妙数是 "5489355214"
     * 第 2 个最小妙数是 "5489355241"
     * 第 3 个最小妙数是 "5489355412"
     * 第 4 个最小妙数是 "5489355421"
     * 返回要得到第 k 个 最小妙数 需要对 num 执行的 相邻位数字交换的最小次数 。
     *
     * 测试用例是按存在第 k 个最小妙数而生成的。
     * @param num 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/3/25 10:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int getMinSwaps(String num, int k) {
        int result;
        result = method_01(num);
        return result;
    }


    /**
     * @Description:
     * "5489355142"
     * 翻转1次, "5489355124"
     * 翻转2次, "5489355214"
     *
     * @param num
     * @return int
     * @author marks
     * @CreateDate: 2025/3/25 10:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String num) {
        return 0;
    }
}
