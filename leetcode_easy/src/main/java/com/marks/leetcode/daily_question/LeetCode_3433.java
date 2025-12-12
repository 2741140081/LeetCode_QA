package com.marks.leetcode.daily_question;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3433 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/12 16:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3433 {

    /**
     * @Description:
     * 给你一个整数 numberOfUsers 表示用户总数，另有一个大小为 n x 3 的数组 events 。
     *
     * 每个 events[i] 都属于下述两种类型之一：
     *
     * 消息事件（Message Event）：["MESSAGE", "timestampi", "mentions_string_i"]
     * 事件表示在 time_stamp_i 时，一组用户被消息提及。
     * mentions_string_i 字符串包含下述标识符之一：
     * id<number>：其中 <number> 是一个区间 [0,numberOfUsers - 1] 内的整数。可以用单个空格分隔 多个 id ，并且 id 可能重复。此外，这种形式可以提及离线用户。
     * ALL：提及 所有 用户。
     * HERE：提及所有 在线 用户。
     * 离线事件（Offline Event）：["OFFLINE", "timestampi", "idi"]
     * 事件表示用户 idi 在 timestampi 时变为离线状态 60 个单位时间。用户会在 timestampi + 60 时自动再次上线。
     * 返回数组 mentions ，其中 mentions[i] 表示  id 为  i 的用户在所有 MESSAGE 事件中被提及的次数。
     *
     * 最初所有用户都处于在线状态，并且如果某个用户离线或者重新上线，其对应的状态变更将会在所有相同时间发生的消息事件之前进行处理和同步。
     *
     * 注意 在单条消息中，同一个用户可能会被提及多次。每次提及都需要被 分别 统计。
     * @param: numberOfUsers
     * @param: events
     * @return int[]
     * @author marks
     * @CreateDate: 2025/12/12 16:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] countMentions(int numberOfUsers, List<List<String>> events) {
        int[] result;
        result = method_01(numberOfUsers, events);
        return result;
    }

    /**
     * @Description:
     * 1. 最开始时, 所有的用户均为在线状态
     * 2. 由于是根据时间戳来依次处理事件, 那么需要对事件events[i][1] 进行排序
     * 3. 需要一个Set<Integer> 存储所有离线用户, 并且使用一个优先队列存储离线用户的再次上线的时间戳
     * 4. 有个疑问, 当timestamp相同时, 同时遇到离线事件和Message事件, 怎么处理, 如何对相同时间戳的事件进行排序
     * AC: 28ms/46.78MB
     * @param: numberOfUsers
     * @param: events
     * @return int[]
     * @author marks
     * @CreateDate: 2025/12/12 16:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int numberOfUsers, List<List<String>> events) {
        // 排序根据时间戳, 升序排序
        events.sort((o1, o2) -> {
            // 时间戳升序排序
            int timestamp1 = Integer.parseInt(o1.get(1));
            int timestamp2 = Integer.parseInt(o2.get(1));
            if (timestamp1 != timestamp2) {
                return timestamp1 - timestamp2;
            } else {
                // 先处理离线事件
                String type1 = o1.get(0);
                if (type1.equals("OFFLINE")) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        // boolean[] userStatus; true: 在线, false: 离线
        boolean[] userStatus = new boolean[numberOfUsers];
        Arrays.fill(userStatus, true);
        // 队列存储离线用户的再次上线时间戳
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int[] ans = new int[numberOfUsers];
        // 遍历events
        for (List<String> event : events) {
            String type = event.get(0);
            int timestamp = Integer.parseInt(event.get(1));
            if (type.equals("MESSAGE")) {
                String mentions = event.get(2);
                if (mentions.equals("ALL")) {
                    for (int i = 0; i < numberOfUsers; i++) {
                        // 所有用户, 包括离线用户
                        ans[i]++;
                    }
                    // 离线用户再次上线
                    while (!queue.isEmpty() && queue.peek()[0] <= timestamp) {
                        int[] poll = queue.poll();
                        userStatus[poll[1]] = true;
                    }
                } else if (mentions.equals("HERE")) {
                    // 先给离线用户上线
                    while (!queue.isEmpty() && queue.peek()[0] <= timestamp) {
                        int[] poll = queue.poll();
                        userStatus[poll[1]] = true;
                    }
                    // 给在线用户进行统计
                    for (int i = 0; i < numberOfUsers; i++) {
                        if (userStatus[i]) {
                            ans[i]++;
                        }
                    }
                } else {
                    String[] split = mentions.split(" ");
                    for (String s : split) {
                        // id+number, 切割字符串获取id
                        int id = Integer.parseInt(s.substring(2));
                        ans[id]++;
                    }
                }
            } else {
                // 离线用户再次上线
                while (!queue.isEmpty() && queue.peek()[0] <= timestamp) {
                    int[] poll = queue.poll();
                    userStatus[poll[1]] = true;
                }
                // 离线用户
                int id = Integer.parseInt(event.get(2));
                userStatus[id] = false;
                queue.offer(new int[]{timestamp + 60, id});
            }
        }
        return ans;
    }

}
