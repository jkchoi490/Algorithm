import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.Vector;

public class BAEKJOON1766 {
    static int n,m;
    static int[] Entry;
    static Vector<Integer>[] Problem;

    public static void main(String[] args) throws IOException {
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
     
        Entry = new int[n + 1];
        Problem = new Vector[n + 1];
        for (int i = 1; i <= n; i++) {
            Problem[i] = new Vector<>();
        }
        for (int i = 0; i < m; i++) {
        	st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            Problem[a].add(b);
            Entry[b]++;
        }
       
        Solution();
    }

    public static void Solution() {
        PriorityQueue<Integer> Q = new PriorityQueue<>();
        for (int i = 1; i <= n; i++) {
            if (Entry[i] == 0) Q.add(-i);
        }
        while (!Q.isEmpty()) {
            int cur = -Q.poll();
            System.out.print(cur + " ");
            for (int i = 0; i < Problem[cur].size(); i++) {
                int Next = Problem[cur].get(i);
                Entry[Next]--;
                if (Entry[Next] == 0) Q.add(-Next);
            }
        }
        System.out.println();
    }
}