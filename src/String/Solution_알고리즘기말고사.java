
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_알고리즘기말고사 { //알고리즘 기말고사
	static int answer = 0;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] score = new int[N+1];
		for(int i = 1; i<=N; i++) {
			score[i] = Integer.parseInt(st.nextToken());
		}
		
		for(int i = 1; i<=N; i++) {
			answer = i-score[i]; 
			System.out.println(answer);
		}

	}

}
