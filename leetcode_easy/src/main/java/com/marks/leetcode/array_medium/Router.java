package com.marks.leetcode.array_medium;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: Router: LeetCode_3508. 设计路由器 </p>
 * <p>描述: [
 * 请你设计一个数据结构来高效管理网络路由器中的数据包。每个数据包包含以下属性：
 *
 * source：生成该数据包的机器的唯一标识符。
 * destination：目标机器的唯一标识符。
 * timestamp：该数据包到达路由器的时间戳。
 * 实现 Router 类：
 *
 * Router(int memoryLimit)：初始化路由器对象，并设置固定的内存限制。
 * memoryLimit 是路由器在任意时间点可以存储的 最大 数据包数量。
 * 如果添加一个新数据包会超过这个限制，则必须移除 最旧的 数据包以腾出空间。
 *
 * bool addPacket(int source, int destination, int timestamp)：将具有给定属性的数据包添加到路由器。
 * 如果路由器中已经存在一个具有相同 source、destination 和 timestamp 的数据包，则视为重复数据包。
 * 如果数据包成功添加（即不是重复数据包），返回 true；否则返回 false。
 *
 * int[] forwardPacket()：以 FIFO（先进先出）顺序转发下一个数据包。
 * 从存储中移除该数据包。
 * 以数组 [source, destination, timestamp] 的形式返回该数据包。
 * 如果没有数据包可以转发，则返回空数组。
 *
 * int getCount(int destination, int startTime, int endTime)：
 * 返回当前存储在路由器中（即尚未转发）的，且目标地址为指定 destination 且时间戳在范围 [startTime, endTime]（包括两端）内的数据包数量。
 * 注意：对于 addPacket 的查询会按照 timestamp 的非递减顺序进行。
 * ] </p>
 *
 * tips:
 * 2 <= memoryLimit <= 10^5
 * 1 <= source, destination <= 2 * 10^5
 * 1 <= timestamp <= 10^9
 * 1 <= startTime <= endTime <= 10^9
 * addPacket、forwardPacket 和 getCount 方法的总调用次数最多为 10^5。
 * 对于 addPacket 的查询，timestamp 按非递减顺序给出。
 * @author marks
 * @version v1.0
 * @date 2026/8/31 14:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class Router {
    private int memoryLimit;
    private Map<Integer, List<Packet>> map;
    private TreeSet<Packet> treeSet;
    private Deque<Packet> queue;

    /**
     * @Description:
     * 1. 需要一种数据结构, 可以增加, 删除, 并且需要按照添加的顺序 FIFO
     * 2. addPacket 增加的 timestamp 是递增的, 也就是 FIFO 是按照 timestamp 的顺序
     * 3. 需要一个队列来实现 FIFO, 队列的最大值是 memoryLimit
     * 4. 使用 Map 来存储数据, key 为 destination, value 为 List<int[]> list;
     * 5. 执行 addPacket 时, 检查 Map 中是否已经存在相同 source, destination, timestamp 的数据包,
     * 此时的复杂度是, map 查询 O(1), list 中使用
     * 6. 添加一个额外的内部类, Map 来查询 getCount, TreeSet 来处理 addPacket 和 forwardPacket
     * 7. 移除好像是顺序移除, 所以可以给 Packet 添加一个序号, 然后按照序号进行移除
     * AC: 364ms/182.7MB
     * @param: memoryLimit
     * @return
     * @author marks
     * @CreateDate: 2026/08/31 14:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Router(int memoryLimit) {
        this.memoryLimit = memoryLimit;
        map = new HashMap<>();
        treeSet = new TreeSet<>();
        queue = new ArrayDeque<>();
    }

    public boolean addPacket(int source, int destination, int timestamp) {
        Packet packet = new Packet();
        packet.source = source;
        packet.destination = destination;
        packet.timestamp = timestamp;
        if (!treeSet.contains(packet)) {
            // 先判断是否需要移除最旧的
            if (queue.size() >= memoryLimit) {
                forwardPacket();
            }
            queue.add(packet);
            treeSet.add(packet);
            map.computeIfAbsent(destination, k -> new ArrayList<>()).add(packet);
            return true;
        }
        return false;
    }

    public int[] forwardPacket() {
        if (!queue.isEmpty()) {
            // 获取队列头部的元素
            Packet packet = queue.poll();
            treeSet.remove(packet);
            // 通过二分查找移除 map 中对应的 packet
            List<Packet> packets = map.get(packet.destination);
            int index = binarySearch(packets, packet.timestamp);
            if (index != -1) {
                packets.remove(index);
                if (packets.isEmpty()) {
                    map.remove(packet.destination);
                }
            }
            return new int[]{packet.source, packet.destination, packet.timestamp};
        }
        return new int[0];
    }

    public int getCount(int destination, int startTime, int endTime) {
        if (!map.containsKey(destination)) {
            return 0;
        }
        // 构建二分查找, 找到第一个大于等于 startTime 的位置
        List<Packet> packets = map.get(destination);
        int startIndex = getFirstIndex(packets, startTime);
        int endIndex = getFirstIndex(packets, endTime + 1);
        if (endIndex >= packets.size()) {
            endIndex = packets.size() - 1;
        }
        if (packets.get(endIndex).timestamp > endTime) {
            endIndex--;
        }
        return endIndex - startIndex + 1;
    }
    // 构建二分查找, 找到第一个大于等于 startTime 的位置
    private int getFirstIndex(List<Packet> packets, int startTime) {
        int left = 0;
        int right = packets.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (packets.get(mid).timestamp >= startTime) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


    // 构建二分查找的辅助方法, 找到一个等于 timestamp 的位置
    private int binarySearch(List<Packet> packets, int timestamp) {
        int left = 0;
        int right = packets.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (packets.get(mid).timestamp == timestamp) {
                return mid;
            } else if (packets.get(mid).timestamp < timestamp) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }


    private class Packet implements Comparable<Packet> {
        int source;
        int destination;
        int timestamp;

        @Override
        public int compareTo(Packet other) {
            // 核心逻辑：先按 timestamp 升序排序
            int cmp = Integer.compare(this.timestamp, other.timestamp);

            // 【关键】如果 timestamp 相同，必须继续比较其他字段（如 source 和 destination）
            // 否则，timestamp 相同的不同数据包会被 TreeSet 视为“重复”而丢弃
            if (cmp != 0) {
                return cmp;
            }

            // 如果 timestamp 相同，按 source 排序
            cmp = Integer.compare(this.source, other.source);
            if (cmp != 0) {
                return cmp;
            }

            // 如果 source 也相同，按 destination 排序
            return Integer.compare(this.destination, other.destination);
        }

        // 最佳实践：保持 equals 和 hashCode 与 compareTo 一致
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Packet packet = (Packet) o;
            return source == packet.source && destination == packet.destination && timestamp == packet.timestamp;
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, destination, timestamp);
        }

        @Override
        public String toString() {
            return "Packet{src=" + source + ", dst=" + destination + ", ts=" + timestamp + "}";
        }
    }

}
