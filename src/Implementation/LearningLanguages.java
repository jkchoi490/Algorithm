package Implementation;

import java.io.*;
import java.util.*;

//CodeForces - Learning Languages
public class LearningLanguages {

    static class DSU {
        int[] root, size;

        DSU(int n) {
            root = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                root[i] = i;
                size[i] = 1;
            }
        }

        int find(int a) {
            if (root[a] != a) root[a] = find(root[a]);
            return root[a];
        }

        void union(int a, int b) {
            a = find(a);
            b = find(b);
            if (a == b) return;
            if (size[a] < size[b]) {
                int tmp = a; a = b; b = tmp;
            }
            root[b] = a;
            size[a] += size[b];
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        DSU dsu = new DSU(n + m + 5);

        boolean check = false;
        ArrayList<Integer>[] langs = new ArrayList[m + 1];
        for (int i = 1; i <= m; i++) langs[i] = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            if (k > 0) check = true;

            for (int j = 0; j < k; j++) {
                int lang = Integer.parseInt(st.nextToken());
                dsu.union(i, n + lang);
                langs[lang].add(i);
            }
        }

        if (!check) {
            System.out.println(n);
            return;
        }

        HashSet<Integer> comps = new HashSet<>();
        for (int i = 1; i <= n; i++) {
            comps.add(dsu.find(i));
        }

        int result = comps.size() - 1;
        System.out.println(result);
    }
}
