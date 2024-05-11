import java.io.*;
import java.util.*;

public class BAEKJOON19941 {

	static int N,K;
	static char[] arr;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		arr = new char[N];
		String str = br.readLine();
		for(int i = 0; i<N; i++) arr[i] = str.charAt(i);
		
		int answer = 0;
		for(int i = 0; i<N; i++) {
			if(arr[i]=='P') {
				for(int j = Math.max(0, i-K); j<Math.min(N, i+K+1); j++) {
					if(arr[j] == 'H') {
						arr[j] = 'E';
						answer++;
						break;
					}
				}
			}
		}
		System.out.println(answer);
	}

}
