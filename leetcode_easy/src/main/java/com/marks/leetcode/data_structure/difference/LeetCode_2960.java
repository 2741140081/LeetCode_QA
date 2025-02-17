package com.marks.leetcode.data_structure.difference;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/17 17:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2960 {
    /**
     * @Description: [功能描述]
     * @param batteryPercentages
     * @return int
     * @author marks
     * @CreateDate: 2025/2/17 17:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countTestedDevices(int[] batteryPercentages) {
        int result;
        result = method_01(batteryPercentages);
        return result;
    }

    /**
     * @Description:
     * AC: 0ms/42.34MB
     * @param batteryPercentages
     * @return int
     * @author marks
     * @CreateDate: 2025/2/17 17:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] batteryPercentages) {
        int count = 0;
        for (int percentage : batteryPercentages) {
            if (percentage - count > 0) {
                count++;
            }
        }
        return count;
    }
}
