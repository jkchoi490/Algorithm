package BruteForce;

import java.io.*;
import java.util.*;

//Help Victoria the Wise
public class HelpVictoriatheWise {

    // 주어진 큐브 회전 매핑에 따라 문자열 회전
    static String rotate(String s, int[] mapping) {
        char[] t = new char[6];
        for (int i = 0; i < 6; i++) {
            t[i] = s.charAt(mapping[i]);
        }
        return new String(t);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();
        br.close();

        // 큐브의 24회전 매핑 (0~5: 위, 아래, 앞, 뒤, 왼쪽, 오른쪽 면)
        int[][] rotations = {
                {0,1,2,3,4,5},{0,2,4,1,3,5},{0,4,3,2,1,5},{0,3,1,4,2,5},
                {1,5,2,3,0,4},{1,2,0,5,4,3},{1,0,4,3,5,2},{1,4,5,2,0,3},
                {2,0,1,5,4,3},{2,1,5,4,0,3},{2,5,4,0,1,3},{2,4,0,1,5,3},
                {3,0,4,5,1,2},{3,4,5,1,0,2},{3,5,1,0,4,2},{3,1,0,4,5,2},
                {4,0,2,5,3,1},{4,2,5,3,0,1},{4,5,3,0,2,1},{4,3,0,2,5,1},
                {5,1,3,2,4,0},{5,3,2,4,1,0},{5,2,4,1,3,0},{5,4,1,3,2,0}
        };

        char[] arr = s.toCharArray();
        Arrays.sort(arr);
        Set<String> unique = new HashSet<>();

        permute(arr, 0, rotations, unique);
        System.out.println(unique.size());
    }

    static void permute(char[] arr, int l, int[][] rotations, Set<String> unique) {
        if (l == arr.length) {
            String s = new String(arr);
            String min = s;
            for (int[] rot : rotations) {
                String r = rotate(s, rot);
                if (r.compareTo(min) < 0) min = r;
            }
            unique.add(min);
            return;
        }

        for (int i = l; i < arr.length; i++) {
            swap(arr, i, l);
            permute(arr, l + 1, rotations, unique);
            swap(arr, i, l);
        }
    }

    static void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}

