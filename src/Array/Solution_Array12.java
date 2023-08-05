package Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_Array12 {
	
	static int[][] students;
	static int n, m;
	
	public static int solution(int[][] students) {
		int ans = 0;
	
		for(int i = 1; i<=n; i++) { 
			for(int j = 1; j<=n; j++) {
				int cnt = 0;
				for(int k = 0; k<m; k++) { //테스트 개수 
					int pi=0, pj = 0;
					for(int s = 0; s<n; s++) { //학생수 
						if(students[k][s] == i) pi = s; //i의 등수
						if(students[k][s] == j) pj = s; //j의 등수
					}
					if(pi<pj) cnt++; // i가 멘토, j가 멘티 -> i의 등수가 j의 등수보다 높을경우 cnt 증가 
				}
				if(cnt==m) {
					ans++;
				}
			}
		}
		
		return ans;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		students = new int[m][n];
		for(int i = 0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<n; j++) {
				students[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		System.out.println(solution(students));
	}

}