package org.xl.algorithm.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 图，通过邻接表来实现(数组+链表)
 *
 * @author xulei
 */
public class Graph {

    /**
     * 邻接表，数组下标代表顶点元素，链表里保存边的信息
     */
    private final List<Integer>[] listArray;

    public Graph(int capacity) {
        listArray = new List[capacity];
        for (int i = 0; i < capacity; i++) {
            listArray[i] = new ArrayList<>();
        }
    }

    /**
     * 添加边，无向图一次保存两条边，有向图一次保存一条边
     */
    public void addEdge(int start, int target) {
        listArray[start].add(target);
        listArray[target].add(start);
    }

    /**
     * 广度优先搜索,获取s到t的最短路径
     *
     * @param s 起始顶点
     * @param t 终止顶点
     */
    public void bfs(int s, int t) {
        if (s == t) {
            return;
        }
        // 用来记录已经被访问的顶点，避免顶点被重复访问
        boolean[] visited = new boolean[listArray.length];
        visited[s] = true;
        // 用来存储自身已经被访问了的，但其相连的顶点还没有被访问到的顶点
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        // 用来记录搜索路径，初始值都设为-1，表示没有元素。此处数组下标表示顶点，数组元素表示该下标顶点对应的前驱顶点
        int[] prev = new int[listArray.length];
        Arrays.fill(prev, -1);

        // 外层while循环用来遍历层
        while (queue.size() != 0) {
            int node = queue.poll();
            // 遍历该顶点所连接的其他顶点
            for (int i = 0; i < listArray[node].size(); i++) {
                int backNode = listArray[node].get(i);
                // 如果该顶点之前没有访问过
                if (!visited[backNode]) {
                    // 以该顶点为数组下标保存该顶点的前驱顶点，相当于prev[]数组的每个元素保存的都是该元素的前驱顶点
                    prev[backNode] = node;
                    if (backNode == t) {
                        // 递归输出路径，逆序输出，比如终点是6，则prev[6]指向的是6的前驱顶点，依次递推直到起点
                        print(prev, s, t);
                        return;
                    }
                    visited[backNode] = true;
                    // 已经访问过的顶点加到队列中
                    queue.add(backNode);
                }
            }
        }
    }

    private void print(int[] prev, int s, int t) {
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.println(t + " ");
    }

    /** 深度优先搜索时，是否已找到终止顶点 */
    private static boolean found = false;

    /**
     * 深度优先搜索算法，该算法得到的路径并不是最短路径
     *
     * @param s 起始顶点
     * @param t 终止顶点
     */
    public void dfs(int s, int t) {
        found = false;
        // 用来记录已经被访问的顶点，避免顶点被重复访问
        boolean[] visited = new boolean[listArray.length];
        // 用来记录搜索路径，初始值都设为-1，表示没有元素。此处数组下标表示顶点，数组元素表示该下标顶点对应的前驱顶点
        int[] prev = new int[listArray.length];
        Arrays.fill(prev, -1);
        // 递归搜索路径
        recurDfs(s, t, visited, prev);
        print(prev, s, t);
    }

    private void recurDfs(int node, int end, boolean[] visited, int[] prev) {
        if (found) {
            return;
        }
        visited[node] = true;
        if (node == end) {
            found = true;
            return;
        }
        // 遍历该顶点连接的其他顶点
        for (int i = 0; i < listArray[node].size(); ++i) {
            int q = listArray[node].get(i);
            // 如果之前已经访问过了，就跳过本次for循环，如果整个for循环结束后都没有发现新的顶点，则回到递归的上一层（回溯）
            if (!visited[q]) {
                prev[q] = node;
                recurDfs(q, end, visited, prev);
            }
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(8);
        graph.addEdge(0,1);
        graph.addEdge(0,3);
        graph.addEdge(1,2);
        graph.addEdge(1,4);
        graph.addEdge(2,5);
        graph.addEdge(4,5);
        graph.addEdge(4,6);
        graph.addEdge(5,7);
        graph.addEdge(6,7);

        // 广度优先
        graph.bfs(0,6);
        System.out.println("----------");
        // 深度优先
        graph.dfs(0, 6);
    }
}
