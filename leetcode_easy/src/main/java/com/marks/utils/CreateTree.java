package com.marks.utils;

import java.util.LinkedList;
import java.util.Queue;

public class CreateTree {
    public static void main(String[] args) {
        Integer[] nums={5,4,8,11,null,13,4,7,2,null,null,null,1};
        TreeNode root=new CreateTree().createTree(nums);
        new CreateTree().printTree(root,"");

    }
    public TreeNode createTree(Integer []nums){
        TreeNode root=null;
        TreeNode point;
        Queue<TreeNode> queue = new LinkedList<>();
        if(nums.length>=1){
            root=new TreeNode(nums[0]);
            queue.add(root);
        }
        int i=1;
        while (i<nums.length){
            point=queue.poll();
            if(null!=nums[i]){
                point.left=new TreeNode(nums[i]);
                queue.add(point.left);
            }
            i++;
            if(i>=nums.length){
                break;
            }
            if(null!=nums[i]){
                point.right=new TreeNode(nums[i]);
                queue.add(point.right);
            }
            i++;
        }
        return root;
    }
    public void printTree(TreeNode treeNode, String str){
        if(null==treeNode){
            return;
        }else{
            printTree(treeNode.left,str+"**");
            System.out.println(str+treeNode.val);
            printTree(treeNode.right,str+"**");
        }
    }
}
