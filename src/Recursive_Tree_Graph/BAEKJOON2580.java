import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class sudoku{
	int x, y;
	public sudoku(int x, int y) {
		this.x=x;
		this.y=y;
	}
}

public class BAEKJOON2580 {
	static ArrayList<sudoku> points;
	static int[][] board;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		board = new int[9][9];
		points = new ArrayList<>();
		StringTokenizer st;
		for(int i = 0; i<9; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<9; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
				if(board[i][j] == 0) points.add(new sudoku(i,j));
			}
		}
		
		DFS(0);
	}

	public static void DFS(int cnt) {
		if(cnt == points.size()) {
			for(int i = 0; i<9; i++) {
				for(int j = 0; j<9; j++) {
					System.out.print(board[i][j]+" ");
				}
				System.out.println();
			}
			System.exit(0);
		}
		
		else {
			sudoku point = points.get(cnt);
			for(int i = 1; i<=9; i++) {
				if(check(point.x, point.y, i)) {
					board[point.x][point.y] = i;
					DFS(cnt+1);
					board[point.x][point.y] = 0;
				}
			}
		}
		
	}

	public static boolean check(int x, int y, int value) {
		for(int i = 0; i<9; i++) {
			if(board[x][i] == value) return false;
			if(board[i][y] == value) return false;
		}
		int nx = x/3*3;
		int ny = y/3*3;
		
		for(int i = nx; i<nx+3; i++) {
			for(int j = ny; j<ny+3; j++) {
				if(board[i][j] == value) return false;
			}
		}
		
		return true;
	}

}
