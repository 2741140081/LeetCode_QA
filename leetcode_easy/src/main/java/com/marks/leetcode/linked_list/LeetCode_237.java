package com.marks.leetcode.linked_list;

import com.marks.utils.ListNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/11 11:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_237 {
    
    /**
     * @Description:
     * 有一个单链表的 head，我们想删除它其中的一个节点 node。
     *
     * 给你一个需要删除的节点 node 。你将 无法访问 第一个节点  head。
     *
     * 链表的所有值都是 唯一的，并且保证给定的节点 node 不是链表中的最后一个节点。
     *
     * 删除给定的节点。注意，删除节点并不是指从内存中删除它。这里的意思是：
     *
     * 给定节点的值不应该存在于链表中。
     * 链表中的节点数应该减少 1。
     * node 前面的所有值顺序相同。
     * node 后面的所有值顺序相同。
     * 自定义测试：
     *
     * 对于输入，你应该提供整个链表 head 和要给出的节点 node。node 不应该是链表的最后一个节点，而应该是链表中的一个实际节点。
     * 我们将构建链表，并将节点传递给你的函数。
     * 输出将是调用你函数后的整个链表。
     * @param node
     * @return void
     * @author marks
     * @CreateDate: 2025/4/11 11:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void deleteNode(ListNode node) {
        method_01(node);
    }

    /**
     * @Description:
     * 删除node节点
     * 1. 知道头结点 head, pre.next = curr.next; 删除 curr 节点
     * 2. 不知道头结点 head, 将 curr 后的节点值 整体前移
     *      例如: 1,3,4,5; 我们要删除值为 1 的节点, 将 3 赋值到 节点 0 处
     *      3, 3, 4, 5
     *      3, 4, 4, 5
     *      3, 4, 5, 5
     *      3, 4, 5, null
     * 上面虽然可以, 但是有更好的方法
     * 当我们复制 节点1 的值时
     * 3, 3, 4, 5; 此时我们可以删除 节点1, 因为它的值已经保存到节点 0
     *
     * AC: 0ms/43.13MB
     * @param node 
     * @return void
     * @author marks
     * @CreateDate: 2025/4/11 11:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_01(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
