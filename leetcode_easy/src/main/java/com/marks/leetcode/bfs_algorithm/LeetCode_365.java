package com.marks.leetcode.bfs_algorithm;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_365 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/19 11:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_365 {

    /**
     * @Description: [方法描述]
     * @param: x
     * @param: y
     * @param: target
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/19 11:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean canMeasureWater(int x, int y, int target) {
        boolean result;
        result = method_01(x, y, target);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入: x = 3,y = 5,target = 4
     * 输出: true
     * need todo
     * @param: x
     * @param: y
     * @param: target
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/19 11:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int x, int y, int target) {
        if (target > x + y) {
            return false;
        }
        if (target == x || target == y || target == x + y) {
            return true;
        }
        if (x == y) {
            return false;
        }
        int temp = Math.min(x, y);
        y = Math.max(x, y);
        x = temp;

        return false;
    }

}
