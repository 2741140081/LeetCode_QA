package com.marks.leetcode.array_medium;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_825 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/20 17:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_825 {

    /**
     * @Description:
     * 在社交媒体网站上有 n 个用户。给你一个整数数组 ages ，其中 ages[i] 是第 i 个用户的年龄。
     * 如果下述任意一个条件为真，那么用户 x 将不会向用户 y（x != y）发送好友请求：
     * ages[y] <= 0.5 * ages[x] + 7
     * ages[y] > ages[x]
     * ages[y] > 100 && ages[x] < 100
     * 否则，x 将会向 y 发送一条好友请求。
     * 注意，如果 x 向 y 发送一条好友请求，y 不必也向 x 发送一条好友请求。另外，用户不会向自己发送好友请求。
     * 返回在该社交媒体网站上产生的好友请求总数。
     * @param: ages
     * @return int
     * @author marks
     * @CreateDate: 2026/09/20 17:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numFriendRequests(int[] ages) {
        int result;
        result = method_01(ages);
        return result;
    }

    /**
     * @Description:
     * 1. 发送好友请求的 y 的条件是 ages[y] <= ages[x] && ages[y] > 0.5 * ages[x] + 7
     * 2. 对 ages 数组进行排序, 升序排序, 对于 ages[i], 只能在 [0 ~ i -1] 的区间找到合法的 y,
     * y 向 i 发送一条好友请求, y 的查找可以通过二分查找来实现
     * 3. 假设找到下标 j 是符合要求的最小下标, 则发送的请求数量是 i - j
     * 4. 那么完全可以使用队列存储以及遍历的 ages 数组, 对于 i, 只需要将队列首部的非法元素移除即可,
     * 有效的好友请求数量为队列的大小, 这样就不需要使用二分法, 而是使用队列替代, 优化时间复杂度
     * 5. 需要特殊处理相同 ages 的情况
     * AC: 26ms/48.2MB
     * @param: ages
     * @return int
     * @author marks
     * @CreateDate: 2026/09/20 17:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] ages) {
        // 对 ages 数组进行排序
        Arrays.sort(ages);
        // 构建一个队列, 用于存储已经遍历的 ages
        Deque<Integer> queue = new ArrayDeque<>();
        int ans = 0;
        int prev = -1;
        int cnt = 1;
        for (int age : ages) {
            while (!queue.isEmpty() && queue.peek() * 2 <= age + 14) {
                queue.poll();
            }
            ans += queue.size();
            if (age == prev && !queue.isEmpty()) {
                ans += cnt; // 这是 i 发给 [j:] 并且 ages[i] == ages[j]
                cnt++;
            } else {
                prev = age;
                cnt = 1;
            }
            queue.offer(age);
        }
        return ans;
    }

}
