import java.io.*;
import java.util.*;

public class BAEKJOON13265 {
	
	static int[] colors;
	static ArrayList<ArrayList<Integer>> graph;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int tc = 0; tc<T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			colors = new int[n+1];
			graph = new ArrayList<>();
			
			for(int i = 0; i<=n; i++) {
				graph.add(new ArrayList<>());
			}
			for(int i = 0; i<m; i++) {
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				graph.get(x).add(y);
				graph.get(y).add(x);
			}
			
			boolean flag = true;
			
			for(int i = 1; i<=n; i++) {
				if(colors[i] == 0) {
					if(!DFS(i, 1)) {
						flag = false;
						break;
					}
				}
			}
			
			
			if(flag) {
				System.out.println("possible");
			}else {
				System.out.println("impossible");
			}
		}

	}

	public static boolean DFS(int node, int color) {
		colors[node] = color;
		
		for(int next : graph.get(node)) {
			if(colors[next] == 0) {
				if(!DFS(next, 3-color)) {
					return false;
				}
			}
			else if(colors[next] == colors[node]) {
				return false;
			}
		}
		return true;
	}



}
