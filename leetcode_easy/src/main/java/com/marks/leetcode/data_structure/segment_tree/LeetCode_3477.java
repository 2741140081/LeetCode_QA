package com.marks.leetcode.data_structure.segment_tree;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3477 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/30 9:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3477 {

    /**
     * @Description:
     * 给你两个长度为 n 的整数数组，fruits 和 baskets，
     * 其中 fruits[i] 表示第 i 种水果的 数量，baskets[j] 表示第 j 个篮子的 容量。
     * 你需要对 fruits 数组从左到右按照以下规则放置水果：
     * 每种水果必须放入第一个 容量大于等于 该水果数量的 最左侧可用篮子 中。
     * 每个篮子只能装 一种 水果。
     * 如果一种水果 无法放入 任何篮子，它将保持 未放置。
     * 返回所有可能分配完成后，剩余未放置的水果种类的数量。
     * @param: fruits
     * @param: baskets
     * @return int
     * @author marks
     * @CreateDate: 2026/01/30 9:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numOfUnplacedFruits(int[] fruits, int[] baskets) {
        int result;
        result = method_01(fruits, baskets);
        return result;
    }


    /**
     * @Description:
     * E1:
     * 输入： fruits = [4,2,5], baskets = [3,5,4]
     * 输出： 1
     * 1. 假设我们把baskets 转换成一个线段树 [3] [5]
     * 2. 查看题解, 使用了线段树来存储区间的最大值
     * AC: 3ms/45.82MB
     * @param: fruits
     * @param: baskets
     * @return int
     * @author marks
     * @CreateDate: 2026/01/30 9:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] fruits, int[] baskets) {
        SegmentTree segmentTree = new SegmentTree(baskets);
        int ans = 0; // 剩余未放置的水果数量
        for (int fruit : fruits) {
            int index = segmentTree.findFirst(fruit);
            if (index != -1) {
                segmentTree.update(index);
            } else {
                ans++;
            }
        }
        return ans;
    }

    // 构建线段树
    static class SegmentTree {
        private int n;
        private int[] tree;

        public SegmentTree(int[] nums) {
            this.n = nums.length;
            this.tree = new int[n * 4];
            build(0, n - 1, 0, nums);
        }

        private void build(int start, int end, int treeIndex, int[] nums) {
            if (start == end) {
                tree[treeIndex] = nums[start];
                return;
            }
            int mid = (end - start) / 2 + start;
            build(start, mid, treeIndex * 2 + 1, nums);
            build(mid + 1, end, treeIndex * 2 + 2, nums);
            // 更新treeIndex
            tree[treeIndex] = Math.max(tree[treeIndex * 2 + 1], tree[treeIndex * 2 + 2]); // 左右子树取较大的值
        }

        public int findFirst(int target) {
            return findFirst(0, n - 1, 0, target);
        }

        private int findFirst(int start, int end, int treeIndex, int target) {
            if (tree[treeIndex] < target) {
                // 线段树中不存在大于target的元素
                return -1;
            }
            if (start == end) {
                return start;
            }
            int mid = (end - start) / 2 + start;
            if (tree[treeIndex * 2 + 1] >= target) {
                return findFirst(start, mid, treeIndex * 2 + 1, target);
            } else if (tree[treeIndex * 2 + 2] >= target) {
                return findFirst(mid + 1, end, treeIndex * 2 + 2, target);
            }
            return -1;
        }

        public void update(int index) {
            // 更新index的值
            update(index, 0, 0, n - 1);
        }

        private void update(int rangeIndex, int treeIndex, int start, int end) {
            if (start == end) {
                tree[treeIndex] = 0;
                return;
            }
            int mid = (end - start) / 2 + start;
            if (rangeIndex <= mid) {
                update(rangeIndex, treeIndex * 2 + 1, start, mid);
            } else {
                update(rangeIndex, treeIndex * 2 + 2, mid + 1, end);
            }
            tree[treeIndex] = Math.max(tree[treeIndex * 2 + 1], tree[treeIndex * 2 + 2]); // 更新线段树
        }
    }

}
