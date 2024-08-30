import java.util.*;

class Solution_전력망을둘로나누기 {
    static int[][] arr;
    public int solution(int n, int[][] wires) {
        int answer = Integer.MAX_VALUE;
        arr = new int[n+1][n+1];
        for(int[] wire : wires){
            arr[wire[0]][wire[1]] = 1;
            arr[wire[1]][wire[0]] = 1;
        }
        
        for(int i = 0; i<wires.length; i++){
            int a = wires[i][0];
            int b = wires[i][1];
           arr[a][b] = 0;
           arr[b][a] = 0;
            answer = Math.min(answer, BFS(n, a));   
            arr[a][b] = 1;
            arr[b][a] = 1;
        }
        
        return answer;
    }
    
    public int BFS(int n, int cur){
    Queue<Integer> q = new LinkedList<>();
    boolean[] visited = new boolean[n+1];
        int cnt = 1;
    q.offer(cur);
    while(!q.isEmpty()){
        int now = q.poll();
        visited[now] = true;
        for(int i = 1; i<=n; i++){
            if(visited[i]==true)continue;
            if(arr[now][i] == 1){
                q.offer(i);
                cnt++;
            }
        }
    }
        
    return Math.abs(n-cnt - cnt);    
        }
}