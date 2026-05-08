package com.marks.leetcode.array;


import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: FileSystemDuplicateFinder </p>
 * <p>描述:
 * 给你一个目录信息列表 paths ，包括目录路径，以及该目录中的所有文件及其内容，请你按路径返回文件系统中的所有重复文件。答案可按 任意顺序 返回。
 * 一组重复的文件至少包括 两个 具有完全相同内容的文件。
 * 输入 列表中的单个目录信息字符串的格式如下：
 * "root/d1/d2/.../dm f1.txt(f1_content) f2.txt(f2_content) ... fn.txt(fn_content)"
 * 这意味着，在目录 root/d1/d2/.../dm 下，有 n 个文件 ( f1.txt, f2.txt ... fn.txt ) 的内容分别是 ( f1_content, f2_content ... fn_content ) 。注意：n >= 1 且 m >= 0 。如果 m = 0 ，则表示该目录是根目录。
 * 输出 是由 重复文件路径组 构成的列表。其中每个组由所有具有相同内容文件的文件路径组成。文件路径是具有下列格式的字符串：
 * "directory_path/file_name.txt"
 *
 * 进阶：
 *
 * 假设您有一个真正的文件系统，您将如何搜索文件？广度搜索还是宽度搜索？
 * 如果文件内容非常大（GB级别），您将如何修改您的解决方案？
 * 如果每次只能读取 1 kb 的文件，您将如何修改解决方案？
 * 修改后的解决方案的时间复杂度是多少？其中最耗时的部分和消耗内存的部分是什么？如何优化？
 * 如何确保您发现的重复文件不是误报？
 *
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/7 11:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */


public class FileSystemDuplicateFinder {
    private static final int BUFFER_SIZE = 1024; // 1KB 缓冲区，可按需调整
    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * 扫描指定根目录，返回所有重复文件组（每组至少两个文件）
     * @param rootPaths 要扫描的根目录列表
     * @param confirmWithByteComparison 是否在哈希匹配后进行字节级确认
     * @return 重复文件路径组列表
     */
    public List<List<Path>> findDuplicates(List<Path> rootPaths, boolean confirmWithByteComparison)
            throws IOException, NoSuchAlgorithmException {
        // 第一步：收集所有常规文件，并按文件大小分组
        Map<Long, List<Path>> sizeToFiles = new HashMap<>();
        for (Path root : rootPaths) {
            collectFiles(root, sizeToFiles);
        }

        // 第二步：对每个大小组内文件计算哈希，找出内容重复的组
        List<List<Path>> duplicates = new ArrayList<>();
        for (Map.Entry<Long, List<Path>> entry : sizeToFiles.entrySet()) {
            List<Path> sameSizeFiles = entry.getValue();
            if (sameSizeFiles.size() < 2) continue; // 只有一个文件，不可能重复

            // 按哈希值分组
            Map<String, List<Path>> hashToPaths = new HashMap<>();
            for (Path file : sameSizeFiles) {
                String hash = computeFileHash(file);
                hashToPaths.computeIfAbsent(hash, k -> new ArrayList<>()).add(file);
            }

            // 筛选出重复组
            for (List<Path> group : hashToPaths.values()) {
                if (group.size() > 1) {
                    if (confirmWithByteComparison) {
                        // 二次确认：对组内文件进行逐字节比较
                        List<List<Path>> confirmedGroups = confirmDuplicates(group);
                        duplicates.addAll(confirmedGroups);
                    } else {
                        duplicates.add(group);
                    }
                }
            }
        }
        return duplicates;
    }

    /**
     * 使用 BFS 收集目录下所有常规文件，并按大小分组
     */
    private void collectFiles(Path root, Map<Long, List<Path>> sizeToFiles) throws IOException {
        Queue<Path> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Path current = queue.poll();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(current)) {
                for (Path entry : stream) {
                    if (Files.isDirectory(entry)) {
                        queue.add(entry);
                    } else if (Files.isRegularFile(entry)) {
                        long size = Files.size(entry);
                        sizeToFiles.computeIfAbsent(size, k -> new ArrayList<>()).add(entry);
                    }
                }
            } catch (AccessDeniedException e) {
                // 忽略无权限访问的目录
                System.err.println("无法访问: " + current);
            }
        }
    }

    /**
     * 分块计算文件的 SHA-256 哈希值（支持大文件，每次读取 BUFFER_SIZE 字节）
     */
    private String computeFileHash(Path file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
        try (InputStream is = Files.newInputStream(file)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }
        byte[] hashBytes = digest.digest();
        // 转换为十六进制字符串
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * 对哈希值相同的文件组进行逐字节确认，避免哈希碰撞误报
     * 返回真正重复的文件组（可能因碰撞拆分为多个子组）
     */
    private List<List<Path>> confirmDuplicates(List<Path> group) throws IOException {
        List<List<Path>> confirmed = new ArrayList<>();
        boolean[] visited = new boolean[group.size()];
        for (int i = 0; i < group.size(); i++) {
            if (visited[i]) continue;
            List<Path> currentGroup = new ArrayList<>();
            currentGroup.add(group.get(i));
            visited[i] = true;
            for (int j = i + 1; j < group.size(); j++) {
                if (!visited[j] && filesIdentical(group.get(i), group.get(j))) {
                    currentGroup.add(group.get(j));
                    visited[j] = true;
                }
            }
            if (currentGroup.size() > 1) {
                confirmed.add(currentGroup);
            }
        }
        return confirmed;
    }

    /**
     * 逐字节比较两个文件内容是否完全相同
     */
    private boolean filesIdentical(Path file1, Path file2) throws IOException {
        try (InputStream is1 = Files.newInputStream(file1);
             InputStream is2 = Files.newInputStream(file2)) {
            byte[] buf1 = new byte[BUFFER_SIZE];
            byte[] buf2 = new byte[BUFFER_SIZE];
            while (true) {
                int read1 = is1.read(buf1);
                int read2 = is2.read(buf2);
                if (read1 != read2) return false;
                if (read1 == -1) break; // 同时到达文件末尾
                if (!Arrays.equals(buf1, 0, read1, buf2, 0, read2)) {
                    return false;
                }
            }
            return true;
        }
    }

    // 示例用法
    public static void main(String[] args) throws Exception {
        FileSystemDuplicateFinder finder = new FileSystemDuplicateFinder();
        List<Path> roots = Arrays.asList(Paths.get("/path/to/scan1"), Paths.get("/path/to/scan2"));
        List<List<Path>> duplicates = finder.findDuplicates(roots, true);
        for (List<Path> group : duplicates) {
            System.out.println("重复文件组:");
            group.forEach(System.out::println);
            System.out.println("---");
        }
    }
}

