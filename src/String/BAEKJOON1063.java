import java.io.*;
import java.util.*;

public class BAEKJOON1063 {

	static char[] Row = {'8', '7', '6', '5', '4', '3', '2', '1'}; 
	static char[] Col = {'A','B','C','D','E','F','G','H'};
	static String[] dir = {"R","L","B","T","RT","LT","RB","LB"};
	static int dx[] = {0, 0, 1,-1,-1,-1,1,1};
	static int dy[] = {1, -1,0,0,1,-1,1,-1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		String king = st.nextToken();
		String stone = st.nextToken();
		int king_x = 0, king_y = 0;
		int stone_x = 0, stone_y = 0;
		for(int i = 0; i<Col.length; i++) {
			if(king.charAt(0) == Col[i]) {
				king_y = i; 
			}
			if(king.charAt(1) == Row[i]) {
				king_x = i;
			}
			
			if(stone.charAt(0) == Col[i]) {
				stone_y = i; 
			}
			if(stone.charAt(1) == Row[i]) {
				stone_x = i;
			}
		}
		
		int n = Integer.parseInt(st.nextToken());
		
		int nx = 0, ny = 0;
		for(int i = 0; i<n; i++) {
			String move = br.readLine();
			for(int j = 0; j<dir.length; j++) {
				if(move.equals(dir[j])) {
					nx = king_x + dx[j];
					ny = king_y + dy[j];
		
					
					if(nx == stone_x && ny == stone_y && stone_x>=0 && stone_x<8 && stone_y>=0 && stone_y<8) {
						stone_x += dx[j];
						stone_y += dy[j];
						if(stone_x<0 || stone_x>=8 || stone_y<0 || stone_y>=8) {
							stone_x -= dx[j];
							stone_y -= dy[j];
							nx -=dx[j];
							ny -=dy[j];
						}
					}
					
				
				}
				
			}
			
			if(nx <0 || nx >=8 || ny<0 || ny>=8 || stone_x <0 || stone_x>=8 || stone_y<0 || stone_y>=8 && nx == stone_x && ny == stone_y) {
				nx = king_x;
				ny = king_y;
				
			}else {
			king_x = nx;
			king_y = ny;
			}

		}
		
		
		StringBuilder sb = new StringBuilder();
		sb.append(Col[king_y]).append(Row[king_x]).append("\n").append(Col[stone_y]).append(Row[stone_x]);
	
		System.out.println(sb);
		
		
		
	}

}
