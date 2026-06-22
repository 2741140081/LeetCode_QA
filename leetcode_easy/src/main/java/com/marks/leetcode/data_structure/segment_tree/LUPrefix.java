package com.marks.leetcode.data_structure.segment_tree;

import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LUPrefix </p>
 * <p>描述: 2424. 最长上传前缀 </p>
 *
 * 给你一个 n 个视频的上传序列，每个视频编号为 1 到 n 之间的 不同 数字，你需要依次将这些视频上传到服务器。
 * 请你实现一个数据结构，在上传的过程中计算 最长上传前缀 。
 * 如果 闭区间 1 到 i 之间的视频全部都已经被上传到服务器，那么我们称 i 是上传前缀。
 * 最长上传前缀指的是符合定义的 i 中的 最大值 。
 * 请你实现 LUPrefix 类：
 *
 * LUPrefix(int n) 初始化一个 n 个视频的流对象。
 * void upload(int video) 上传 video 到服务器。
 * int longest() 返回上述定义的 最长上传前缀 的长度。
 *
 * tips:
 * 1 <= n <= 10^5
 * 1 <= video <= 10^5
 * video 中所有值 互不相同 。
 * upload 和 longest 总调用 次数至多不超过 2 * 105 次。
 * 至少会调用 longest 一次。
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/16 14:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LUPrefix {
    private PriorityQueue<Integer> small;
    private PriorityQueue<Integer> big;
    /**
     * @Description:
     * 1. 想不到怎么用线段树, 但是可以使用两个优先队列, 一个大根堆存储 0 ~ i 的连续最长前缀, 一个小根堆存储 j ~ n 的值
     * 2. 但是这个 n 就没有作用了, 先尝试使用优先队列
     * AC: 96ms/136.57MB
     * 3. 查看题解, 看看如何使用线段树来处理.
     * @param: n
     * @return
     * @author marks
     * @CreateDate: 2026/06/16 14:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public LUPrefix(int n) {
        small = new PriorityQueue<>(n + 1, (a, b) -> b - a);
        big = new PriorityQueue<>(n);
        small.offer(0);
    }

    public void upload(int video) {
        int topSmall = small.peek();
        if (video == topSmall + 1) {
            small.offer(video);
        } else {
            big.offer(video);
        }
    }

    public int longest() {
        int topSmall = small.peek();
        int topBig = big.isEmpty() ? -1 : big.peek();
        while (topSmall + 1 == topBig) {
            small.offer(topBig);
            topSmall = topBig;
            big.poll();
            if (big.isEmpty()) {
                break;
            }
            topBig = big.peek();
        }

        return small.size() - 1;
    }

}
