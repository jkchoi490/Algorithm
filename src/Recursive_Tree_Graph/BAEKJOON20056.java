import java.io.*;
import java.util.*;

class Fireball{
	int r,c, m, s, d;
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
	static ArrayList<Fireball> list = new ArrayList<>();
	static	ArrayList<Fireball>[][] map;
	static int[] dx = {-1,-1,0,1,1,1,0,-1};
	static int[] dy = {0,1,1,1,0,-1,-1,-1};
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
		
		
		for(int i = 1; i<=M; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			list.add(new Fireball(r,c,m,s,d));
		}
		
		while(K-- > 0) {
			move();
			divide();
		}
		int answer = 0;
		for(Fireball fb : list) {
			answer += fb.m;
		}
		System.out.println(answer);
	}

	public static void move() {
		
		for(Fireball fb : list) {
			int nx = (fb.r+N+dx[fb.d]*(fb.s % N))%N;
			int ny = (fb.c+N+dy[fb.d]*(fb.s % N))%N;
			//System.out.println(nx+" "+ny+" ");
			map[nx][ny].add(new Fireball(nx, ny,fb.m,fb.s,fb.d));
					
		}
	}
	
	public static void divide() {
		
		
	}
	

}
