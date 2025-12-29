package com.marks.leetcode.daily_question;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_756 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/29 10:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_756 {


    /**
     * @Description:
     * 你正在把积木堆成金字塔。每个块都有一个颜色，用一个字母表示。每一行的块比它下面的行 少一个块 ，并且居中。
     * 为了使金字塔美观，只有特定的 三角形图案 是允许的。一个三角形的图案由 两个块 和叠在上面的 单个块 组成。
     * 模式是以三个字母字符串的列表形式 allowed 给出的，其中模式的前两个字符分别表示左右底部块，第三个字符表示顶部块。
     *
     * 例如，"ABC" 表示一个三角形图案，其中一个 “C” 块堆叠在一个 'A' 块(左)和一个 'B' 块(右)之上。请注意，这与 "BAC" 不同，"B" 在左下角，"A" 在右下角。
     * 你从作为单个字符串给出的底部的一排积木 bottom 开始，必须 将其作为金字塔的底部。
     * 在给定 bottom 和 allowed 的情况下，如果你能一直构建到金字塔顶部，使金字塔中的 每个三角形图案 都是在 allowed 中的，则返回 true ，否则返回 false 。
     * @param: bottom
     * @param: allowed
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/29 10:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        boolean result;
        result = method_01(bottom, allowed);
        return result;
    }

    /**
     * @Description:
     * 1. 这种字符串类型, 并且需要顺序, 想到的处理办法是字典树, 将allowed中的字符串, 构建成字典树
     * 2. 然后通过广度优先搜索
     * 3. 由于无法通过前2个字符确定整个字符, 所以第三个字符存在多种可能性, 需要回溯判断当前是否可以
     * AC: 1274ms/47.44MB
     * @param: bottom
     * @param: allowed
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/29 10:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String bottom, List<String> allowed) {
        Node root = new Node();
        for(String s : allowed){
            root.insert(s);
        }
        // 回溯
        return backTracking(bottom, root);
    }

    private boolean backTracking(String next, Node root) {
        // 如果队列不为空, 并且size == 1, 并且 queue.peck().length() == 1, 说明已经到达顶部, 则返回true
        if(next.length() == 1){
            return true;
        }
        boolean ans = false;
        int n = next.length();
        List<Character>[] list = new List[n - 1]; // 记录下一个位置的可选字符
        for (int i = 0; i < n - 1; i++) {
            list[i] = new ArrayList<>();
        }
        char[] array = next.toCharArray();
        for (int i = 1; i < n; i++) {
            Node curr = root;
            if (curr.children[array[i - 1] - 'A'] == null) {
                return false;
            }
            curr = curr.children[array[i - 1] - 'A'];
            curr = curr.children[array[i] - 'A'];
            if (curr != null) {
                for (int j = 0; j < 6; j++) {
                    if (curr.children[j] != null) {
                        list[i - 1].add((char)('A' + j));
                    }
                }
                if (list[i - 1].isEmpty()) {
                    // 提前剪枝
                    return false;
                }
            } else {
                // 提前剪枝
                return false;
            }
        }
        Set<String> nextStr = new HashSet<>();
        backtrack(list, 0, new StringBuilder(), nextStr);
        for (String t : nextStr) {
            if (backTracking(t, root)) {
                ans = true;
                break;
            }
        }
        return ans;
    }

    private void backtrack(List<Character>[] list, int index, StringBuilder curr, Set<String> result) {
        if (index == list.length) {
            result.add(curr.toString());
            return;
        }
        for (Character c : list[index]) {
            curr.append(c);
            backtrack(list, index + 1, curr, result);
            curr.deleteCharAt(curr.length() - 1);
        }
    }

    private class Node{
        Node[] children;
        boolean isEnd;
        public Node(){
            children = new Node[6];
            isEnd = false;
        }

        public void insert(String s){
            Node cur = this;
            for(int i = 0; i < s.length(); i++){
                int t = s.charAt(i) - 'A';
                if(cur.children[t] == null){
                    cur.children[t] = new Node();
                }
                cur = cur.children[t];
            }
            cur.isEnd = true;
        }
    }

}
