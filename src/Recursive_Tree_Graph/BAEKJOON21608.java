import java.io.*;
import java.util.*;

public class BAEKJOON21608 {

	static int N, answer = 0;;
	static int[][] map;
	static boolean[][] visit;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		visit = new boolean[N][N];
		map = new int[N][N];
		
		StringTokenizer st;
		HashMap<Integer, int[]> hm = new HashMap<>();
		for(int i = 0; i<N*N; i++) {
			st = new StringTokenizer(br.readLine());
			int student = Integer.parseInt(st.nextToken());
			int[] friends = new int[4];
			for(int j = 0; j<4; j++) {
				friends[j] = Integer.parseInt(st.nextToken());
			}
			hm.put(student, friends);
			solve(student, friends);
		}

		count(hm);
		System.out.println(answer);
	
	}
	public static void count(HashMap<Integer, int[]> hm) {
		int[][] result = new int[N][N];
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				int cnt = 0;
				for(int f : hm.get(map[i][j])) {
				for(int d = 0; d<4; d++) {
					int ni = i + dx[d];
					int nj = j + dy[d];
					if(ni>=0 && ni<N && nj>=0 && nj<N && map[ni][nj] ==f) {
						cnt++;
					}
				}
				}
				result[i][j] = cnt;
			}
			
		}
		int sum = 0;
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				if(result[i][j] == 0) sum += 0;
				else if(result[i][j] == 1) sum +=1;
				else if(result[i][j] == 2) sum +=10;
				else if(result[i][j] == 3) sum += 100;
				else if(result[i][j] == 4) sum += 1000;
			}
		}
		answer = sum;
		
	}
	public static void solve(int student, int[] friends) {
		
		ArrayList<int[]> list = new ArrayList<>();
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				int blank = 0;
				int friend = 0;
				for(int d = 0; d<4; d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];
					if(nx>=0 && nx<N && ny>=0 && ny<N && map[nx][ny] ==0 && !visit[nx][ny]) {
						blank++;
					}
					for(int k = 0; k<friends.length; k++) {
						if(nx>=0 && nx<N && ny>=0 && ny<N && map[nx][ny] ==friends[k]) {
							friend++;
						}
					}
				}
				
				if(!visit[i][j])list.add(new int[] {friend, blank, i, j});
			}
		}
		
		Collections.sort(list, new Comparator<int[]>() {

			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1[0] == o2[0]) {
					if(o2[1] == o1[1]) {
						if(o1[2]== o2[2]) {
							return o1[3] - o2[3];
						}
						return o1[2] - o2[2];
					}
					return o2[1] - o1[1];
				}
				return o2[0]- o1[0];
			}
			
		});
		
		map[list.get(0)[2]][list.get(0)[3]] = student;
		visit[list.get(0)[2]][list.get(0)[3]] = true;
	}

}
