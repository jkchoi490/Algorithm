import java.io.*;
import java.util.*;

public class BAEKJOON1516 {
    static int N;
    static List<List<Integer>> l = new ArrayList<>();
    static int[] time;
    static int[] parentNum;
    static int[] answer;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        time = new int[N+1];
        parentNum = new int[N+1];
        answer = new int[N+1];
        for(int i = 0; i<=N; i++)
            l.add(new ArrayList<>());

        for(int i = 1; i<=N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());

            time[i] = Integer.parseInt(st.nextToken());

            while(st.hasMoreTokens()){
                int num = Integer.parseInt(st.nextToken());

                if(num == -1) break;

                l.get(num).add(i);
                parentNum[i]++;
            }
        }

        topologicalSort(); 

        for(int i = 1; i<=N; i++) System.out.println(answer[i]);
    }

    static void topologicalSort(){ 
        Queue<Integer> q = new LinkedList<>();

        for(int i = 1; i<=N; i++)
            if(parentNum[i] == 0)
                q.offer(i);

        while(!q.isEmpty()){
            int vertex = q.poll();
            answer[vertex] += time[vertex];

            for(int i = 0; i<l.get(vertex).size(); i++){
                int nextV = l.get(vertex).get(i);

                parentNum[nextV]--;
                if(parentNum[nextV] == 0)
                    q.offer(nextV);

                answer[nextV] = Math.max(answer[nextV], answer[vertex]);
            }
        }
    }
}