import java.io.*;
import java.util.*;

public class BAEKJOON9081 {

	static ArrayList<String> list;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int tc = 1; tc<=T; tc++) {
			String word = br.readLine();
			char[] arr = word.toCharArray();
			boolean[] ch = new boolean[arr.length];
			char[] pm = new char[arr.length];
			list = new ArrayList<>();
			DFS(0, arr, ch, pm);
			Collections.sort(list);

			for(int i = 0; i<list.size(); i++) {
				if(list.get(i).equals(word)) {
					if(i == list.size()-1) {
						sb.append(list.get(i)).append("\n");
						break;
					}
					else{
						sb.append(list.get(i+1)).append("\n");
						break;
					}
				}
			}		
		}
		System.out.println(sb);
	}

	public static void DFS(int L, char[] arr, boolean[] ch, char[] pm) {
		if(L == arr.length) {
			String str = "";
			for(char x : pm) {
				str += x;
			}
			if(!list.contains(str)) list.add(str);
		}else {
			for(int i = 0; i<arr.length; i++) {
				if(ch[i]==false) {
					ch[i]=true;
					pm[L] = arr[i];
					DFS(L+1,arr,ch,pm);
					ch[i] = false;
				}
			}
		}
		
	}

}
