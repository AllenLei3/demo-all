package org.xl.algorithm.leetcode.jianzhioffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author xulei
 */
public class 字符串 {

    /**
     * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
     *
     * 示例 1：
     *
     * 输入：s = "We are happy."
     * 输出："We%20are%20happy."
     */
    public String replaceSpace(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        char[] charArray = s.toCharArray();
        // 先申请一个三倍大小的数组，保证所有字符都可以替换
        char[] newCharArray = new char[charArray.length * 3];
        int size = 0;
        // 遍历判断是否为空格
        for (char c : charArray) {
            if (Character.isWhitespace(c)) {
                newCharArray[size] = '%';
                newCharArray[size + 1] = '2';
                newCharArray[size + 2] = '0';
                size += 3;
            } else {
                newCharArray[size] = c;
                size++;
            }
        }
        // 返回 0~size
        return new String(newCharArray, 0, size);
    }

    /**
     * 输入一个字符串，打印出该字符串中字符的所有排列。
     * 你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。
     *
     * 示例:
     *
     * 输入：s = "abc"
     * 输出：["abc","acb","bac","bca","cab","cba"]
     */
    public String[] permutation(String s) {
        c = s.toCharArray();
        dfs(0);
        return res.toArray(new String[0]);
    }

    private final List<String> res = new LinkedList<>();
    private char[] c;

    private void dfs(int x) {
        if (x == c.length - 1) {
            res.add(String.valueOf(c));      // 添加排列方案
            return;
        }
        HashSet<Character> set = new HashSet<>();
        for (int i = x; i < c.length; i++) {
            if (set.contains(c[i])) {
                continue;       // 重复，因此剪枝
            }
            set.add(c[i]);
            swap(i, x);         // 交换，将 c[i] 固定在第 x 位
            dfs(x + 1);      // 开启固定第 x + 1 位字符
            swap(i, x);         // 恢复交换
        }
    }

    private void swap(int a, int b) {
        char tmp = c[a];
        c[a] = c[b];
        c[b] = tmp;
    }

    /**
     * 请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
     *
     * 示例 1:
     *
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     *
     * 示例 2:
     *
     * 输入: "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     *
     * 示例 3:
     *
     * 输入: "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     */
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        char[] charArray = s.toCharArray();
        // i为窗口左值，j为窗口右值. ans为最大子串长度
        int i = 0, ans = 0;
        for (int j = 0; j < charArray.length; j++) {
            // 如果有重复值，则窗口左侧需要滑动，滑动后删除不在窗口范围内的map里的数据
            if (map.containsKey(charArray[j])) {
                // 清空map数据
                int index = map.get(charArray[j]) + 1;
                for (int start = i; start < index; start++) {
                    map.remove(charArray[start]);
                }
                // 窗口左侧滑动到上一次该字符出现的位置
                i = index;
            }
            map.put(charArray[j], j);
            // 统计子串的长度
            ans = Math.max(j - i + 1, ans);
        }
        return ans;
    }

    /**
     * 在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。 s 只包含小写字母。
     *
     * 示例 1:
     *
     * 输入：s = "abaccdeff"
     * 输出：'b'
     *
     * 示例 2:
     *
     * 输入：s = ""
     * 输出：' '
     */
    public char firstUniqChar(String s) {
        LinkedHashMap<Character, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < s.length(); i++) {
            Integer count = map.get(s.charAt(i));
            if (count == null) {
                count = 1;
            } else {
                count++;
            }
            map.put(s.charAt(i), count);
        }
        // 利用LinkedHashMap的有序性找到第一个只出现一次的字符
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return ' ';
    }

    /**
     * 输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一样处理。例如输入字符串"I am a student. "，则输出"student. a am I"。
     *
     * 示例 1：
     *
     * 输入: "the sky is blue"
     * 输出: "blue is sky the"
     *
     * 示例 2：
     *
     * 输入: "  hello world!  "
     * 输出: "world! hello"
     * 解释: 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
     *
     * 示例 3：
     *
     * 输入: "a good   example"
     * 输出: "example good a"
     * 解释: 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
     */
    public String reverseWords(String s) {
        List<String> res = new ArrayList<>();
        // 去除首尾空格
        s = s.trim();
        int i = s.length() - 1, j = i;
        while (i >= 0) {
            // 搜索第一个空格
            while (i >= 0 && s.charAt(i) != ' ') {
                i--;
            }
            // 保存单词
            res.add(s.substring(i + 1, j + 1));
            // 跳过连续空格
            while (i >= 0 && s.charAt(i) == ' ') {
                i--;
            }
            // 将j指向下一个单词的尾部
            j = i;
        }
        return String.join(" ", res);
    }

    /**
     * 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。
     * 比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
     *
     * 示例 1：
     *
     * 输入: s = "abcdefg", k = 2
     * 输出: "cdefgab"
     *
     * 示例 2：
     *
     * 输入: s = "lrloseumgh", k = 6
     * 输出: "umghlrlose"
     */
    public String reverseLeftWords(String s, int n) {
        String left = s.substring(0, n);
        String right = s.substring(n);
        return right + left;
    }

    /**
     * 写一个函数 StrToInt，实现把字符串转换成整数这个功能。不能使用 atoi 或者其他类似的库函数。
     *
     * 首先，该函数会根据需要丢弃无用的开头空格字符，直到寻找到第一个非空格的字符为止。
     * 当我们寻找到的第一个非空字符为正或者负号时，则将该符号与之后面尽可能多的连续数字组合起来，作为该整数的正负号；
     * 假如第一个非空字符是数字，则直接将其与之后连续的数字字符组合起来，形成整数。
     * 该字符串除了有效的整数部分之后也可能会存在多余的字符，这些字符可以被忽略，它们对于函数不应该造成影响。
     *
     * 注意：假如该字符串中的第一个非空格字符不是一个有效整数字符、字符串为空或字符串仅包含空白字符时，则你的函数不需要进行转换。
     * 在任何情况下，若函数不能进行有效的转换时，请返回 0。
     *
     * 说明：
     *
     * 假设我们的环境只能存储 32 位大小的有符号整数，那么其数值范围为 [−231,  231 − 1]。如果数值超过这个范围，请返回  INT_MAX (231 − 1) 或 INT_MIN (−231) 。
     *
     * 示例 1:
     *
     * 输入: "42"
     * 输出: 42
     *
     * 示例 2:
     *
     * 输入: "   -42"
     * 输出: -42
     * 解释: 第一个非空白字符为 '-', 它是一个负号。
     *      我们尽可能将负号与后面所有连续出现的数字组合起来，最后得到 -42 。
     *
     * 示例 3:
     *
     * 输入: "4193 with words"
     * 输出: 4193
     * 解释: 转换截止于数字 '3' ，因为它的下一个字符不为数字。
     *
     * 示例 4:
     *
     * 输入: "words and 987"
     * 输出: 0
     * 解释: 第一个非空字符是 'w', 但它不是数字或正、负号。
     *      因此无法执行有效的转换。
     *
     * 示例 5:
     *
     * 输入: "-91283472332"
     * 输出: -2147483648
     * 解释: 数字 "-91283472332" 超过 32 位有符号整数范围。
     *      因此返回 INT_MIN (−231) 。
     */
    public int strToInt(String str) {
        char[] array = str.trim().toCharArray();
        if (array.length == 0) {
            return 0;
        }
        int i = 1;
        // 判断符号位
        boolean negative = true;
        if ('-' == array[0]) {
            negative = false;
        } else if ('+' != array[0]) {
            // 第一个字符不是符号位
            i = 0;
        }
        // 遍历剩余字符，提取数字
        int res = 0, maxInt = Integer.MAX_VALUE / 10;;
        for (; i < array.length; i++) {
            // 非数字
            if (array[i] < '0' || array[i] > '9') {
                break;
            }
            // 判断是否超过最大整数
            if (res > maxInt || (res == maxInt && array[i] > '7')) {
                return negative ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            res = res * 10 + (array[i] - '0');
        }
        return res == 0 ? 0 : (negative ? res : res * -1);
    }
}
