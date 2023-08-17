package BruteForce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_6603로또 {	// 완전 탐색

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int k = Integer.parseInt(st.nextToken());
		int[] arr = new int[k];
		for(int i = 0; i<k; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

	}

}
