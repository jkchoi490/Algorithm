package Implementation;

import java.io.*;
import java.util.*;

//Dovelet - 공주
public class 공주{

    static class Plant {
        int x, y, id;
        Plant(int x, int y, int id) {
            this.x = x; this.y = y; this.id = id;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        String dirs = br.readLine().trim();

        Map<Integer, ArrayList<Plant>> diag1 = new HashMap<>();
        Map<Integer, ArrayList<Plant>> diag2 = new HashMap<>();

        Plant[] plants = new Plant[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            Plant p = new Plant(x, y, i);
            plants[i] = p;

            diag1.computeIfAbsent(x - y, k -> new ArrayList<>()).add(p);
            diag2.computeIfAbsent(x + y, k -> new ArrayList<>()).add(p);
        }

        for (ArrayList<Plant> list : diag1.values()) list.sort(Comparator.comparingInt(a -> a.x));
        for (ArrayList<Plant> list : diag2.values()) list.sort(Comparator.comparingInt(a -> a.x));

        boolean[] removed = new boolean[N];
        Plant cur = plants[0];

        for (int i = 0; i < K; i++) {
            char c = dirs.charAt(i);
            int x = cur.x, y = cur.y;
            Plant next = null;

            if (c == 'A' || c == 'D') {
                int key = x - y;
                ArrayList<Plant> list = diag1.get(key);
                int pos = binary_search(list, x);

                if (c == 'A') {
                    for (int j = pos; j < list.size(); j++) {
                        if (!removed[list.get(j).id]) {
                            next = list.get(j);
                            break;
                        }
                    }
                } else {
                    for (int j = pos - 1; j >= 0; j--) {
                        if (!removed[list.get(j).id]) {
                            next = list.get(j);
                            break;
                        }
                    }
                }

            } else {
                int key = x + y;
                ArrayList<Plant> list = diag2.get(key);
                int pos = binary_search(list, x);

                if (c == 'B') {
                    for (int j = pos; j < list.size(); j++) {
                        if (!removed[list.get(j).id]) {
                            next = list.get(j);
                            break;
                        }
                    }
                } else { // C
                    for (int j = pos - 1; j >= 0; j--) {
                        if (!removed[list.get(j).id]) {
                            next = list.get(j);
                            break;
                        }
                    }
                }
            }

            if (next != null) {
                removed[cur.id] = true;
                cur = next;
            }
        }

        System.out.println(cur.x + " " + cur.y);
    }

    static int binary_search(ArrayList<Plant> list, int target) {
        int l = 0, r = list.size();
        while (l < r) {
            int m = (l + r) >>> 1;
            if (list.get(m).x <= target) l = m + 1;
            else r = m;
        }
        return l;
    }
}
