package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class BJ_1759암호만들기 {
	
	static int L, C;
	static String[] arr;
	static String[] combi;
	
	public static void DFS(int leng, int s) {
		Arrays.sort(arr);
		if(leng==L) {
			String tmp = "";
			for(String x : combi) {
				tmp += x;
			}
			if(checkwords(tmp)) {
				System.out.println(tmp);
				return;
			}
			
		}else {
			for(int i = s; i<arr.length; i++) {
				combi[leng] = arr[i];
				DFS(leng+1, i+1);
			}		
		}
	}
	
	public static boolean checkwords(String tmp) {
		int mcnt = 0;
		int jcnt = 0; 
		for(int i = 0; i<tmp.length(); i++) {
			int t = tmp.charAt(i);
			if(t == 'a' || t == 'e' ||t == 'i' ||t == 'o' ||t == 'u' ) mcnt++;
			else jcnt++;
		}
		if(mcnt >= 1 && jcnt>=2) return true;
		else return false;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		L = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		arr = new String[C];
		combi = new String[L];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i<C; i++) {
			 arr[i] = st.nextToken();
		}
		DFS(0,0);
	}

}
