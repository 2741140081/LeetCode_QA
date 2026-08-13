package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_835 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/24 16:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_835 {


    /**
     * @Description:
     * 给你两个图像 img1 和 img2 ，两个图像的大小都是 n x n ，用大小相同的二进制正方形矩阵表示。
     * 二进制矩阵仅由若干 0 和若干 1 组成。
     * 转换 其中一个图像，将所有的 1 向左，右，上，或下滑动任何数量的单位；然后把它放在另一个图像的上面。
     * 该转换的 重叠 是指两个图像 都 具有 1 的位置的数目。
     * 请注意，转换 不包括 向任何方向旋转。越过矩阵边界的 1 都将被清除。
     * 最大可能的重叠数量是多少？
     *
     * tips:
     * n == img1.length == img1[i].length
     * n == img2.length == img2[i].length
     * 1 <= n <= 30
     * img1[i][j] 为 0 或 1
     * img2[i][j] 为 0 或 1
     * @param: img1
     * @param: img2
     * @return int
     * @author marks
     * @CreateDate: 2026/07/24 16:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int largestOverlap(int[][] img1, int[][] img2) {
        int result;
        result = method_01(img1, img2);
        return result;
    }

    /**
     * @Description:
     * 1. 枚举将 img1 进行上下左右滑动, 最大不会超过, 数量是 (-n - 1 ~ n - 1)
     * AC: 66ms/43.54MB
     * @param: img1
     * @param: img2
     * @return int
     * @author marks
     * @CreateDate: 2026/07/24 16:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] img1, int[][] img2) {
        int n = img1.length;
        int result = 0;
        for (int i = -(n - 1); i < n; i++) {
            for (int j = -(n - 1); j < n; j++) {
                int cnt = 0;
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        if (k + i >= 0 && k + i < n && l + j >= 0 && l + j < n)
                            if (img1[k][l] == 1 && img2[k + i][l + j] == 1)
                                cnt++;
                    }
                }
                result = Math.max(result, cnt);
            }
        }

        return result;
    }

}
