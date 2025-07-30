package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/18 16:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_26 {

    private int left = 0;
    private int right = 0;

    /**
     * @Description:
     * 小扣参加的秋日市集景区共有 N 个景点，景点编号为 1~N。景点内设有 N−1 条双向道路，使所有景点形成了一个二叉树结构，根结点记为 root，景点编号即为节点值。
     *
     * 由于秋日市集景区的结构特殊，游客很容易迷路，主办方决定在景区的若干个景点设置导航装置，按照所在景点编号升序排列后定义装置编号为 1 ~ M。
     * 导航装置向游客发送数据，数据内容为列表 [游客与装置 1 的相对距离,游客与装置 2 的相对距离,...,游客与装置 M 的相对距离]。
     * 由于游客根据导航装置发送的信息来确认位置，因此主办方需保证游客在每个景点接收的数据信息皆不相同。
     * 请返回主办方最少需要设置多少个导航装置。
     *
     * tips:
     * 2 <= N <= 50000
     * 二叉树的非空节点值为 1~N 的一个排列
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/7/18 16:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int navigation(TreeNode root) {
        int result;
        result = method_01(root);
        return result;
    }

    private int getCount(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftCount = getCount(root.left);
        int rightCount = getCount(root.right);

        return leftCount + rightCount + 1;
    }

    /**
     * @Description:
     * E1:输入：root = [1,2,null,3,4]
     * 输出：2
     * 解释：在景点 1、3 或景点 1、4 或景点 3、4 设置导航装置。
     *
     * 基本理解了, 游客在每个景点都会收到 导航装置发过来的信息, 即游客有个手机, 可以接收这些信息, 我们要做的就是保证游客接收的信息皆不相同
     * 1. 观看案例E1, 假设装置安装在 1, 3 处, 当游客处于 1: [0, 2]; 处于 2:[1, 1]; 处于 3:[2, 0]; 处于 4: [2, 2]
     * 2. 继续分析案例E1, 我们从每一个节点出发, 因为是双向路, 所以, 相当于任意节点都可以看做是根节点
     * 1: [0] [1] [2] [2]; 2:[1] [0] [1] [1]; 3:[2] [1] [0] [2]; 4: [2] [1] [2] [0]
     *
     *
     * 另外一种思路是, 通过一个点确定一条最长的路径, 例如1 -> 2 -> 3 -> 5 确定这条路径, 然后在通过剩余的点确定剩余的最长路径 4 -> 2 -> 3 -> 5
     *
     * 还有一种思路是: 二叉树的分叉越多, 所需要的节点也就越多，对于不是根节点的父节点, 将其子树的分叉点进行相加, 得到left 和 right, 最小所需节点, 通过在根节点时比较Math.max(left, right) 的较大值即为所需最小节点
     * 以上思路待验证, 查看图之后, 感觉是左右子树的叶子数, 其中最大的即为所需最小的装置数量
     *
     * 再次经过验证, 假设二叉树是满二叉树, 那么所需最小装置数 = left / 2 + right / 2, 其中 left / right 分别是左右子树的叶子节点, 待验证
     * 也不对, 修改感觉叶子是满二叉树才使得left++ or right++, 在将 left + right 即为最小装置数.
     *
     * @param root 
     * @return int
     * @author marks
     * @CreateDate: 2025/7/18 16:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        boolean flag = true;
        dfs(root.left, flag);
        dfs(root.right, !flag);
        int ans = this.left + this.right;
        return ans == 0 ? 1 : ans;
    }

    private void dfs(TreeNode root, boolean flag) {
        if (root == null) {
            return;
        }

        if (root.left != null && root.right != null) {
            boolean leftLeave = checkIsLeave(root.left);
            boolean rightLeave = checkIsLeave(root.right);
            if (leftLeave && rightLeave) {
                if (flag) {
                    left++;
                } else {
                    right++;
                }
            }
        }
        dfs(root.left, flag);
        dfs(root.right, flag);
    }

    private boolean checkIsLeave(TreeNode root) {
        if (root.left == null && root.right == null) {
            return true;
        }
        return false;
    }
}
