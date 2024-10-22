import java.io.*;
import java.util.*;

class Lecture {
    int start, end;

    public Lecture(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

public class BAEKJOON11000 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Lecture[] arr = new Lecture[N];
        
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            arr[i] = new Lecture(start, end);
        }

        Arrays.sort(arr, new Comparator<Lecture>() {
            @Override
            public int compare(Lecture o1, Lecture o2) {
                return o1.start - o2.start;
            }
        });

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        pq.offer(arr[0].end);

        for (int i = 1; i < N; i++) {
           
            if (arr[i].start >= pq.peek()) {
                pq.poll();
            }

            pq.offer(arr[i].end);
        }

        System.out.println(pq.size());
    }
}
