package com.marks.leetcode.data_structure.binary_indexed_tree;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/18 10:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3072 {
    
    /**
     * @Description:
     * 给你一个下标从 1 开始、长度为 n 的整数数组 nums 。
     *
     * 现定义函数 greaterCount ，使得 greaterCount(arr, val) 返回数组 arr 中 严格大于 val 的元素数量。
     *
     * 你需要使用 n 次操作，将 nums 的所有元素分配到两个数组 arr1 和 arr2 中。在第一次操作中，将 nums[1] 追加到 arr1 。在第二次操作中，将 nums[2] 追加到 arr2 。
     * 之后，在第 i 次操作中：
     *
     * 如果 greaterCount(arr1, nums[i]) > greaterCount(arr2, nums[i]) ，将 nums[i] 追加到 arr1 。
     * 如果 greaterCount(arr1, nums[i]) < greaterCount(arr2, nums[i]) ，将 nums[i] 追加到 arr2 。
     * 如果 greaterCount(arr1, nums[i]) == greaterCount(arr2, nums[i]) ，将 nums[i] 追加到元素数量较少的数组中。
     * 如果仍然相等，那么将 nums[i] 追加到 arr1 。
     * 连接数组 arr1 和 arr2 形成数组 result 。例如，如果 arr1 == [1,2,3] 且 arr2 == [4,5,6] ，那么 result = [1,2,3,4,5,6] 。
     *
     * 返回整数数组 result 。
     *
     * tips:
     * 3 <= n <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param nums
     * @return int[]
     * @author marks
     * @CreateDate: 2025/3/18 10:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] resultArray(int[] nums) {
        int[] result;
//        result = method_01(nums);
//        result = method_02(nums);
        result = method_03(nums);
        return result;
    }

    /**
     * @Description:
     * 查看官解: 树状数组 + 模拟
     * 如何解决?
     *
     * @param nums
     * @return int[]
     * @author marks
     * @CreateDate: 2025/3/19 10:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_03(int[] nums) {
        int n = nums.length;
        int[] sortedNums = Arrays.copyOf(nums, n);
        Arrays.sort(sortedNums);

        Map<Integer, Integer> index = new HashMap<>();
        for (int i = 0; i < n; i++) {
            index.put(sortedNums[i], i + 1);
        }

        List<Integer> arr1 = new ArrayList<>(List.of(nums[0]));
        List<Integer> arr2 = new ArrayList<>(List.of(nums[1]));
        BinaryIndexedTree tree1 = new BinaryIndexedTree(n);
        BinaryIndexedTree tree2 = new BinaryIndexedTree(n);
        tree1.add(index.get(nums[0]));
        tree2.add(index.get(nums[1]));

        for (int i = 2; i < n; i++) {
            int count1 = arr1.size() - tree1.get(index.get(nums[i]));
            int count2 = arr2.size() - tree2.get(index.get(nums[i]));
            if (count1 > count2 || (count1 == count2 && arr1.size() <= arr2.size())) {
                arr1.add(nums[i]);
                tree1.add(index.get(nums[i]));
            } else {
                arr2.add(nums[i]);
                tree2.add(index.get(nums[i]));
            }
        }

        int i = 0;
        for (int a: arr1) {
            nums[i++] = a;
        }
        for (int a: arr2) {
            nums[i++] = a;
        }
        return nums;
    }

    private static class BinaryIndexedTree {
        private int[] tree;

        public BinaryIndexedTree(int n) {
            tree = new int[n + 1];
        }

        public void add(int i) {
            while (i < tree.length) {
                tree[i]++;
                i += i & -i;
            }
        }

        public int get(int i) {
            int sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= i & -i;
            }
            return sum;
        }
    }

    /**
     * @Description:
     * 1. 使用两个 List 来存储数据 List<int[2]>, 其中 val = int[0], index = int[1];
     * 2. 并且使用二分法分别向List中插入元素 list.add(Index, map)
     *
     * AC: 777ms/66.93MB
     * @param nums
     * @return int[]
     * @author marks
     * @CreateDate: 2025/3/18 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int[] nums) {
        List<int[]> list_1 = new ArrayList<>();
        List<int[]> list_2 = new ArrayList<>();
        int n = nums.length;
        int first_index = 0;
        int second_index = 0;

        list_1.add(new int[]{nums[0], first_index++});
        list_2.add(new int[]{nums[1], second_index++});

        for (int i = 2; i < n; i++) {
            int val = nums[i];
            int index_1 = greaterCount(list_1, val);
            int index_2 = greaterCount(list_2, val);
            int count_1 = (index_1 == -1 ? 0 : list_1.size() - index_1);
            int count_2 = (index_2 == -1 ? 0 : list_2.size() - index_2);
            if (count_1 > count_2) {
                list_1.add(index_1, new int[]{val, first_index++});
            } else if (count_1 < count_2) {
                list_2.add(index_2, new int[]{val, second_index++});
            } else {
                if (first_index > second_index) {
                    if (index_2 != -1) {
                        list_2.add(index_2, new int[]{val, second_index++});
                    } else {
                        list_2.add(new int[]{val, second_index++});
                    }
                } else {
                    if (index_1 != -1) {
                        list_1.add(index_1, new int[]{val, first_index++});
                    } else {
                        list_1.add(new int[]{val, first_index++});
                    }
                }
            }
        }

        int[] ans = new int[n];
        for (int[] ints : list_1) {
            int value = ints[0];
            int index = ints[1];
            ans[index] = value;
        }
        for (int[] ints : list_2) {
            int value = ints[0];
            int index = ints[1];
            ans[index + first_index] = value;
        }

        return ans;
    }

    /**
     * @Description:
     * [1,2,3,4,5], val = 3
     * left = 0, right = 4
     * mid = 2, list.get(2) = 3 <= 3, left = 3, right = 4
     * mid = 3, list.get(3) = 4 > 3, left = 3, right = 3
     *
     * mid = 3, list.get(3) = 4 <= 3, left = 4, right = 4
     * @param list
     * @param val
     * @return int
     * @author marks
     * @CreateDate: 2025/3/19 9:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int greaterCount(List<int[]> list, int val) {
        int left = 0;
        int right = list.size();
        int firstGreaterIndex = -1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (list.get(mid)[0] <= val) {
                left = mid + 1;
            } else {
                firstGreaterIndex = mid;
                right = mid - 1;
            }
        }
        return firstGreaterIndex;
    }

    /**
     * @Description:
     * 先按照一般的思路看看
     * 1. 构建2个list list_1 和 list_2
     * 2. nums[0], nums[1] 分别添加到list_1 和list_2中
     * 3. 此时 i = 2, 即 nums[2],
     * 4. 看到题目后面的离散化以及题目的数据范围
     * 0, 1, 2, index = 3, 0, 1, 2
     *
     * 超时!!! 780/784
     * @param nums
     * @return int[]
     * @author marks
     * @CreateDate: 2025/3/18 11:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums) {
        int n = nums.length;
        TreeMap<Integer, List<Integer>> arr_1_map = new TreeMap<>(Collections.reverseOrder());
        TreeMap<Integer, List<Integer>> arr_2_map = new TreeMap<>(Collections.reverseOrder());
        int index_first = 0; // 1. 作为下标, 2. 记录arr_1_map的数量
        int index_second = 0;

        addData(arr_1_map, nums[0], index_first++);
        addData(arr_2_map, nums[1], index_second++);

        for (int i = 2; i < n; i++) {
            int val = nums[i];
            int count_1 = getCount(val, arr_1_map);
            int count_2 = getCount(val, arr_2_map);

            if (count_1 > count_2) {
                addData(arr_1_map, val, index_first++);
            } else if (count_1 < count_2) {
                addData(arr_2_map, val, index_second++);
            } else {
                if (index_first > index_second) {
                    addData(arr_2_map, val, index_second++);
                } else {
                    addData(arr_1_map, val, index_first++);
                }
            }
        }


        int[] ans = new int[n];
        for (Map.Entry<Integer, List<Integer>> entry : arr_1_map.entrySet()) {
            int val = entry.getKey();
            List<Integer> index = entry.getValue();
            for (int i : index) {
                ans[i] = val;
            }
        }
        for (Map.Entry<Integer, List<Integer>> entry : arr_2_map.entrySet()) {
            int val = entry.getKey();
            List<Integer> index = entry.getValue();
            for (int i : index) {
                ans[i + index_first] = val;
            }
        }
        return ans;
    }

    private void addData(TreeMap<Integer, List<Integer>> map, int val, int index) {
        map.computeIfAbsent(val, k -> new ArrayList<>()).add(index);
    }

    private int getCount(int val, TreeMap<Integer, List<Integer>> map) {
        NavigableMap<Integer, List<Integer>> descendingMap = map.descendingMap();
        NavigableMap<Integer, List<Integer>> tailMap = descendingMap.tailMap(val + 1, true);
        int count = 0;
        for (Map.Entry<Integer, List<Integer>> entry : tailMap.entrySet()) {
            count += entry.getValue().size();
        }
        return count;
    }
}
