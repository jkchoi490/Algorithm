package DynamicProgramming;

import java.io.*;
import java.util.*;

// Kattis - What Does It Mean?
public class WhatDoesItMean {

    static class TrieNode {
        TrieNode[] next = new TrieNode[37];
        int meaningCount = 0;  // Non-zero if a dictionary word ends here
    }

    static final long MOD = 1000000007L;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        String name = st.nextToken();
        int L = name.length();

        TrieNode root = new TrieNode();

        // Build Trie with meanings
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            String w = st.nextToken();
            int meanings = Integer.parseInt(st.nextToken());

            TrieNode cur = root;
            for (char c : w.toCharArray()) {
                int idx = c - 'a';
                if (cur.next[idx] == null) cur.next[idx] = new TrieNode();
                cur = cur.next[idx];
            }
            cur.meaningCount = meanings;
        }

        long[] dp = new long[L + 1];
        dp[0] = 1;

        // DP
        for (int i = 0; i < L; i++) {
            if (dp[i] == 0) continue;

            TrieNode cur = root;
            for (int j = i; j < L; j++) {
                int idx = name.charAt(j) - 'a';
                if (cur.next[idx] == null) break;

                cur = cur.next[idx];

                if (cur.meaningCount > 0) {
                    dp[j + 1] = (dp[j + 1] + dp[i] * cur.meaningCount) % MOD;
                }
            }
        }

        System.out.println(dp[L] % MOD);
    }
}
