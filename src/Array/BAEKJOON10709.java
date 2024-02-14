import java.io.*;
import java.util.*;

class Cloud{
	int x, y;
	public Cloud(int x, int y) {
		this.x=x;
		this.y=y;
	}
}
public class BAEKJOON10709 {

	static int H, W;
	static char[][] map;
	static int[][] ch;
	static Queue<Cloud> q;
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		H = Integer.parseInt(st.nextToken());
		W = Integer.parseInt(st.nextToken());
		map = new char[H][W];
		ch = new int[H][W];
		q = new LinkedList<>();
		for(int i = 0; i<H; i++) {
			String str = br.readLine();
			for(int j = 0; j<W; j++) {
				map[i][j] = str.charAt(j);
				if(map[i][j]=='c') {
					ch[i][j] = 0;
					q.offer(new Cloud(i,j));
				}
				else ch[i][j] = -1;
			}
		}
		
		int time = 0;
		while(!q.isEmpty()) {
			time++;
			int len = q.size(); 
			for(int i = 0; i<len; i++) { 
				Cloud cloud = q.poll();
				int nx = cloud.x;
				int ny = cloud.y+1;
				if(nx>=H || ny >=W || ch[nx][ny]==0) continue;
				ch[nx][ny] = time;
				q.offer(new Cloud(nx, ny));
			}
		}
		
		for(int i = 0; i<H; i++) {
			for(int j = 0; j<W; j++) {
				System.out.print(ch[i][j]+" ");
			}
			System.out.println();
		}
		
		
	}

}
