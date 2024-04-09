import java.io.*;
import java.util.*;

public class BAEKJOON14500 {

	static int N,M;
	static int[][] board;
	static int[][][] t = {
            {{0, 1}, {0, 2}, {0, 3}},
            {{1, 0}, {2, 0}, {3, 0}},
            {{0, 1}, {1, 0}, {1, 1}},
            {{1, 0}, {2, 0}, {2, 1}},
            {{0, 1}, {0, 2}, {1, 0}},
            {{0, 1}, {1, 1}, {2, 1}},
            {{0, 1}, {0, 2}, {-1, 2}},
            {{0, 1}, {-1, 1}, {-2, 1}},
            {{1, 0}, {1, 1}, {1, 2}},
            {{0, 1}, {1, 0}, {2, 0}},
            {{0, 1}, {0, 2}, {1, 2}},
            {{1, 0}, {1, 1}, {2, 1}},
            {{0, 1}, {-1, 1}, {-1, 2}},
            {{1, 0}, {1, 1}, {2, 1}},
            {{0, 1}, {1, 0}, {-1, 1}},
            {{0, 1}, {1, 1}, {1, 2}},
            {{0, 1}, {0, 2}, {1, 1}},
            {{-1, 1}, {0, 1}, {1, 1}},
            {{0, 1}, {0, 2}, {-1, 1}},
            {{1, 0}, {2, 0}, {1, 1}}
        };
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st=  new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		board = new int[N][M];
		for(int i = 0; i<N; i++) {
			st=  new StringTokenizer(br.readLine());
			for(int j = 0; j<M; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int answer = 0;
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<M; j++) {
				for(int[][] pos : t) {
					int sum = getSum(i, j, pos);
					answer = Math.max(answer, sum);
				}
			}
		}
		System.out.println(answer);
	}
	public static int getSum(int x, int y, int[][] pos) {
		int sum = board[x][y];
		for(int[] p : pos) {
			int nx = x + p[0];
			int ny = y + p[1];
			if(nx>=0 && nx<N && ny>=0 && ny<M) {
				sum += board[nx][ny];
			}else {
				return 0;
			}
		}
		return sum;
	}

}
