import java.io.*;
import java.util.*;

public class BAEKJOON19583 {
	static int S,E,Q, cnt = 0;
	static HashSet<String> inlist, outlist;
	public static void main(String[] args) throws IOException{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		S = getTime(st.nextToken());
		E = getTime(st.nextToken());
		Q = getTime(st.nextToken());
		
		inlist = new HashSet<>();
		outlist = new HashSet<>();

		String str = null;
		while((str= br.readLine()) != null) {
			String[] arr = str.split(" ");
			int time = getTime(arr[0]);
			String id = arr[1];
			solve(time, id);
		}
		count();
		System.out.println(cnt);
	}
	public static void solve(int time, String id) {
		if(time>=0 && time<=S){
			inlist.add(id);
			
		}
		
		if(time>=E && time<=Q) {
			outlist.add(id);
		}
	}
	
	private static void count() {
		for(String id : inlist) {
			if(outlist.contains(id)) cnt++;
		}
	}

	public static int getTime(String time) {
		int H = Integer.parseInt(time.split(":")[0]);
		int M = Integer.parseInt(time.split(":")[1]);
		return H*60+M;
	}

}
