package com.marks.leetcode.backtrack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/18 10:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_51 {

    /**
     * @Description:
     * 欢迎各位勇者来到力扣城，城内设有烹饪锅供勇者制作料理，为自己恢复状态。
     * 勇者背包内共有编号为 0 ~ 4 的五种食材，其中 materials[j] 表示第 j 种食材的数量。
     * 通过这些食材可以制作若干料理，cookbooks[i][j] 表示制作第 i 种料理需要第 j 种食材的数量，而 attribute[i] = [x,y] 表示第 i 道料理的美味度 x 和饱腹感 y。
     *
     * 在饱腹感不小于 limit 的情况下，请返回勇者可获得的最大美味度。
     * 如果无法满足饱腹感要求，则返回 -1。
     * 注意：
     * 每种料理只能制作一次。
     * tips:
     * materials.length == 5
     * 1 <= cookbooks.length == attribute.length <= 8
     * cookbooks[i].length == 5
     * attribute[i].length == 2
     * 0 <= materials[i], cookbooks[i][j], attribute[i][j] <= 20
     * 1 <= limit <= 100
     *
     * @param materials
     * @param cookbooks
     * @param attribute
     * @param limit
     * @return int
     * @author marks
     * @CreateDate: 2025/8/18 10:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int perfectMenu(int[] materials, int[][] cookbooks, int[][] attribute, int limit) {
        int result;
        result = method_01(materials, cookbooks, attribute, limit);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：materials = [3,2,4,1,2] cookbooks = [[1,1,0,1,2],[2,1,4,0,0],[3,2,4,1,0]] attribute = [[3,2],[2,4],[7,6]] limit = 5
     * 输出：7
     * 1. 使用回溯法, 枚举 cookbooks[i], 对于每个 cookbooks[i], 制作或者不制作
     * 2. 递归的截止条件：index == n, 满足条件 sumY >= limit, ans = Math.max(ans, sumX), 并且 ans 的初始值为 -1。
     * 3. just code it!
     * AC: 0ms(100%)/41.44MB(47.09%)
     * @param materials
     * @param cookbooks
     * @param attribute
     * @param limit
     * @return int
     * @author marks
     * @CreateDate: 2025/8/18 10:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int n;
    private int ans;
    private int method_01(int[] materials, int[][] cookbooks, int[][] attribute, int limit) {
        n = cookbooks.length;
        ans = -1;
        backtrack(materials, cookbooks, attribute, limit, 0, 0, 0);
        return ans;
    }

    private void backtrack(int[] materials, int[][] cookbooks, int[][] attribute, int limit, int index, int sumX, int sumY) {
        if (index == n) {
            if (sumY >= limit) {
                ans = Math.max(ans, sumX);
            }
            return;
        }

        // 制作当前料理, 判断是否满足制作料理的条件, 即食材是否充足
        boolean flag = true;
        for (int i = 0; i < 5; i++) {
            if (cookbooks[index][i] > materials[i]) {
                flag = false;
                break;
            }
        }

        if (flag) {
            // 可以制作
            int[] temp = new int[5];
            System.arraycopy(materials, 0, temp, 0, 5);
            for (int i = 0; i < 5; i++) {
                temp[i] -= cookbooks[index][i];
            }

            backtrack(temp, cookbooks, attribute, limit, index + 1, sumX + attribute[index][0], sumY + attribute[index][1]);
            // 即使食材充足, 也不制作当前料理, 下一道料理更好
            backtrack(materials, cookbooks, attribute, limit, index + 1, sumX, sumY);
        } else {
            // 不可以制作
            backtrack(materials, cookbooks, attribute, limit, index + 1, sumX, sumY);
        }
    }
}
