import java.io.*;
import java.util.*;

class Word{
	String word;
	int cnt;
	int length;
	public Word(String word, int cnt, int length) {
		this.word=word;
		this.cnt=cnt;
		this.length=length;
	}
}
public class BAEKJOON20920 {

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		HashMap<String, Integer> cntMap = new HashMap<>();
	
		for(int i = 0; i<N; i++) {
			String word = br.readLine();
			if(word.length() < M) continue;
			cntMap.put(word, cntMap.getOrDefault(word, 0)+1);
		}
		Word[] list  = new Word[cntMap.size()];
	
		int cnt = 0;
		for(String key: cntMap.keySet()) {
			list[cnt] = new Word(key, cntMap.get(key), key.length());	
			cnt++;
		}
			
		Arrays.sort(list, new Comparator<Word>(){

			@Override
			public int compare(Word o1, Word o2) {
				if(o1.cnt==o2.cnt) {
					if(o1.length == o2.length) {
						return o1.word.compareTo(o2.word);
					}
					return o2.length- o1.length;
				}
				return o2.cnt -o1.cnt;
			}
			
		});
		
		StringBuilder sb = new StringBuilder();
		for(int j = 0; j<list.length; j++) {
			sb.append(list[j].word).append("\n");
		}
		
		
		System.out.println(sb);
		
		
	}

}
