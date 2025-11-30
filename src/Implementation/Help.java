package Implementation;

import java.io.*;
import java.util.*;

// Kattis - Help!
public class Help {

    static class DSU {
        Map<String, String> root = new HashMap<>();

        String find(String x) {
            root.putIfAbsent(x, x);
            if (!root.get(x).equals(x))
                root.put(x, find(root.get(x)));
            return root.get(x);
        }

        void union(String a, String b) {
            a = find(a);
            b = find(b);
            if (!a.equals(b)) root.put(a, b);
        }
    }

    static boolean isPlaceholder(String s) {
        return s.startsWith("<") && s.endsWith(">");
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            String[] A = br.readLine().trim().split(" ");
            String[] B = br.readLine().trim().split(" ");

            if (A.length != B.length) {
                out.append("-\n");
                continue;
            }

            int n = A.length;
            DSU dsu = new DSU();

            Map<String, String> assign = new HashMap<>();
            boolean ok = true;

            for (int i = 0; i < n && ok; i++) {
                String x = A[i];
                String y = B[i];

                boolean phX = isPlaceholder(x);
                boolean phY = isPlaceholder(y);

                if (!phX && !phY) {
                    if (!x.equals(y)) ok = false;
                }
                else if (phX && phY) {
                    dsu.union(x, y);
                }
                else if (phX && !phY) {
                    String root = dsu.find(x);
                    if (assign.containsKey(root) && !assign.get(root).equals(y))
                        ok = false;
                    assign.put(root, y);
                }
                else {
                    String root = dsu.find(y);
                    if (assign.containsKey(root) && !assign.get(root).equals(x))
                        ok = false;
                    assign.put(root, x);
                }
            }

            if (!ok) {
                out.append("-\n");
                continue;
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < A.length; i++) {
                String t = A[i];

                if (!isPlaceholder(t)) {
                    sb.append(t);
                } else {
                    String root = dsu.find(t);
                    String w = assign.getOrDefault(root, "a"); // free choice
                    sb.append(w);
                }

                if (i + 1 < A.length) sb.append(" ");
            }

            out.append(sb.toString()).append("\n");
        }

        System.out.print(out);
    }
}
