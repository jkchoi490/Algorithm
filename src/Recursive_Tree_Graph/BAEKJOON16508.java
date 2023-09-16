import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

class Book{
	int price;
	String title;
	
	public Book(int price, String title) {
		this.price = price;
		this.title = title;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price=price;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
public class BAEKJOON16508 {
	static List<Book> books = new ArrayList<>();
	static String T;
	static int[] cnt = new int[26];
	static int[] select_cnt = new int[26];
	static int n, min = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = br.readLine();
		
		for(int k = 0; k<T.length(); k++) {
			cnt[T.charAt(k) - 'A']++;
		}
		
		n = Integer.parseInt(br.readLine());
		StringTokenizer st;
		for(int i = 0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			int c = Integer.parseInt(st.nextToken());
			String w = st.nextToken();
			books.add(new Book(c,w));
		}
	 dfs(0,0);
	 
	 if(min == Integer.MAX_VALUE)System.out.println(-1);
	 else System.out.println(min);
		
	}

	private static void dfs(int index, int total) {
		if(index == n) {
			if(check()) min = Math.min(min, total);
			return;
		}
		
		for(int i = 0; i<books.get(index).getTitle().length(); i++) {
			select_cnt[books.get(index).getTitle().charAt(i)-'A']++;
		}
		dfs(index+1, total+books.get(index).getPrice());
		
		for(int i = 0; i<books.get(index).getTitle().length(); i++) {
			select_cnt[books.get(index).getTitle().charAt(i)-'A']--;
		}
		dfs(index+1, total);
	
}
	private static boolean check() {
		for(int i = 0; i<26; i++) {
			if(cnt[i] > select_cnt[i]) return false;
		}
		return true;
	}

}
