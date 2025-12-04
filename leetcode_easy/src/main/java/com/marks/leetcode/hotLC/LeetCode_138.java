package com.marks.leetcode.hotLC;

import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_138 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/4 10:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_138 {

    /**
     * @Description:
     * 给你一个长度为 n 的链表，每个节点包含一个额外增加的随机指针 random ，该指针可以指向链表中的任何节点或空节点。
     * 构造这个链表的 深拷贝。
     * 深拷贝应该正好由 n 个 全新 节点组成，其中每个新节点的值都设为其对应的原节点的值。
     * 新节点的 next 指针和 random 指针也都应指向复制链表中的新节点，并使原链表和复制链表中的这些指针能够表示相同的链表状态。
     * 复制链表中的指针都不应指向原链表中的节点 。
     *
     * 例如，如果原链表中有 X 和 Y 两个节点，其中 X.random --> Y 。
     * 那么在复制链表中对应的两个节点 x 和 y ，同样有 x.random --> y 。
     * 返回复制链表的头节点。
     *
     * 用一个由 n 个节点组成的链表来表示输入/输出中的链表。每个节点用一个 [val, random_index] 表示：
     * val：一个表示 Node.val 的整数。
     * random_index：随机指针指向的节点索引（范围从 0 到 n-1）；如果不指向任何节点，则为  null 。
     * 你的代码 只 接受原链表的头节点 head 作为传入参数。
     * @param: head
     * @return com.marks.leetcode.hotLC.LeetCode_138.Node
     * @author marks
     * @CreateDate: 2025/12/04 10:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Node copyRandomList(Node head) {
        Node result;
        result = method_01(head);
        return result;
    }

    /**
     * @Description:
     * 输入：head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
     * 输出：[[7,null],[13,0],[11,4],[10,2],[1,0]]
     * 1. 需要拷贝一份新的链表返回, 先把链表构建起来, 即先把next 处理完成, 并且可以用一个List<Node> 来存储所有的节点
     * 2. 处理random, 由于前一步已经使用了List<Node> 来存储所有的节点, 所以可以直接使用List<Node> 来处理random
     * AC: 0ms/45.72MB
     * @param: head
     * @return com.marks.leetcode.hotLC.LeetCode_138.Node
     * @author marks
     * @CreateDate: 2025/12/04 10:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private Node method_01(Node head) {
        // 构建一个虚拟节点
        Node dummy = new Node(-1);
        List<Node> list = new ArrayList<>();
        // 添加一个Map 用于存储所有的节点
        Map<Node, Integer> map = new HashMap<>(); // 节点及其下表
        Node cur = head;
        Node tail = dummy;
        int index = 0;
        while (cur != null) {
            map.put(cur, index++);
            Node node = new Node(cur.val);
            list.add(node);
            tail.next = node;
            tail = tail.next;
            cur = cur.next;

        }
        // 再次遍历链表, 处理random
        cur = head;
        index = 0;
        while (cur != null) {
            Node randomNode = cur.random;
            if (randomNode != null) {
                int randomIndex = map.get(randomNode);
                Node newNode = list.get(index);
                newNode.random = list.get(randomIndex);
            }
            index++;
            cur = cur.next;
        }

        return dummy.next;
    }

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

}
