package Recursive_Tree_Graph;

import java.util.*;
import java.io.*;

// Kattis - Tree hugging
public class TreeHugging {

    static int n;
    static List<Integer>[] adj;
    static int[] arr;
    static boolean[] checked;
    static int number = 0;
    static int[] CheckTag;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String firstLine = br.readLine();
        if (firstLine == null) return;

        n = Integer.parseInt(firstLine.trim());
        int num = 2 * n - 2; //문제에서 주어진 값 등을 사용

        adj = new ArrayList[num];
        for (int i = 0; i < num; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int node = Integer.parseInt(st.nextToken());
            int Num = Integer.parseInt(st.nextToken());

            int value = Math.min(node, Num);
            int maxValue = Math.max(node, Num);

            adj[maxValue - 2].add(i);

            adj[(n - 1) + (value - 1)].add(i);
        }

        arr = new int[num];
        Arrays.fill(arr, -1);
        CheckTag = new int[num];
        int count = 0;

        for (int i = 0; i < num; i++) {
            number++;
            if (dfs(i)) count++;
        }

        if (count < num) {
            System.out.println("Tree hugging");
        } else {
            char[] result = new char[num];
            for (int edgeIdx = 0; edgeIdx < num; edgeIdx++) {
                if (arr[edgeIdx] < n - 1) result[edgeIdx] = 'L';
                else result[edgeIdx] = 'R';
            }
            System.out.println(new String(result));
        }
    }

    static boolean dfs(int num) {
        for (int edgeIdx : adj[num]) {
            if (CheckTag[edgeIdx] == number) continue;
            CheckTag[edgeIdx] = number;

            if (arr[edgeIdx] == -1 || dfs(arr[edgeIdx])) {
                arr[edgeIdx] = num;
                return true;
            }
        }
        return false;
    }
}