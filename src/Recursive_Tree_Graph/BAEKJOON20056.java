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
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		for(int i = 1; i<=M; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			
		}
		
	}

}
