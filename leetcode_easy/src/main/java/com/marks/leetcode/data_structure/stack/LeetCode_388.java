package com.marks.leetcode.data_structure.stack;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_388 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/29 15:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_388 {

    /**
     * @Description:
     * 假设有一个同时存储文件和目录的文件系统。下图展示了文件系统的一个示例：
     * 这里将 dir 作为根目录中的唯一目录。
     * dir 包含两个子目录 subdir1 和 subdir2 。subdir1 包含文件 file1.ext 和子目录 subsubdir1；subdir2 包含子目录 subsubdir2，该子目录下包含文件 file2.ext 。
     * 在文本格式中，如下所示(⟶表示制表符)：
     * dir
     * ⟶ subdir1
     * ⟶ ⟶ file1.ext
     * ⟶ ⟶ subsubdir1
     * ⟶ subdir2
     * ⟶ ⟶ subsubdir2
     * ⟶ ⟶ ⟶ file2.ext
     * 如果是代码表示，上面的文件系统可以写为 "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext" 。'\n' 和 '\t' 分别是换行符和制表符。
     *
     * 文件系统中的每个文件和文件夹都有一个唯一的 绝对路径 ，即必须打开才能到达文件/目录所在位置的目录顺序，所有路径用 '/' 连接。
     * 上面例子中，指向 file2.ext 的 绝对路径 是 "dir/subdir2/subsubdir2/file2.ext" 。
     * 每个目录名由字母、数字和/或空格组成，每个文件名遵循 name.extension 的格式，其中 name 和 extension由字母、数字和/或空格组成。
     * 给定一个以上述格式表示文件系统的字符串 input ，返回文件系统中 指向 文件 的 最长绝对路径 的长度 。 如果系统中没有文件，返回 0。
     * @param: input
     * @return int
     * @author marks
     * @CreateDate: 2026/01/29 15:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int lengthLongestPath(String input) {
        int result;
        result = method_01(input);
        return result;
    }

    /**
     * @Description:
     * 1. 查看官解直接开始遍历, int[] level 记录上一个目录的长度
     * AC: 1ms/41.8MB
     * @param: input
     * @return int
     * @author marks
     * @CreateDate: 2026/01/29 15:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String input) {
        int post = 0;
        int n = input.length();
        int ans = 0;
        int[] level = new int[n + 1];
        while (post < n) {
            // 当前文件\文件夹的深度
            int depth = 1;
            while (post < n && input.charAt(post) == '\t') {
                depth++;
                post++;
            }
            int len = 0;
            // 统计当前文件\文件夹的名称的长度
            boolean isFile = false;
            while (post < n && input.charAt(post) != '\n') {
                len++;
                post++;
                // 判断是否是文件
                if (post < n && input.charAt(post) == '.') {
                    isFile = true;
                }
            }
            post++; // 跳过 /n 换行符
            if (depth > 1) {
                len += level[depth - 1] + 1; // 加上上一个目录的长度 + '/'(1) 目录分割符
            }
            if (isFile) {
                ans = Math.max(ans, len);
            } else {
                level[depth] = len;
            }
        }
        return ans;
    }

}
