package org.xl.algorithm.leetcode.tophot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * @author xulei
 */
public class 字符串 {

    /**
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     *
     * 示例 1：
     *
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba" 同样是符合题意的答案。
     *
     * 示例 2：
     *
     * 输入：s = "cbbd"
     * 输出："bb"
     */
    public String longestPalindrome(String s) {
        if (s.length() < 2){
            return s;
        }
        int len = s.length();

        // 记录回文串的最长长度
        int maxLen = 1;
        // 回文串的起始位置
        int begin  = 0;

        // 构建DP数组
        boolean[][] dp = new boolean[len][len];
        // 初始化长度为1的子串，长度为1肯定是回文串
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }

        char[] chars = s.toCharArray();
        // 填表规则：先一列一列的填写，再一行一行的填，保证左下方的单元格先进行计算
        for (int j = 1; j < len; j++) {
            for (int i = 0; i < j; i++) {
                if (chars[i] != chars[j]) {
                    // 两头不相等，肯定不是回文串
                    dp[i][j] = false;
                } else {
                    // 相等的情况下, 考虑头尾去掉以后没有字符剩余，或者剩下一个字符的时候，肯定是回文串
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        // 状态转移, 头向前走一个，尾向后走一个，即去掉两个头后还是否是回文串
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
                // 只要dp[i][j] == true 成立，表示s[i...j]是回文串, 此时更新记录回文长度和起始位置
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    /**
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按任意顺序返回。
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     *
     * 示例 1：
     *
     * 输入：digits = "23"
     * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
     *
     * 示例 2：
     *
     * 输入：digits = ""
     * 输出：[]
     *
     * 示例 3：
     *
     * 输入：digits = "2"
     * 输出：["a","b","c"]
     */
    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return new ArrayList<>();
        }
        phoneMap.put('2', new String[]{"a", "b", "c"});
        phoneMap.put('3', new String[]{"d", "e", "f"});
        phoneMap.put('4', new String[]{"g", "h", "i"});
        phoneMap.put('5', new String[]{"j", "k", "l"});
        phoneMap.put('6', new String[]{"m", "n", "o"});
        phoneMap.put('7', new String[]{"p", "q", "r", "s"});
        phoneMap.put('8', new String[]{"t", "u", "v"});
        phoneMap.put('9', new String[]{"w", "x", "y", "z"});
        recur(digits, 0, new StringBuilder());
        return comRes;
    }

    private final Map<Character, String[]> phoneMap = new HashMap<>();
    private final List<String> comRes = new ArrayList<>();

    private void recur(String digits, int index, StringBuilder sb) {
        if (index == digits.length()) {
            comRes.add(sb.toString());
            return;
        }
        String[] str = phoneMap.get(digits.charAt(index));
        if (str == null) {
            return;
        }
        for (String s : str) {
            sb.append(s);
            recur(digits, index + 1, sb);
            // 回溯
            sb.deleteCharAt(index);
        }
    }

    /**
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     *
     * 有效字符串需满足：
     * 1. 左括号必须用相同类型的右括号闭合。
     * 2. 左括号必须以正确的顺序闭合。
     *
     * 示例 1：
     *
     * 输入：s = "()"
     * 输出：true
     *
     * 示例 2：
     *
     * 输入：s = "()[]{}"
     * 输出：true
     *
     * 示例 3：
     *
     * 输入：s = "(]"
     * 输出：false
     *
     * 示例 4：
     *
     * 输入：s = "([)]"
     * 输出：false
     *
     * 示例 5：
     *
     * 输入：s = "{[]}"
     * 输出：true
     */
    public boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (char c1 : s.toCharArray()) {
            if (stack.empty()) {
                stack.push(c1);
            } else {
                Character character = stack.peek();
                // 括号可以抵消的，进行出栈
                if ((character == '(' && c1 == ')') || (character == '{' && c1 == '}') || (character == '[' && c1 == ']')) {
                    stack.pop();
                } else {
                    stack.push(c1);
                }
            }
        }
        return stack.empty();
    }

    /**
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且有效的括号组合。
     *
     * 示例 1：
     *
     * 输入：n = 3
     * 输出：["((()))","(()())","(())()","()(())","()()()"]
     *
     * 示例 2：
     *
     * 输入：n = 1
     * 输出：["()"]
     */
    public List<String> generateParenthesis(int n) {
        if (n == 0) {
            return parenthesis;
        }
        backtrack(new StringBuilder(), 0, 0, n);
        return parenthesis;
    }

    private final List<String> parenthesis = new ArrayList<>();

    private void backtrack(StringBuilder cur, int open, int close, int max) {
        if (cur.length() == max * 2) {
            parenthesis.add(cur.toString());
            return;
        }
        // 先递归全是左括号的情况
        if (open < max) {
            cur.append("(");
            backtrack(cur, open + 1, close, max);
            cur.deleteCharAt(cur.length() - 1);
        }
        if (open > close) {
            cur.append(")");
            backtrack(cur, open, close + 1, max);
            cur.deleteCharAt(cur.length() - 1);
        }
    }

    /**
     * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
     *
     * 示例 1：
     *
     * 输入：s = "(()"
     * 输出：2
     * 解释：最长有效括号子串是 "()"
     *
     * 示例 2：
     *
     * 输入：s = ")()())"
     * 输出：4
     * 解释：最长有效括号子串是 "()()"
     *
     * 示例 3：
     *
     * 输入：s = ""
     * 输出：0
     */
    public int longestValidParentheses(String s) {
        int res = 0;
        // 保持栈底元素为当前已经遍历过的元素中, 最后一个没有被匹配的右括号的下标
        Deque<Integer> stack = new LinkedList<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    res = Math.max(res, i - stack.peek());
                }
            }
        }
        return res;
    }

    /**
     * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
     * 字母异位词 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母通常恰好只用一次。
     *
     * 示例 1:
     *
     * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
     *
     * 示例 2:
     *
     * 输入: strs = [""]
     * 输出: [[""]]
     *
     * 示例 3:
     *
     * 输入: strs = ["a"]
     * 输出: [["a"]]
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] array = str.toCharArray();
            Arrays.sort(array);
            String key = new String(array);
            List<String> list = map.getOrDefault(key, new ArrayList<>());
            list.add(str);
            map.put(key, list);
        }
        return new ArrayList<>(map.values());
    }

    /**
     * 给你一个字符串 s、一个字符串 t。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 ""。
     *
     * 注意：
     * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
     * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
     *
     * 示例 1：
     *
     * 输入：s = "ADOBECODEBANC", t = "ABC"
     * 输出："BANC"
     *
     * 示例 2：
     *
     * 输入：s = "a", t = "a"
     * 输出："a"
     *
     * 示例 3:
     *
     * 输入: s = "a", t = "aa"
     * 输出: ""
     * 解释: t 中两个字符 'a' 均应包含在 s 的子串中，因此没有符合条件的子字符串，返回空字符串。
     */
    public String minWindow(String s, String t) {
        String res = "";
        // 记录目标字符及出现次数
        Map<Character, Integer> need = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }
        int i = 0;
        // 还需要匹配的字符数
        int needCount = t.length();
        // 滑动直到满足包含所有目标字符
        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            // 如果是待匹配的目标字符
            if (need.getOrDefault(c, 0) > 0) {
                needCount--;
            }
            need.put(c, need.getOrDefault(c, 0) - 1);
            // 如果匹配了全部目标字符,记录子串
            if (needCount == 0) {
                // 窗口左侧滑动，直到不满足包含所有字符
                while (i <= j) {
                    c = s.charAt(i);
                    int needValue = need.get(c);
                    if (needValue + 1 > 0) {
                        needCount++;
                        need.put(c, needValue + 1);
                        // 记录最短子串
                        res = res.equals("") ? s.substring(i, j + 1) : (res.length() > (j - i + 1) ? s.substring(i, j + 1) : res);
                        i++;
                        break;
                    } else {
                        i++;
                        need.put(c, needValue + 1);
                    }
                }
            }
        }
        return res;
    }

}
