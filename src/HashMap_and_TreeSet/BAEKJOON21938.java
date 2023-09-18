import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class BAEKJOON21938 {

	public static int N, M;
	public static TreeSet<Pair> asc = new TreeSet<Pair>(new AscendingComparator());
	public static Map<Integer,Integer> map = new HashMap<>(); 
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		for(int i=0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int P = Integer.parseInt(st.nextToken());
			int L = Integer.parseInt(st.nextToken());
			
			asc.add(new Pair(P, L));
			map.put(P, L);
		}
		
		M = Integer.parseInt(br.readLine());
		
		for(int j=0; j<M; j++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			String cmd = st.nextToken();
						
			if(cmd.equals("recommend")) {

				int x = Integer.parseInt(st.nextToken());
				
				if(x == 1) {
					System.out.println(asc.last().P);
				}
				else {
		
					System.out.println(asc.first().P);
				}
			} 
			else if(cmd.equals("add")){

				int P = Integer.parseInt(st.nextToken());
				int L = Integer.parseInt(st.nextToken());
				
				asc.add(new Pair(P, L));
				map.put(P, L);
			}
			else {
				//solved P
				int P = Integer.parseInt(st.nextToken());
				int L = map.get(P);
				
				asc.remove(new Pair(P, L));
				map.remove(P);
			}
		}
	}
}


class AscendingComparator implements Comparator<Pair>{
	
	@Override
	public int compare(Pair a, Pair b) {
		if(a.L < b.L) return -1;
		else if(a.L == b.L) {
			if(a.P < b.P) return -1;
			else if(a.P == b.P) return 0;
			else return 1;
		}
		else return 1;
	}
}

class Pair{
	int P, L;
	
	Pair(int p, int l){
		this.P = p;
		this.L = l;
	}
}