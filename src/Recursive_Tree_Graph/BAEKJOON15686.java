import java.io.*;
import java.util.*;

class Point{
	int x, y;
	public Point(int x, int y) {
		this.x=x;
		this.y=y;
	}
}
public class BAEKJOON15686 {
	static int N,M, answer = Integer.MAX_VALUE;
	static ArrayList<Point> home, chicken;
	static Point[] combi;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		home = new ArrayList<>();
		chicken = new ArrayList<>();
		combi = new Point[M];
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++) {
				int x = Integer.parseInt(st.nextToken());
				if(x == 1) home.add(new Point(i,j));
				else if(x == 2) chicken.add(new Point(i,j));
			}
		}
		
		DFS(0,0);
		System.out.println(answer);
	}

	public static void DFS(int L, int s) {
		if(L==M) {
			int sum = 0;
			for(int h = 0; h<home.size(); h++) {
				int dist = Integer.MAX_VALUE;
				for(int c = 0; c<M; c++) {
					int tmp = Math.abs(home.get(h).x-combi[c].x)+Math.abs(home.get(h).y - combi[c].y);
					dist = Math.min(dist, tmp);
				}
				sum += dist;
			}
			answer = Math.min(answer, sum);
			return;
		}
		else {
			if(s==chicken.size()) return;
			for(int i = s; i<chicken.size(); i++) {
				combi[L] = chicken.get(i);
				DFS(L+1, i+1);
			}
		}
	}
}	
