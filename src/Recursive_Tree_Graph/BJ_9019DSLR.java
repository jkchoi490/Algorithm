package Recursive_Tree_Graph;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class DSLRNode{
	int num;
	String str;
	public DSLRNode(int num, String str) {
		this.num=num;
		this.str=str;
	}
}
public class BJ_9019DSLR {
	static int T;
	static String[] DSLRarr = {"D","S","L","R"};
	
	public static String BFS(int A, int B) {
		Queue<DSLRNode> Q = new LinkedList<>();
		boolean[] visited = new boolean[10000];
		Q.add(new DSLRNode(A, ""));
		
		while(!Q.isEmpty()) {
			DSLRNode now = Q.poll();
			int num = now.num;
			String numstr = now.str;
			if(num == B) {
				return now.str;
			}
			for(int i = 0; i<DSLRarr.length; i++) {
				int next = DSLR(now, DSLRarr[i]);
				if(!visited[next]) {
					visited[next] = true;
					Q.add(new DSLRNode(next, numstr+DSLRarr[i]));
					
				}
			}
			
		}
		return "";
		
	}
	
	public static int DSLR(DSLRNode now, String type) {
		switch(type) {
	
		case "D": return (now.num *2)%10000;
		case "S": return now.num == 0 ? 9999: now.num-1;
		case "L": return (now.num % 1000)*10 + (now.num/1000);
		case "R": return (now.num % 10) * 1000 + (now.num /10);
		}
		
		return 0;
		
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
	
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i<T; i++) {
			st = new StringTokenizer(br.readLine());
			
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			String result = BFS(A, B);
			sb.append(result+ "\n");
		}
		System.out.println(sb.toString());
	}

}
