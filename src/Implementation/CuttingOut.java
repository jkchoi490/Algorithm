package Implementation;

import java.io.*;
import java.util.*;

// Sphere Online Judge - Cutting Out
public class CuttingOut {

    static class Arr {
        int x1, x2, y1, y2;
        Arr(int a, int b, int c, int d) {
            this.x1 = a;
            this.x2 = b;
            this.y1 = c;
            this.y2 = d;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int d = Integer.parseInt(br.readLine());

        while (d-- > 0) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());

            Arr[] arr = new Arr[r];

            if (r > 0) {
                st = new StringTokenizer(br.readLine());
                for (int i = 0; i < r; i++) {
                    int x1 = Integer.parseInt(st.nextToken());
                    int x2 = Integer.parseInt(st.nextToken());
                    int y1 = Integer.parseInt(st.nextToken());
                    int y2 = Integer.parseInt(st.nextToken());
                    arr[i] = new Arr(x1, x2, y1, y2);
                }
            } else {
                br.readLine(); // consume empty line
            }

            // Collect unique coordinates
            TreeSet<Integer> xs = new TreeSet<>();
            TreeSet<Integer> ys = new TreeSet<>();

            xs.add(0); xs.add(n);
            ys.add(0); ys.add(n);

            for (Arr R : arr) {
                xs.add(R.x1);
                xs.add(R.x2);
                ys.add(R.y1);
                ys.add(R.y2);
            }

            List<Integer> xv = new ArrayList<>(xs);
            List<Integer> yv = new ArrayList<>(ys);

            int X = xv.size();
            int Y = yv.size();

            boolean[][] blocked = new boolean[Y - 1][X - 1];

            // Mark blocked cells
            for (Arr R : arr) {
                int xi1 = Collections.binarySearch(xv, R.x1);
                int xi2 = Collections.binarySearch(xv, R.x2);
                int yi1 = Collections.binarySearch(yv, R.y1);
                int yi2 = Collections.binarySearch(yv, R.y2);

                for (int yy = yi1; yy < yi2; yy++) {
                    for (int xx = xi1; xx < xi2; xx++) {
                        blocked[yy][xx] = true;
                    }
                }
            }

            int[] hist = new int[X - 1];
            int maxArea = 0;

            for (int y = 0; y < Y - 1; y++) {
                for (int x = 0; x < X - 1; x++) {
                    if (!blocked[y][x]) {
                        hist[x] += (yv.get(y + 1) - yv.get(y));
                    } else {
                        hist[x] = 0;
                    }
                }
                maxArea = Math.max(maxArea, solve(hist));
            }

            System.out.println(maxArea);
        }
    }

    static int solve(int[] h) {
        Stack<Integer> st = new Stack<>();
        int max = 0;
        int n = h.length;

        for (int i = 0; i <= n; i++) {
            int cur = (i == n ? 0 : h[i]);

            while (!st.isEmpty() && cur < h[st.peek()]) {
                int height = h[st.pop()];
                int width = st.isEmpty() ? i : (i - st.peek() - 1);
                max = Math.max(max, height * width);
            }
            st.push(i);
        }
        return max;
    }
}
