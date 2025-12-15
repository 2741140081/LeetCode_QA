package com.marks.leetcode.graph_theory;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_433 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/12 17:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_433 {

    /**
     * @Description:
     * 基因序列可以表示为一条由 8 个字符组成的字符串，其中每个字符都是 'A'、'C'、'G' 和 'T' 之一。
     * 假设我们需要调查从基因序列 start 变为 end 所发生的基因变化。
     * 一次基因变化就意味着这个基因序列中的一个字符发生了变化。
     * 例如，"AACCGGTT" --> "AACCGGTA" 就是一次基因变化。
     * 另有一个基因库 bank 记录了所有有效的基因变化，只有基因库中的基因才是有效的基因序列。
     * （变化后的基因必须位于基因库 bank 中）
     *
     * 给你两个基因序列 start 和 end ，以及一个基因库 bank ，请你找出并返回能够使 start 变化为 end 所需的最少变化次数。如果无法完成此基因变化，返回 -1 。
     * 注意：起始基因序列 start 默认是有效的，但是它并不一定会出现在基因库中。
     *
     * tips:
     * start.length == 8
     * end.length == 8
     * 0 <= bank.length <= 10
     * bank[i].length == 8
     * start、end 和 bank[i] 仅由字符 ['A', 'C', 'G', 'T'] 组成
     * @param: startGene
     * @param: endGene
     * @param: bank
     * @return int
     * @author marks
     * @CreateDate: 2025/12/12 17:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minMutation(String startGene, String endGene, String[] bank) {
        int result;
        result = method_01(startGene, endGene, bank);
        return result;
    }

    /**
     * @Description:
     * 输入：start = "AAAAACCC", end = "AACCCCCC", bank = ["AAAACCCC","AAACCCCC","AACCCCCC"]
     * 输出：3
     * 1. 广度优先搜索, 从 startGene 开始, visited[] 存储是否访问过, 遍历bank[], 获取下一个与前一个节点仅有一个字符不同的节点, 添加到队列中
     * AC: 0ms/42.10MB
     * @param: startGene
     * @param: endGene
     * @param: bank
     * @return int
     * @author marks
     * @CreateDate: 2025/12/12 17:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String startGene, String endGene, String[] bank) {
        int ans = 0; // 记录步数
        int n = bank.length;
        boolean[] visited = new boolean[n];
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(startGene);
        // 判断bank中是否有startGene
        for (int i = 0; i < n; i++) {
            if (bank[i].equals(startGene)) {
                visited[i] = true;
            }
        }
        while (!queue.isEmpty()) {
            int size = queue.size();
            ans++;
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                for (int j = 0; j < n; j++) {
                    if (!visited[j] && isNext(curr, bank[j])) {
                        if (bank[j].equals(endGene)) {
                            return ans;
                        }
                        queue.offer(bank[j]);
                        visited[j] = true;
                    }
                }
            }
        }

        return -1;
    }

    private boolean isNext(String curr, String next) {
        int count = 0;
        int n = curr.length();
        for (int i = 0; i < n; i++) {
            if (curr.charAt(i) != next.charAt(i)) {
                count++;
            }
        }
        return count == 1;
    }

}
