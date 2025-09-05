package com.marks.leetcode.backtrack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/3 16:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1240 {

    
    /**
     * @Description:
     * 给定一个大小为 n x m 的长方形，返回贴满矩形所需的整数边正方形的最小数量。
     *
     * tips:
     * 1 <= n, m <= 13
     * @param n 
     * @param m
     * @return int
     * @author marks
     * @CreateDate: 2025/9/3 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int tilingRectangle(int n, int m) {
        int result;
        result = method_01(n, m);
        return result;
    }

    
    /**
     * @Description:
     * E1:
     * 输入：n = 11, m = 13
     * 输出：6
     * 1. int len = Math.min(n, m), len 为最大的边正方形边长, list.add(1~len), 共计len个正方形
     * 2. 可以取 list中任意数量和类型的正方形, 组合成目标矩形 n * m, 如何判断组合的长方形是否满足要求
     * 3. 算了还是用二维数组来存储, int[][] array = new int[n][m];
     * need todo!
     * @param n 
     * @param m 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/3 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int m) {
        return 0;
    }


}
