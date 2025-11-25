package Recursive_Tree_Graph;

import java.io.*;
import java.util.*;

//Kattis - ReMorse
public class Remorse {

    static class Node implements Comparable<Node> {
        long freq;
        Node left, right;

        Node(long f) {
            freq = f;
        }
        Node(Node a, Node b) {
            freq = a.freq + b.freq;
            left = a;
            right = b;
        }
        public int compareTo(Node o) {
            return Long.compare(this.freq, o.freq);
        }
    }

    static Map<Node, Integer> depthMap = new HashMap<>();

    static void dfs(Node n, int depth) {
        if (n.left == null && n.right == null) {
            depthMap.put(n, depth);
            return;
        }
        if (n.left != null) dfs(n.left, depth + 1);
        if (n.right != null) dfs(n.right, depth + 1);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

        long[] freq = new long[27];
        int totalLetters = 0;

        for (char c : s.toCharArray()) {
            if ('A' <= c && c <= 'Z') {
                freq[c - 'A']++;
                totalLetters++;
            } else if ('a' <= c && c <= 'z') {
                freq[c - 'a']++;
                totalLetters++;
            }
        }

        if (totalLetters == 1) {
            System.out.println(1);
            return;
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        List<Node> leaves = new ArrayList<>();

        for (int i = 0; i < 27; i++) {
            if (freq[i] > 0) {
                Node n = new Node(freq[i]);
                pq.add(n);
                leaves.add(n);
            }
        }

        // Huffman tree construction
        while (pq.size() > 1) {
            Node a = pq.poll();
            Node b = pq.poll();
            pq.add(new Node(a, b));
        }

        Node root = pq.poll();

        dfs(root, 1);

        long cost = 0;
        int leafIndex = 0;

        for (int i = 0; i < 27; i++) {
            if (freq[i] > 0) {
                Node leaf = leaves.get(leafIndex++);
                int d = depthMap.get(leaf);
                cost += freq[i] * (2L * d - 1L);
            }
        }

        cost += 3L * (totalLetters - 1);

        System.out.println(cost);
    }
}

