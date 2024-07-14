import java.util.*;
class Point{
    int x;
    int y;
    public Point(int x, int y){
        this.x=x;
        this.y=y;
    }
}
class Solution_게임맵최단거리 {
    int[] dx = {0, 0, -1, 1};
    int[] dy = {-1, 1, 0, 0};
    
    public void BFS(int[][] maps, int[][]visited){
        visited[0][0] = 1;
        Queue<Point> q = new LinkedList<>();
        q.add(new Point(0,0));
        
        while(!q.isEmpty()){
            int size = q.size();
            while(size-- > 0){
                Point now = q.poll();
                for(int i = 0; i<4; i++){
                    int nx = now.x+dx[i];
                    int ny = now.y+dy[i];
                    if(nx<0|| ny<0 || nx>maps.length-1 || ny>maps[0].length-1) continue;
                    if(visited[nx][ny]==0 && maps[nx][ny] == 1) {
                        visited[nx][ny] = visited[now.x][now.y]+1;
                        q.add(new Point(nx,ny));
                    }
                }
            }
        }
    }
    
    public int solution(int[][] maps) {
        int answer = 0;
        int[][] visited = new int[maps.length][maps[0].length];
        BFS(maps, visited);
        answer = visited[maps.length-1][maps[0].length-1];
        if(answer == 0) answer = -1;        
        return answer;
    }
}
