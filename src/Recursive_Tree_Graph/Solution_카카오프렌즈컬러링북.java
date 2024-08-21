import java.util.*;

class Solution_카카오프렌즈컬러링북 {
    static boolean[][] visited;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    public int[] solution(int m, int n, int[][] picture) {
        int numberOfArea = 0;
        int maxSizeOfOneArea = 0;

        int[] answer = new int[2];
        visited = new boolean[m][n];

        for(int i = 0; i<m; i++){
            for(int j = 0; j<n; j++){
                if(picture[i][j] >0 && !visited[i][j]){
                   maxSizeOfOneArea = Math.max(maxSizeOfOneArea, BFS(i, j, m, n, picture));
                numberOfArea++;   
                }
            }
        }    
        
        answer[0] = numberOfArea;
        answer[1] = maxSizeOfOneArea;
        return answer;
    }
    
    public int BFS(int x, int y, int m, int n, int[][] picture){
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{x, y});
        visited[x][y] = true;
        int area = 1;
        
        while(!q.isEmpty()){
            int[] cur = q.poll();
            for(int d = 0; d<4; d++){
                int nx = cur[0] + dx[d];
                int ny = cur[1] + dy[d];
                if(nx>=0 && nx<m && ny>=0 && ny<n && !visited[nx][ny] && picture[nx][ny] == picture[cur[0]][cur[1]]){
                    area++;
                    visited[nx][ny] = true;
                    q.offer(new int[]{nx, ny});
                }
            }
        }
        return area;
    }
}