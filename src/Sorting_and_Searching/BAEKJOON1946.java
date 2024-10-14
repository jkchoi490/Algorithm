import java.io.*;
import java.util.*;

class person{
	int score1, score2;
	public person(int score1, int score2) {
		this.score1=score1;
		this.score2=score2;
	}
}

public class BAEKJOON1946 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		
		for(int tc = 1; tc<=T; tc++) {
			int N = Integer.parseInt(br.readLine());
			ArrayList<person> list = new ArrayList<>();
			for(int i = 0; i<N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				int score1 = Integer.parseInt(st.nextToken());
				int score2 = Integer.parseInt(st.nextToken());
				list.add(new person(score1, score2));
			}
			
			Collections.sort(list, new Comparator<person>() {

				@Override
				public int compare(person o1, person o2) {
					return o1.score1 - o2.score1;
				}
			});
		
			int cnt = 1; 
			int min_score2 = list.get(0).score2; 
			for(int j = 1; j<list.size(); j++) {
				int score2 = list.get(j).score2;
				
				if(min_score2 > score2) {
					cnt++;
					min_score2 = score2; 
				}
			
			}
			System.out.println(cnt);
			
		}

	}

}
