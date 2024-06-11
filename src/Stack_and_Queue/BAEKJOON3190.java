import java.io.*;
import java.util.*;

class Dir {
    int time;
    char dir;

    public Dir(int time, char dir) {
        this.time = time;
        this.dir = dir;
    }
}

public class BAEKJOON3190 {
    static int[][] map;
    static Deque<int[]> dq;
    static Queue<Dir> directions;
    static int n, k, L;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};
    static int d = 0; 
    static int time = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        dq = new LinkedList<>();
        directions = new LinkedList<>();
        n = Integer.parseInt(br.readLine());
        k = Integer.parseInt(br.readLine());
        map = new int[n][n];
        
        for (int i = 0; i < k; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            map[x][y] = 2;
        }
        
        L = Integer.parseInt(br.readLine());
        for (int i = 0; i < L; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            char dir = st.nextToken().charAt(0);
            directions.add(new Dir(t, dir));
        }

        dq.offer(new int[]{0, 0});
        map[0][0] = 1; 

        while (true) {
            time++;
            if (!move()) {
                break;
            }
        }

        System.out.println(time);
    }

    private static boolean move() {
        int[] head = dq.peekFirst();
        int nx = head[0] + dx[d];
        int ny = head[1] + dy[d];

        if (nx < 0 || nx >= n || ny < 0 || ny >= n || map[nx][ny] == 1) {
            return false;
        }

        if (map[nx][ny] == 2) {
            map[nx][ny] = 1; 
            dq.offerFirst(new int[]{nx, ny});
        } else { 
            map[nx][ny] = 1;
            dq.offerFirst(new int[]{nx, ny});
            int[] tail = dq.pollLast();
            map[tail[0]][tail[1]] = 0;
        }

        
        if (!directions.isEmpty() && directions.peek().time == time) {
            Dir dir = directions.poll();
            d = changeDirection(dir.dir);
        }

        return true;
    }

    public static int changeDirection(char dir) {
    	
    	if(dir == 'L') { 
			if(d==0) d = 3;
			else d -= 1;
		}
		else if(dir == 'D') {
			if(d==3) d = 0;
			else d+=1;
		}
    	
    	return d;
    }
}
