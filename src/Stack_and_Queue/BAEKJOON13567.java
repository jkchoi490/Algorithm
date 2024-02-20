import java.io.*;
import java.util.*;

class Robot{
	int x, y;
	public Robot(int x, int y) {
		this.x=x;
		this.y=y;
	}
}
public class BAEKJOON13567 {

	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {1, 0, -1, 0};
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int M = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());
		StringBuilder sb = new StringBuilder();
		Queue<Robot> q = new LinkedList<>();
		q.offer(new Robot(0,0));
		int d = 0;
		boolean flag = true;
		for(int i = 0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			String command = st.nextToken();
			int num = Integer.parseInt(st.nextToken());
			if(command.equals("MOVE")) {
				Robot robot = q.poll();
				int nx = robot.x + dx[d]*num;
				int ny = robot.y + dy[d]*num;
				q.offer(new Robot(nx,ny));
				if(nx < 0 || nx >M || ny<0 || ny>M) {
					flag = false;
				}
			}
			else if(command.equals("TURN")) {
				if(num == 0) {
					if(d == 3) d = 0;
					else d+=1;
				}
				else if(num==1) {
					if(d==0) d = 3;
					else d -= 1;
				}
			}
		}
		
		for(Robot r : q) {
			if(flag) sb.append(r.y).append(" ").append(r.x);
			else sb.append(-1);
		}
		System.out.println(sb);
	}

}
