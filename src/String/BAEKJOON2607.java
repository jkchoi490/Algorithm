import java.io.*;
import java.util.*;

public class BAEKJOON2607 {

	static int n;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		String word = br.readLine();
		int cnt = 0;
		for(int i = 1; i<n; i++) {
			String w = br.readLine();
			if(check(word, w)) cnt++;
		}
		System.out.println(cnt);
	}
	
	public static boolean check(String word, String w) {
		int[] wordArr = new int[26];
		int[] wArr = new int[26];
		for(int i = 0; i<word.length(); i++) {
			wordArr[word.charAt(i) - 'A'] += 1;
		}
		for(int i = 0; i<w.length(); i++) {
			wArr[w.charAt(i)-'A'] += 1; 
		}
		
		boolean flag = true;
		for(int i = 0; i<26; i++) {
			if(wordArr[i]!=wArr[i]) {
				flag = false;
				break;
			}
		}
		
		if(!flag) {
			int len_diff = Math.abs(word.length() - w.length());
			if(len_diff == 1) {
				int cntDiff = 0;
				for(int i = 0; i<26; i++) {
					if(wordArr[i] != wArr[i]) {
						cntDiff += Math.abs(wordArr[i]-wArr[i]);
					}
				}
				
				if(cntDiff == 2 || cntDiff==1) { 
					flag = true;
				}
			}
		
			else if(len_diff == 0) {
				int cntDiff = 0;
				for(int i = 0; i<26; i++) {
					if(wordArr[i] != wArr[i]) {
						cntDiff +=Math.abs(wordArr[i]-wArr[i]);
					}
				}
				if(cntDiff == 2) {
					flag = true;
				}
			}
		}
		
		return flag;
	}

}
