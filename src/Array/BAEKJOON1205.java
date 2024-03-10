import java.io.*;
import java.util.*;

public class BAEKJOON1205 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int new_score = Integer.parseInt(st.nextToken());
		int P = Integer.parseInt(st.nextToken());
		Integer[] scores = new Integer[N];
	
		int answer = 0;
		if(N == 0) {
			answer = 1;
		}
		else {
			st = new StringTokenizer(br.readLine());
			for(int i = 0; i<N; i++) scores[i] = Integer.parseInt(st.nextToken());
			Arrays.sort(scores, Collections.reverseOrder());
			
			if(N==P && new_score <= scores[N-1]) answer = -1;
			else {
				int num = 1;
				for(int i = 0; i<N; i++) {
					if(new_score < scores[i]) num++;
					else break;
				}
				answer = num;
			}
			
	}
		System.out.println(answer);
}

}
