package com.marks.leetcode.double_pointer.single_sequence;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/1 17:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2105 {
    public int minimumRefill(int[] plants, int capacityA, int capacityB) {
        int result = 0;
        result = method_01(plants, capacityA, capacityB);
        return result;
    }
    /**
     * @Description: [
     * E1:
     * 输入：plants = [2,2,3,3], capacityA = 5, capacityB = 5
     * 输出：1
     * 解释：
     * - 最初，Alice 和 Bob 的水罐中各有 5 单元水。
     * - Alice 给植物 0 浇水，Bob 给植物 3 浇水。
     * - Alice 和 Bob 现在分别剩下 3 单元和 2 单元水。
     * - Alice 有足够的水给植物 1 ，所以她直接浇水。Bob 的水不够给植物 2 ，所以他先重新装满水，再浇水。
     * 所以，两人浇灌所有植物过程中重新灌满水罐的次数 = 0 + 0 + 1 + 0 = 1 。
     *
     * AC:4ms/56.82MB
     * ]
     * @param plants
     * @param capacityA
     * @param capacityB
     * @return int
     * @author marks
     * @CreateDate: 2024/11/1 17:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] plants, int capacityA, int capacityB) {
        int n = plants.length;
        int left = 0;
        int right = n - 1;
        int ans = 0;
        int temp_A = capacityA;
        int temp_B = capacityB;
        while (left < right) {
            if (temp_A >= plants[left]) {
                temp_A -= plants[left];
            }else {
                // 装水后再去浇灌
                ans++;
                temp_A = capacityA - plants[left];
            }

            if (temp_B >= plants[right]) {
                temp_B -= plants[right];
            }else {
                ans++;
                temp_B = capacityB - plants[right];
            }
            left++;
            right--;
        }
        // 当最后浇到同一株植物时
        if(left == right) {
            if (temp_A >= temp_B && temp_A < plants[left]) {
                ans++;
            } else if (temp_A < temp_B && temp_B < plants[left]) {
                ans++;
            }
        }
        return ans;
    }
}
