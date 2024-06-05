import java.io.*;
import java.util.*;

class Pair{
	String S, T;
	int cnt;
	public Pair(String S, String T, int cnt) {
		this.S=S;
		this.T=T;
		this.cnt=cnt;
	}
}

public class BAEKJOON2179 {
	
	static int N;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		String[] arr = new String[N];
		for(int i = 0; i<N; i++) {
			arr[i] = br.readLine();
		}
		int max = 0;
		ArrayList<Pair> list = new ArrayList<>();
		for(int i = 0; i<N; i++) {
			String cur = arr[i];

			for(int j = 0; j<N; j++) {
				if(i==j) continue;
				String next = arr[j];
				int cnt = 0;
				for(int k = 0; k<Math.min(cur.length(), next.length()); k++) {
					if(cur.charAt(k) == next.charAt(k)) cnt++;
				}

				if(cnt == max) list.add(new Pair(cur, next, cnt));
				else if(cnt > max) {
					max = cnt;
					list = new ArrayList<>();
					list.add(new Pair(cur, next, cnt));
				}
			}
		}
		
		
		System.out.println(list.get(0).S);
		System.out.println(list.get(0).T);
		
		
		
	}

}
