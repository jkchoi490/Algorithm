

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class sdokuPoint{
	int x, y;
	public sdokuPoint(int x, int y){
		this.x=x;
		this.y=y;
	}
}
public class BJ2580_스도쿠 {
	static int[][] graph;
	static ArrayList<sdokuPoint> points;
	
	public static void DFS(int cnt) {
		if(cnt==points.size()) { //0인 지점을 다 탐색하면 종료!
			for(int i = 0; i<9; i++) {
				for(int j = 0; j<9; j++) {
					System.out.print(graph[i][j]+" ");
				}
				System.out.println();
			}
			System.exit(0); //System.exit(0); 해줘야됨
		}
		
		else {
			sdokuPoint point = points.get(cnt);
			for(int i= 1; i<=9; i++) {
				if(check(point.x, point.y, i)) { // 가로세로정사각형에 중복되는 숫자 없으면
					graph[point.x][point.y] = i;
					DFS(cnt+1);
					graph[point.x][point.y] = 0;
				}
			}
		}
	}
	
	
	public static boolean check(int x, int y, int value) {
		//가로세로 검사
		for(int i = 0; i<9; i++) {
			if(graph[x][i] == value) return false; //가로줄에 value와 같은값이 있다면 false
			if(graph[i][y] == value) return false; //세로줄에 value와 같은값이 있다면 false
		}
		
		int nx = x/3*3; //9칸의 시작 x좌표
		int ny = y/3*3; //9칸의 시작 y좌표
		for(int i = nx; i<nx+3; i++) {
			for(int j = ny; j<ny+3; j++) {
				if(graph[i][j] == value) return false;
			}
		}
		
		return true;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		graph = new int[9][9];
		points = new ArrayList<>(); //0인지점 저장
		
		for(int i = 0; i<9; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<9; j++) {
				graph[i][j]=Integer.parseInt(st.nextToken());
				if(graph[i][j] == 0) points.add(new sdokuPoint(i, j));
			}
		}
		
		DFS(0);

	}

}
