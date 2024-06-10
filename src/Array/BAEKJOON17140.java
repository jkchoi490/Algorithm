import java.io.*;
import java.util.*;

class Number {
    int num, cnt;

    public Number(int num, int cnt) {
        this.num = num;
        this.cnt = cnt;
    }
}

public class BAEKJOON17140 {

    static int r, c, k, time;
    static int[][] A;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        A = new int[3][3];

        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        time = 0;
        while (time <= 100) {
            if (r - 1 < A.length && c - 1 < A[0].length && A[r - 1][c - 1] == k) {
                System.out.println(time);
                return;
            }
            if (A.length >= A[0].length) {
                R();
            } else {
                C();
            }
            time++;
        }
        System.out.println(-1);
    }

    private static void R() {
        int maxCol = 0;
        List<int[]> newRows = new ArrayList<>();
        for (int[] row : A) {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int num : row) {
                if (num != 0) {
                    map.put(num, map.getOrDefault(num, 0) + 1);
                }
            }

            List<Number> list = new ArrayList<>();
            for (int key : map.keySet()) {
                list.add(new Number(key, map.get(key)));
            }

            Collections.sort(list, (o1, o2) -> {
                if (o1.cnt == o2.cnt) {
                    return o1.num - o2.num;
                }
                return o1.cnt - o2.cnt;
            });

            List<Integer> newRow = new ArrayList<>();
            for (Number number : list) {
                newRow.add(number.num);
                newRow.add(number.cnt);
            }

            maxCol = Math.max(maxCol, newRow.size());
            newRows.add(newRow.stream().mapToInt(i -> i).toArray());
        }

        int[][] newA = new int[newRows.size()][maxCol];
        for (int i = 0; i < newRows.size(); i++) {
            for (int j = 0; j < newRows.get(i).length; j++) {
                newA[i][j] = newRows.get(i)[j];
            }
        }
        A = newA;
    }

    private static void C() {
        int maxRow = 0;
        List<int[]> newCols = new ArrayList<>();
        int colLength = A[0].length;
        for (int j = 0; j < colLength; j++) {
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int[] ints : A) {
                int num = ints[j];
                if (num != 0) {
                    map.put(num, map.getOrDefault(num, 0) + 1);
                }
            }

            List<Number> list = new ArrayList<>();
            for (int key : map.keySet()) {
                list.add(new Number(key, map.get(key)));
            }

            Collections.sort(list, (o1, o2) -> {
                if (o1.cnt == o2.cnt) {
                    return o1.num - o2.num;
                }
                return o1.cnt - o2.cnt;
            });

            List<Integer> newCol = new ArrayList<>();
            for (Number number : list) {
                newCol.add(number.num);
                newCol.add(number.cnt);
            }

            maxRow = Math.max(maxRow, newCol.size());
            newCols.add(newCol.stream().mapToInt(i -> i).toArray());
        }

        int[][] newA = new int[maxRow][newCols.size()];
        for (int j = 0; j < newCols.size(); j++) {
            for (int i = 0; i < newCols.get(j).length; i++) {
                newA[i][j] = newCols.get(j)[i];
            }
        }
        A = newA;
    }
}
