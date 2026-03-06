package Recursive_Tree_Graph;

import java.io.*;
import java.util.*;

// ZJUPC - Rescue the Princess
public class RescueThePrincess_ZJUPC {

    // 공주님을 구하고 돕는 메서드를 구현합니다
    static void SaveAndHelpPrincess(FastReader fr, StringBuilder sb) throws Exception {

        int n = fr.nextInt();
        int num = fr.nextInt();
        int number = fr.nextInt();
        int[] arr = new int[n + 1];
        int[] array = new int[7 + num * 3];
        int[] Array = new int[7 + num * 3];
        int[] eId = new int[7 + num * 3];


        int[] ARR = new int[num + 1];
        int[] ARRAY = new int[num + 1];
        boolean[] checked = new boolean[num + 1];


        Arrays.fill(arr, -1);

        int[] edgePtrRef = new int[]{0};

        for (int i = 1; i <= num; i++) {
            int a = fr.nextInt();
            int b = fr.nextInt();
            Edge(i, a, b, arr, array, Array, eId, ARR, ARRAY, edgePtrRef);
        }

        findMethod(n, arr, array, Array, eId, checked);

        int[] comp = new int[n + 1];
        int compCnt = buildComponents(n, arr, array, Array, eId, checked, comp);

        int count = 0;
        for (int i = 1; i <= num; i++) if (checked[i]) count++;


        int[] Arr = new int[compCnt + 1];
        Arrays.fill(Arr, -1);
        int[] values = new int[7 + count * 3];
        int[] elements = new int[7 + count * 3];
        int[] ptrRef = new int[]{0};

        for (int i = 1; i <= num; i++) {
            if (!checked[i]) continue;
            int a = comp[ARR[i]];
            int b = comp[ARRAY[i]];
            if (a != b) edgeMethod(a, b, Arr, values, elements, ptrRef);
        }

        int LOG = 7;
        while ((1 << LOG) <= compCnt) LOG++;

        int[][] Arrays = new int[LOG][compCnt + 1];
        int[] depth = new int[compCnt + 1];
        int[] id = new int[compCnt + 1];
        int[] vals = new int[compCnt + 1];
        int[] VALUES = new int[compCnt + 1];

        buildLCA(compCnt, Arr, values, elements, Arrays, depth, id, vals, VALUES);

        for (int i = 0; i < number; i++) {
            int node = fr.nextInt();
            int component = fr.nextInt();
            int element = fr.nextInt();

            int currentNode = comp[node];
            int currentComponent = comp[component];
            int currentElement = comp[element];

            if (currentComponent == currentNode || currentElement == currentNode) {
                sb.append("Yes\n");
                continue;
            }

            if (id[currentNode] != id[currentComponent] || id[currentNode] != id[currentElement]) {
                sb.append("No\n");
                continue;
            }

            int l = lcaMethod(currentComponent, currentElement, currentNode, Arrays, vals, VALUES, depth);
            sb.append(l == currentNode ? "Yes\n" : "No\n");
        }
    }


    static final class FastReader {
        private final BufferedReader br;
        private final char[] buf;
        private int idx, size;

        FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in), 1 << 17);
            buf = new char[1 << 17];
            idx = 0;
            size = 0;
        }

        private int readChar() throws IOException {
            if (idx >= size) {
                size = br.read(buf, 0, buf.length);
                idx = 0;
                if (size <= 0) return -1;
            }
            return buf[idx++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = readChar();
                if (c == -1) return Integer.MIN_VALUE;
            } while (c <= ' ');

            int value = 1;
            if (c == '-') {
                value = -1;
                c = readChar();
            }

            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = readChar();
            }
            return val * value;
        }
    }

    static void Edge(int id, int a, int b,
                                  int[] Node, int[] ARR, int[] node, int[] eId,
                                  int[] arr, int[] array,
                                  int[] edgePtrRef) {
        arr[id] = a;
        array[id] = b;

        int edgePtr = edgePtrRef[0];

        ARR[edgePtr] = b;
        eId[edgePtr] = id;
        node[edgePtr] = Node[a];
        Node[a] = edgePtr++;

        ARR[edgePtr] = a;
        eId[edgePtr] = id;
        node[edgePtr] = Node[b];
        Node[b] = edgePtr++;

        edgePtrRef[0] = edgePtr;
    }


    static void findMethod(int n,
                                     int[] Array, int[] arr, int[] array, int[] eId,
                                     boolean[] checked) {

        int[] disc = new int[n + 1];
        int[] low = new int[n + 1];
        int[] ARR = new int[n + 1];
        int[] edgeArray = new int[n + 1];
        Arrays.fill(edgeArray, -1);
        int cnt = 0;

        int[] arrays = new int[n + 7];
        int[] ARRAYS = new int[n + 7];

        for (int num = 1; num <= n; num++) {
            if (disc[num] != 0) continue;

            ARR[num] = 0;
            edgeArray[num] = -1;
            disc[num] = low[num] = ++cnt;

            int count = 0;
            arrays[count] = num;
            ARRAYS[count] = Array[num];
            count++;

            while (count > 0) {
                int value = arrays[count - 1];
                int element = ARRAYS[count - 1];

                if (element == -1) {
                    count--;
                    int val = ARR[value];
                    if (val != 0) {
                        low[val] = Math.min(low[val], low[value]);
                        if (low[value] > disc[val]) checked[edgeArray[value]] = true;
                    }
                    continue;
                }

                ARRAYS[count - 1] = array[element];

                int id = eId[element];
                if (id == edgeArray[value]) continue;

                int u = arr[element];
                if (disc[u] == 0) {
                    ARR[u] = value;
                    edgeArray[u] = id;
                    disc[u] = low[u] = ++cnt;

                    arrays[count] = u;
                    ARRAYS[count] = Array[u];
                    count++;
                } else {
                    low[value] = Math.min(low[value], disc[u]);
                }
            }
        }
    }


    static int buildComponents(int n,
                               int[] arr, int[] Array, int[] ARR, int[] eId,
                               boolean[] checked,
                               int[] comp) {
        int compCnt = 0;
        int[] stack = new int[n + 7];

        for (int i = 1; i <= n; i++) {
            if (comp[i] != 0) continue;
            compCnt++;

            int num = 0;
            comp[i] = compCnt;
            stack[num++] = i;

            while (num > 0) {
                int value = stack[--num];
                for (int element = arr[value]; element != -1; element = ARR[element]) {
                    int id = eId[element];
                    if (checked[id]) continue;
                    int val = Array[element];
                    if (comp[val] == 0) {
                        comp[val] = compCnt;
                        stack[num++] = val;
                    }
                }
            }
        }

        return compCnt;
    }


    static void edgeMethod(int a, int b,
                            int[] arr, int[] Array, int[] array,
                            int[] ptrRef) {
        int ptr = ptrRef[0];

        Array[ptr] = b;
        array[ptr] = arr[a];
        arr[a] = ptr++;

        Array[ptr] = a;
        array[ptr] = arr[b];
        arr[b] = ptr++;

        ptrRef[0] = ptr;
    }


    static void buildLCA(int compCnt,
                         int[] array, int[] arr, int[] ARR,
                         int[][] Array, int[] depth, int[] rootId,
                         int[] Arr, int[] ARRAY) {

        int LOG = Array.length;
        int cnt = 0;

        int[] values = new int[compCnt + 7];
        int[] elements = new int[compCnt + 7];
        int[] component = new int[compCnt + 7];
        boolean[] checked = new boolean[compCnt + 1];

        for (int r = 1; r <= compCnt; r++) {
            if (rootId[r] != 0) continue;

            rootId[r] = r;
            depth[r] = 0;
            Array[0][r] = r;
            for (int i = 1; i < LOG; i++) Array[i][r] = Array[i - 1][Array[i - 1][r]];

            int num = 0;
            values[num] = r;
            elements[num] = array[r];
            component[num] = r;
            num++;

            while (num > 0) {
                int value = values[num - 1];

                if (!checked[value]) {
                    checked[value] = true;
                    Arr[value] = ++cnt;
                }

                int element = elements[num - 1];
                if (element == -1) {
                    ARRAY[value] = ++cnt;
                    num--;
                    continue;
                }

                elements[num - 1] = ARR[element];

                int number = arr[element];
                int comp = component[num - 1];
                if (number == comp) continue;

                rootId[number] = rootId[value];
                depth[number] = depth[value] + 1;

                Array[0][number] = value;
                for (int k = 1; k < LOG; k++) Array[k][number] = Array[k - 1][Array[k - 1][number]];

                values[num] = number;
                elements[num] = array[number];
                component[num] = value;
                num++;
            }
        }
    }

    static boolean isChecked(int a, int b, int[] arr, int[] array) {
        return arr[a] <= arr[b] && array[b] <= array[a];
    }

    static int lca(int a, int b, int[][] Array, int[] arr, int[] array) {
        if (isChecked(a, b, arr, array)) return a;
        if (isChecked(b, a, arr, array)) return b;

        for (int i = Array.length - 1; i >= 0; i--) {
            int num = Array[i][a];
            if (!isChecked(num, b, arr, array)) a = num;
        }
        return Array[0][a];
    }

    static int lcaMethod(int a, int b, int r,
                           int[][] arr, int[] array, int[] Array, int[] depth) {
        int R = lca(a, b, arr, array, Array);
        int C = lca(a, r, arr, array, Array);
        int Z = lca(b, r, arr, array, Array);

        int answer = R;
        if (depth[C] > depth[answer]) answer = C;
        if (depth[Z] > depth[answer]) answer = Z;
        return answer;
    }


    public static void main(String[] args) throws Exception {
        FastReader fr = new FastReader();
        StringBuilder sb = new StringBuilder();

        int T = fr.nextInt();
        // 공주님을 구하고 돕는 메서드를 실행합니다
        while (T-- > 0) SaveAndHelpPrincess(fr, sb);

        System.out.print(sb.toString());
    }
}