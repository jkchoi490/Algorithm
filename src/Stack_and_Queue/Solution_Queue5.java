import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution_Queue5 { // 문제집
	
	 public static void main(String[] args) throws Exception {
		 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 StringTokenizer st = new StringTokenizer(br.readLine());
	        int N = Integer.parseInt(st.nextToken());
	        int M = Integer.parseInt(st.nextToken());
	        int[] indegree = new int[N+1];
	        ArrayList<Integer>[] list = new ArrayList[N+1];    //LinkedList 사용해도 상관 없음
	        for(int i=1; i<=N; i++){
	            list[i] = new ArrayList<Integer>();
	        }
	        for(int i=0; i<M; i++){
	        	st = new StringTokenizer(br.readLine());
	            int A = Integer.parseInt(st.nextToken());
	            int B = Integer.parseInt(st.nextToken());
	            list[A].add(B);    //리스트 안의 리스트에 값을 넣어준다.
	            indegree[B]++;    //자신을 가르키고 있는 화살표의 갯수
	        }
	        PriorityQueue<Integer> q = new PriorityQueue<Integer>();
	        
	        for(int i=1; i<=N; i++){
	            //indegree가 0인 값들 모두 큐에 넣어준다.
	            if(indegree[i]==0){
	                q.add(i);
	            }
	        }
	        while(!q.isEmpty()){
	            //indgree가 0인 값을 큐에서 뽑는다.
	            int current = q.poll();
	            System.out.print(current+" ");
	            //뽑힌 곳에서 갈 수 있는 곳들을 검색하여 indegree를 -1한다.(자신을 가르키는 화살표가 하나 사라졌기 때문에)
	            for(int i=0; i<list[current].size(); i++){
	                int next = list[current].get(i);
	                indegree[next]--;
	                //indegree가 0이라면 큐에 넣는다.
	                if(indegree[next]==0){
	                    q.add(next);
	                }
	            }
	        }
	    }
	 
}
