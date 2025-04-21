package com.marks.leetcode.data_structure.heap_advance;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/5 16:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_30 {
    /**
     * @Description:
     * 小扣当前位于魔塔游戏第一层，共有 N 个房间，编号为 0 ~ N-1。每个房间的补血道具/怪物对于血量影响记于数组 nums，
     * 其中正数表示道具补血数值，即血量增加对应数值；负数表示怪物造成伤害值，即血量减少对应数值；0 表示房间对血量无影响。
     *
     * 小扣初始血量为 1，且无上限。假定小扣原计划按房间编号升序访问所有房间补血/打怪，为保证血量始终为正值，
     * 小扣需对房间访问顺序进行调整，每次仅能将一个怪物房间（负数的房间）调整至访问顺序末尾。请返回小扣最少需要调整几次，才能顺利访问所有房间。
     * 若调整顺序也无法访问完全部房间，请返回 -1。
     * tips:
     * 1 <= nums.length <= 10^5
     * -10^5 <= nums[i] <= 10^5
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/3/5 16:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int magicTower(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：nums = [100,100,100,-250,-60,-140,-50,-50,100,150]
     * AC: 17ms/54.98MB
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/3/5 16:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if (sum <= -1) {
            return -1;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o)); // 升序排序, 因为我们存储的是一个负数, 优先将最小的取出来
        long blood = 1; // 初始化血量为 1, 应该用 long, 因为数据范围为 10^10
        int ans = 0; // 调整次数
        for (int num : nums) {
            if (num >= 0) {
                blood += num;
            } else {
                // num < 0
                blood += num;
                queue.offer(num);
                while (blood <= 0 && !queue.isEmpty()) {
                    int min_value = queue.poll();
                    blood -= min_value;
                    ans++;
                }
            }
        }

        return ans;
    }
}
