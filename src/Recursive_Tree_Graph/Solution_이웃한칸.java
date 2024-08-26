import java.util.*;

class Color{
    int x, y;
    String color;
    public Color(int x, int y, String color){
        this.x=x;
        this.y=y;
        this.color=color;
    }
}
class Solution_이웃한칸 {
    public int solution(String[][] board, int h, int w) {
        int answer = 0;
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        Queue<Color> q = new LinkedList<>();
        q.offer(new Color(h, w, board[h][w]));
        while(!q.isEmpty()){
            Color color = q.poll();
            for(int d = 0; d<4; d++){
                int nx = color.x+dx[d];
                int ny = color.y+dy[d];
                if(nx>=0 && nx<board.length && ny>=0 && ny<board[0].length 
                   && color.color.equals(board[nx][ny])){
                   answer++; 
                }
            }
        }
        return answer;
    }
}