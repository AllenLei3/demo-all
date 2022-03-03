package org.xl.algorithm.search;

/**
 * 二分查找算法
 *
 * @author xulei
 */
public class BinarySearch {

    /**
     * 二分查找的非递归实现，假设array数组有序，且不包含重复元素
     */
    public static int search1(int[] array, int value) {
        int min = 0;
        int max = array.length - 1;
        while (min <= max) {
            int middle = (min + max) / 2;
            if (array[middle] == value) {
                return middle;
            }
            if (array[middle] > value) {
                // 目标数据在中间位的左侧，所以max索引减1
                max = middle - 1;
            } else {
                // 目标数据在中间位的右侧，所以min索引加1
                min = middle + 1;
            }
        }
        return -1;
    }

    /**
     * 二分查找的递归实现，假设array数组有序，且不包含重复元素
     */
    public static int search2(int[] array, int value) {
        return searchInternally(array, 0, array.length - 1, value);
    }

    private static int searchInternally(int[] array, int min, int max, int value) {
        if (min > max) {
            return -1;
        }
        int middle = (min + max) / 2;
        if (array[middle] == value) {
            return middle;
        }
        if (array[middle] > value) {
            return searchInternally(array, min, middle - 1, value);
        } else {
            return searchInternally(array, middle + 1, max, value);
        }
    }

    /**
     * 查找第一个值等于给定值的元素，array有序且包含重复元素
     */
    public static int search3(int[] array, int value) {
        int min = 0;
        int max = array.length - 1;
        while (min <= max) {
            int middle = (min + max) / 2;
            if (array[middle] > value) {
                // 目标数据在中间位的左侧，所以max索引减1
                max = middle - 1;
            } else if (array[middle] < value) {
                // 目标数据在中间位的右侧，所以min索引加1
                min = middle + 1;
            } else {
                // 如果命中，则判断它的前一个元素是否也命中，middle为0则表示前面没数据了
                if (middle == 0 || array[middle - 1] != value) {
                    return middle;
                } else {
                    max = middle - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 查找最后一个值等于给定值的元素，array有序且包含重复元素
     */
    public static int search4(int[] array, int value) {
        int min = 0;
        int max = array.length - 1;
        while (min <= max) {
            int middle = (min + max) / 2;
            if (array[middle] > value) {
                // 目标数据在中间位的左侧，所以max索引减1
                max = middle - 1;
            } else if (array[middle] < value) {
                // 目标数据在中间位的右侧，所以min索引加1
                min = middle + 1;
            } else {
                // 如果命中，则判断它的后一个元素是否也命中，middle为max则表示前面没数据了
                if (middle == max || array[middle + 1] != value) {
                    return middle;
                } else {
                    min = middle + 1;
                }
            }
        }
        return -1;
    }

    /**
     * 查找第一个大于等于给定值的元素，array有序且包含重复元素
     */
    public static int search5(int[] array, int value) {
        int min = 0;
        int max = array.length - 1;
        while (min <= max) {
            int middle = (min + max) / 2;
            // 如果大于等于给定值，则判断它的前一个元素是否也大于等于给定值
            if (array[middle] >= value) {
                if (middle == 0 || array[middle - 1] < value) {
                    return middle;
                } else {
                    max = middle - 1;
                }
            } else {
                min = middle + 1;
            }
        }
        return -1;
    }

    /**
     * 查找第一个小于等于给定值的元素，array有序且包含重复元素
     */
    public static int search6(int[] array, int value) {
        int min = 0;
        int max = array.length - 1;
        while (min <= max) {
            int middle = (min + max) / 2;
            // 如果小于等于给定值，则判断它的前一个元素是否也小于等于给定值
            if (array[middle] <= value) {
                if (middle == 0 || array[middle - 1] > value) {
                    return middle;
                } else {
                    max = middle - 1;
                }
            } else {
                min = middle + 1;
            }
        }
        return -1;
    }

    /**
     * 查找最后一个大于等于给定值的元素，array有序且包含重复元素
     */
    public static int search7(int[] array, int value) {
        int min = 0;
        int max = array.length - 1;
        while (min <= max) {
            int middle = (min + max) / 2;
            // 如果大于等于给定值，则判断它的后一个元素是否也大于等于给定值
            if (array[middle] >= value) {
                if (middle == max || array[middle + 1] < value) {
                    return middle;
                } else {
                    min = middle + 1;
                }
            } else {
                min = middle + 1;
            }
        }
        return -1;
    }

    /**
     * 查找最后一个小于等于给定值的元素，array有序且包含重复元素
     */
    public static int search8(int[] array, int value) {
        int min = 0;
        int max = array.length - 1;
        while (min <= max) {
            int middle = min + ((max - min) >> 1);
            if (array[middle] > value) {
                max = middle - 1;
            } else {
                if (middle == max || array[middle + 1] >= value) {
                    return middle;
                } else {
                    min = middle + 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(search1(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 8));
        System.out.println(search2(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 8));

        System.out.println(search3(new int[]{1, 2, 2, 4, 5, 5, 7, 8, 8}, 8));
        System.out.println(search4(new int[]{1, 2, 2, 4, 5, 5, 7, 8, 8}, 2));
        System.out.println(search5(new int[]{1, 2, 2, 4, 5, 5, 7, 8, 8, 10, 10, 10}, 9));
        System.out.println(search6(new int[]{1, 2, 2, 4, 5, 5, 7, 8, 8, 10, 10, 10}, 9));
        System.out.println(search7(new int[]{1, 2, 2, 4, 5, 5, 7, 8, 8, 10, 10, 10}, 9));
        System.out.println(search8(new int[]{1, 2, 2, 4, 5, 5, 7, 8, 8, 10, 10, 10}, 9));
    }
}
