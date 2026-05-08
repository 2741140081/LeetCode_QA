package com.marks.leetcode.array;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_609 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/7 10:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_609 {

    /**
     * @Description:
     * 给你一个目录信息列表 paths ，包括目录路径，以及该目录中的所有文件及其内容，请你按路径返回文件系统中的所有重复文件。答案可按 任意顺序 返回。
     * 一组重复的文件至少包括 两个 具有完全相同内容的文件。
     * 输入 列表中的单个目录信息字符串的格式如下：
     * "root/d1/d2/.../dm f1.txt(f1_content) f2.txt(f2_content) ... fn.txt(fn_content)"
     * 这意味着，在目录 root/d1/d2/.../dm 下，有 n 个文件 ( f1.txt, f2.txt ... fn.txt ) 的内容分别是 ( f1_content, f2_content ... fn_content ) 。注意：n >= 1 且 m >= 0 。如果 m = 0 ，则表示该目录是根目录。
     * 输出 是由 重复文件路径组 构成的列表。其中每个组由所有具有相同内容文件的文件路径组成。文件路径是具有下列格式的字符串：
     * "directory_path/file_name.txt"
     *
     * tips:
     * 1 <= paths.length <= 2 * 10^4
     * 1 <= paths[i].length <= 3000
     * 1 <= sum(paths[i].length) <= 5 * 10^5
     * paths[i] 由英文字母、数字、字符 '/'、'.'、'('、')' 和 ' ' 组成
     * 你可以假设在同一目录中没有任何文件或目录共享相同的名称。
     * 你可以假设每个给定的目录信息代表一个唯一的目录。目录路径和文件信息用单个空格分隔。
     * @param: paths
     * @return java.util.List<java.util.List<java.lang.String>>
     * @author marks
     * @CreateDate: 2026/05/07 10:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<String>> findDuplicate(String[] paths) {
        List<List<String>> result;
        result = method_01(paths);
        return result;
    }

    /**
     * @Description:
     * 1. 对于每一个path, 使用空格拆分, path[0]为目录路径, path[1:]为文件信息
     * 2. 遍历path[1:], 使用括号拆分, path[1:][i][0]为文件名, path[1:][i][1]为文件内容
     * 3. 使用map 存储文件内容, key为文件内容, value为文件路径, Map<String, List<String>> map;
     * AC: 20ms/59.52MB
     * @param: paths
     * @return java.util.List<java.util.List<java.lang.String>>
     * @author marks
     * @CreateDate: 2026/05/07 10:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<String>> method_01(String[] paths) {
        Map<String, List<String>> map = new HashMap<>();
        for (String path : paths) {
            String[] pathInfo = path.split(" ");
            if (pathInfo.length < 2) {
                continue;
            }
            String baseDir = pathInfo[0];
            for (int i = 1; i < pathInfo.length; i++) {
                // get file name by sub string
                String fileName = pathInfo[i].substring(0, pathInfo[i].indexOf("("));
                // get file content by sub string
                String fileContent = pathInfo[i].substring(pathInfo[i].indexOf("(") + 1, pathInfo[i].length() - 1);
                // add file path to map
                map.putIfAbsent(fileContent, new ArrayList<>());
                map.get(fileContent).add(baseDir + File.separator + fileName);
            }
        }
        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (entry.getValue().size() > 1) {
                result.add(entry.getValue());
            }
        }

        return result;
    }

}
