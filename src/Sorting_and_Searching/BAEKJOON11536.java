import java.io.*;
import java.util.*;

public class BAEKJOON11536 {
	static List<String> list, names;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		list = new ArrayList<>();
		names = new ArrayList<>();
		for(int i = 0; i<N; i++) {
			String name = br.readLine();
			list.add(name);
			names.add(name);
		}
		
		if(increase()) System.out.println("INCREASING");
		else if(decrease()) System.out.println("DECREASING");
		else System.out.println("NEITHER");
	}

	public static boolean increase() {
		
		Collections.sort(list);
	
		for(int i = 0; i<list.size(); i++) {
			if(!names.get(i).equals(list.get(i))) return false;
		}
		
		return true;
	}

	private static boolean decrease() {
		
		Collections.sort(list, Collections.reverseOrder());
		
		for(int i = 0; i<list.size(); i++) {
			if(!names.get(i).equals(list.get(i))) return false;
		}
		return true;
	}

}
