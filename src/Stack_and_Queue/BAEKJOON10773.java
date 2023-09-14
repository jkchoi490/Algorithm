package Stack_and_Queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class BAEKJOON10773 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Stack<Integer> stack = new Stack<>();
		int k = Integer.parseInt(br.readLine());
		for(int i = 0; i<k; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			if(n == 0) stack.pop();
			else stack.push(n);
		}
		int ans = 0;
		for(int x : stack) ans += x;
		System.out.println(ans);
	}

}
