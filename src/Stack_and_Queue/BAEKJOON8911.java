import java.awt.Point;
import java.io.*;
import java.util.*;

class Turtle{
	int x, y, dir;
	public Turtle(int x, int y, int dir) {
		this.x=x;
		this.y=y;
		this.dir=dir;
	}
}
public class BAEKJOON8911 {

	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {1, 0, -1, 0};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		int x = 0, y = 0, d = 0;
		StringBuilder sb = new StringBuilder();
		for(int tc = 1; tc<=T; tc++) {
			char[] control = br.readLine().toCharArray();
			ArrayList<Point> points = new ArrayList<>();
			Queue<Turtle> q = new LinkedList<>();
			q.offer(new Turtle(x,y,d));
			points.add(new Point(x,y));
			for(char c : control) {
				if(c=='F') {
					Turtle turtle = q.poll();
					int nx = turtle.x + dx[d];
					int ny = turtle.y + dy[d];
					points.add(new Point(nx,ny));
					q.offer(new Turtle(nx,ny,d));
				}
				else if(c=='B') {
					Turtle turtle = q.poll();
					int nx = turtle.x - dx[d];
					int ny = turtle.y - dy[d];
					points.add(new Point(nx,ny));
					q.offer(new Turtle(nx,ny,d));
				}
				else if(c=='L') {
					if(d == 0) d = 3;
					else d -= 1;
				}
				else if(c=='R') {
					if(d == 3) d = 0;
					else d += 1;
				}
			}
			int max_x = 0, max_y = 0, min_x = 0, min_y = 0;
			for(Point p : points) {
				max_x = Math.max(max_x, p.x);
				max_y = Math.max(max_y, p.y);
				min_x = Math.min(min_x, p.x);
				min_y = Math.min(min_y, p.y);
			}
			
			if((max_x == 0 && min_x == 0)||(max_y == 0 && min_y == 0)) sb.append(0).append("\n");
			else {
				int garo = Math.abs(max_x-min_x);
				int sero = Math.abs(max_y-min_y);
				int area = garo*sero;
				sb.append(area).append("\n");
			}
			
		}
		System.out.println(sb);

	}

}
