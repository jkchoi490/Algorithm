class Solution_네트워크 {
  static int ans,n;
	static boolean[] visited;
    static int len = 0;
	
	public static void DFS(int v, int[][] computers, boolean[] visited) {
		if(v>len) return;
		else {
        for(int i = 0; i < len; i++) {
            if(visited[i] == false && computers[v][i] == 1) {
            	visited[i] = true;
            	DFS(i, computers,visited);
            }
        }
		}
	}
	
	public static int solution(int n, int[][] computers) {
		ans= 0;
        len = computers[0].length;
        visited = new boolean[len];        
        for(int i = 0; i <len; i++) {
            if(visited[i] == false) {                                   
                DFS(i, computers,visited);
                ans++;                                            
            }
        }
        
     return ans;
		
	}
}