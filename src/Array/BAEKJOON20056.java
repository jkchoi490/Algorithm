import java.io.*;
import java.util.*;

class Fireball{
	int r,c,m,s,d;
	public Fireball(int r, int c, int m, int s, int d) {
		this.r=r;
		this.c=c;
		this.m=m;
		this.s=s;
		this.d=d;
	}
}

public class BAEKJOON20056 {
	static int N,M,K;
	static int[] dx = {-1, -1,0,1,1,1,0,-1};
	static int[] dy = {0,1,1,1,0,-1,-1,-1};
	static ArrayList<Fireball>[][] map;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new ArrayList[N][N];

		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				map[i][j] = new ArrayList<Fireball>();
			}
		}
		
		for(int i = 0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			map[r][c].add(new Fireball(r,c,m,s,d));
		}
		while(K-->0) {
			moveFireball();
			step2();
		}
		System.out.println(getSum());
	}
	
	private static void moveFireball() {
		ArrayList<Fireball>[][] newMap = new ArrayList[N][N];
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				newMap[i][j] = new ArrayList<Fireball>();
			}
		}
		
		
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				if(map[i][j].size() > 0) {
					for(int k = 0; k<map[i][j].size(); k++) {
						Fireball ball = map[i][j].get(k);
						int nx = (i + dx[ball.d] * ball.s %N + N) % N;
						int ny = (j + dy[ball.d] * ball.s %N + N) % N;
						newMap[nx][ny].add(new Fireball(nx, ny, ball.m, ball.s, ball.d));
					}
				}
			}
		}
		map = newMap;
		
	}
	private static void step2() {
		ArrayList<Fireball>[][] newMap = new ArrayList[N][N];
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				newMap[i][j] = new ArrayList<Fireball>();
			}
		}
		
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				if(map[i][j].size() >=2) {
					
					int mSum = 0, sSum = 0, cnt = map[i][j].size();
					boolean allEven = true, allOdd = true;

					for(Fireball ball : map[i][j]) {
						mSum += ball.m;
						sSum += ball.s;
						if(ball.d % 2 == 0) {
							allOdd = false;
						}
						else {
							allEven = false;
						}
					}
					
					int m = mSum/5;
					int s = sSum/cnt;
					
						
					
					int[] newdir = new int[4];
					if(allEven || allOdd) {
						for(int d = 0; d<4; d++) {
							newdir[d] = d*2;
						}
					}
					else {
						for(int d = 0; d<4; d++) {
							newdir[d] = d*2+1;
						}
					}
					
					for(int dir : newdir) {
						if(m > 0) {
							newMap[i][j].add(new Fireball(i,j,m,s,dir));
						}
					}
					
					
					
				}else if(map[i][j].size() == 1) {
					newMap[i][j].add(map[i][j].get(0));
				}
				
				
			}
		}
		map = newMap;
	}
	
	private static int getSum() {
		int result = 0;
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				for(Fireball ball : map[i][j]) {
					result += ball.m;
						}
					}
				}
		return result;
		
	}
	
	

}
