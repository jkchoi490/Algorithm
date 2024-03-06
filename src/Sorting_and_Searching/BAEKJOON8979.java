import java.io.*;
import java.util.*;

class Nation{
	int name, gold, silver, bronze;
	public Nation(int name, int gold, int silver, int bronze) {
		this.name=name;
		this.gold=gold;
		this.silver=silver;
		this.bronze=bronze;
	}
}
public class BAEKJOON8979 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); 
		int K = Integer.parseInt(st.nextToken()); 
		List<Nation> list = new ArrayList<>();
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			int name = Integer.parseInt(st.nextToken());
			int gold = Integer.parseInt(st.nextToken());
			int silver = Integer.parseInt(st.nextToken());
			int bronze = Integer.parseInt(st.nextToken());
			list.add(new Nation(name, gold, silver, bronze));
		}

		Collections.sort(list, new Comparator<Nation>() {
			
			@Override
			public int compare(Nation o1, Nation o2) {
	
				if(o1.gold < o2.gold) return 1;
				else if(o1.gold==o2.gold) {
					if(o1.silver < o2.silver) return 1;
					else if(o1.silver==o2.silver) {
						if(o1.bronze < o2.bronze) return 1;
					}
				}
				return -1;
			}
		});
		
		
	    int answer = 1;
        int cnt = 1;

        if (list.get(0).name == K) {
            System.out.println(1);
            return;
        }

        for (int i = 1; i < N; i++) {

            Nation prev = list.get(i-1);
            Nation now = list.get(i);

            if ((prev.gold != now.gold) || (prev.silver != now.silver) || (prev.bronze != now.bronze)) {
                answer += cnt;
                cnt = 1;
            }
            else
                cnt++;

            if (now.name == K) {
                System.out.println(answer);
                break;
            }
        }
		
		
		
	}

}
