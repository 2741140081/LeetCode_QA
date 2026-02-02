package com.marks.leetcode.daily_question;

import java.util.TreeMap;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3013 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/2 15:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3013 {

    /**
     * @Description:
     * 给你一个下标从 0 开始长度为 n 的整数数组 nums 和两个 正 整数 k 和 dist 。
     * 一个数组的 代价 是数组中的 第一个 元素。比方说，[1,2,3] 的代价为 1 ，[3,4,1] 的代价为 3 。
     * 你需要将 nums 分割成 k 个 连续且互不相交 的子数组，满足 第二 个子数组与第 k 个子数组中第一个元素的下标距离 不超过 dist 。
     * 换句话说，如果你将 nums 分割成子数组 nums[0..(i1 - 1)], nums[i1..(i2 - 1)], ..., nums[ik-1..(n - 1)] ，那么它需要满足 ik-1 - i1 <= dist 。
     *
     * 请你返回这些子数组的 最小 总代价。
     * tips:
     * 3 <= n <= 10^5
     * 1 <= nums[i] <= 10^9
     * 3 <= k <= n
     * k - 2 <= dist <= n - 2
     * @param: nums
     * @param: k
     * @param: dist
     * @return long
     * @author marks
     * @CreateDate: 2026/02/02 15:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minimumCost(int[] nums, int k, int dist) {
        long result;
        result = method_01(nums, k, dist);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：nums = [1,3,2,6,4,2], k = 3, dist = 3
     * 输出：5
     * 1. 分割为 k 个子数组, 需要进行 k - 1 次分割点的选取, 并且需要 idx_k - idx_1 <= dist
     * 2. 这种题目大概里是进行滑动窗口, 并且结合优先队列来处理, 优先队列中
     * 3. 当 i > 0 时, 将当前 i 作为第一个分割点, 即 idx_1 = i; 可以推断出 idx_k 的范围为 [i + k - 2, i + dist] 之间
     * 4. 那么怎么得到[i, i + dist] 这个区间的最小代价, 并且 将这个区间的点放入到队列中
     * 5. 创建一个大根堆 存储 k - 2 个分割点, 再创建一个小根堆(值, 节点编号), 存储待添加的元素, 并且它的删除模式是一种懒删除模式,
     * 即过期的节点不会立刻删除, 而是在向 大根堆进行添加的过程中判断节点是否有效, 如果无效则删除,
     * 6. 如何快速计算大根堆中的元素之和? 对于大根堆而言, 如何清除过期元素? 如何处理更换元素?
     * 7. 为了方便找到索引, 使用有序集合 TreeMap 来进行存储
     * @param: nums
     * @param: k
     * @param: dist
     * @return long
     * @author marks
     * @CreateDate: 2026/02/02 15:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeMap<Integer, Integer> kMinHeap;
    private TreeMap<Integer, Integer> waitToAdd;
    private int kMinHeapSize;
    private int waitToAddSize;
    private int size;
    private long sum;
    private long method_01(int[] nums, int k, int dist) {
        // 构建 k 大小的有序集合
        kMinHeap = new TreeMap<>();
        waitToAdd = new TreeMap<>();
        size = k - 2;
        kMinHeapSize = 0;
        waitToAddSize = 0;
        sum = 0; // 记录当前 kMinHeap 的元素之和

        for (int i = 1; i < k - 1; i++) {
            // 添加初始窗口中的元素
            addNumber(nums[i]);
        }
        long ans = sum + nums[k - 1];
        // 开始进行滑动操作
        for (int i = k; i < nums.length; i++) {
            int j = i - dist - 1;
            if (j > 0) {
                // 删除 过期的 j
                erase(nums[j]);
            }
            addNumber(nums[i - 1]); // 添加i 的前一个作为窗口的终点元素/新元素
            ans = Math.min(ans, sum + nums[i]);
        }

        return ans + nums[0];
    }

    private void erase(int num) {
        if (kMinHeap.containsKey(num)) {
            removeNumFromTree(num, kMinHeap);
            kMinHeapSize--;
            sum -= num;
        } else if (waitToAdd.containsKey(num)) {
            removeNumFromTree(num, waitToAdd);
            waitToAddSize--;
        }
        adjust();
    }

    private void addNumber(int num) {
        if (!waitToAdd.isEmpty() && waitToAdd.firstKey() <= num) {
            addNumToTree(num, waitToAdd);
            waitToAddSize++;
        } else {
            addNumToTree(num, kMinHeap);
            kMinHeapSize++;
            sum += num;
        }
        adjust();
    }

    private void adjust() {
        while (kMinHeapSize < size && !waitToAdd.isEmpty()) {
            int num = waitToAdd.firstKey();
            addNumToTree(num, kMinHeap);
            kMinHeapSize++;
            sum += num;
            removeNumFromTree(num, waitToAdd);
            waitToAddSize--;
        }
        while (kMinHeapSize > size) {
            int num = kMinHeap.lastKey();
            addNumToTree(num, waitToAdd);
            waitToAddSize++;
            removeNumFromTree(num, kMinHeap);
            sum -= num;
            kMinHeapSize--;
        }

    }

    private void removeNumFromTree(int num, TreeMap<Integer, Integer> map) {
        if (map.get(num) == 1) {
            map.remove(num);
        } else {
            map.merge(num, -1, Integer::sum);
        }
    }

    private void addNumToTree(int num, TreeMap<Integer, Integer> treeMap) {
        treeMap.put(num, treeMap.getOrDefault(num, 0) + 1);
    }

}
