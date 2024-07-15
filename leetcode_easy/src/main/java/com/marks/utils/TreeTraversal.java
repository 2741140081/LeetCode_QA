package com.marks.utils;

/**
 * 树的遍历分为四种：先序遍历，中序遍历，后序遍历，层序遍历。
 *                A
 *         B            C
 *      D    E        F     G
 *             H
 *
 * 先序遍历：先序遍历的遍历顺序是 根→左→右的顺序打印，我们先打印根节点，在打印左孩子，最后打印右孩子，
 * 如上图，我们先打印A，然后打印B，发现B也有左孩子，之后我们以B为根节点再进行一次跟左右遍历，打印D，
 * 之后我们准备打印B的右孩子就是E，发现E有右孩子，所以我们这边又以E作为根节点，进行跟左右操作，以此类推，得到最后的结果是：ABDEHCFG。
 *
 * 中序遍历：先序遍历的遍历顺序是 左→根→右的顺序打印，我们一开始准备打印A的左孩子B，发现B也有左孩子
 * 然后以B为根节点进行左→根→右进行打印，所以我们一开始打印的是D这个节点，中序遍历和先序遍历的思维差不多，
 * 无非就是打印顺序的调整。最后的结果为：DBEHAFCG。
 *
 * 后序遍历：先序遍历的遍历顺序是 左→右→根的顺序打印，跟前面两种遍历方式类似，就是打印的顺序不同，
 * 我们应该先遍历完左右孩子再进行根节点的打印。最后的结果是：DHEBFGCA。
 *
 * 层序遍历：层序遍历就比较简单，层序遍历就是按照各层顺序打印结果，如上图的层序遍历就是：ABCDEFGH。
 *
 * 遍历我们一般用递归方式，以下是递归方式实现的前中后序的遍历代码：
 */
public class TreeTraversal {
    public static void main(String[] args) {
        int[] nums = {5,4,11,7,2,0,8,13,4,0,1};
    }
//    构建一颗二叉树
    public TreeNode buildTree(int[] nums) {

        return null;

    }

//    前序遍历：根→左→右
    public void preOrderTraversal(TreeNode root){
        if (root == null) {
            return;
        }else {
            System.out.print(root.val+"->");
            preOrderTraversal(root.left);
            preOrderTraversal(root.right);
        }
    }

//    中序遍历: 左→根→右
    public void inOrderTraversal(TreeNode root){
        if (root==null) {
            return;
        }else {
            inOrderTraversal(root.left);
            System.out.print(root.val+"->");
            inOrderTraversal(root.right);
        }
    }

//    后序遍历: 左→右→根
    public void poseOrderTraversal(TreeNode root){
        if (root==null) {
            return;
        }else {
            inOrderTraversal(root.left);
            inOrderTraversal(root.right);
            System.out.print(root.val+"->");
        }
    }
}
