import java.io.*;
import java.util.*;

public class BAEKJOON17615 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		char[] balls = br.readLine().toCharArray();
		
		int red = 0, blue = 0;
		for(char ball : balls) {
			if(ball=='R') {
				red++;
			}
			else blue++;
		}
		
		int answer = Math.min(red, blue);
		int cnt = 0;
		
		for(int i = 0; i<n-1; i++) {
			if(balls[i] != balls[0]) {
				break;
			}
			cnt++;
		}
		
		if(balls[0] == 'R') {
			answer = Math.min(answer, red-cnt);
		}else {
			answer = Math.min(answer, blue-cnt);
		}
		
		cnt = 0;
		
		for(int i = n-1; i>=0; i--) {
			if(balls[i] != balls[n-1]) {
				break;
			}
			cnt++;
		}
		
		if(balls[n-1]=='R') {
			answer = Math.min(answer, red-cnt);
		}else {
			answer = Math.min(answer, blue-cnt);
		}
		
		System.out.println(answer);
	}

}
