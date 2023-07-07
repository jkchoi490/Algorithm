package TimeComplexity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_24313알고리즘수업7 {

/*
 
 O(g(n)) = {f(n) | 모든 n ≥ n0에 대하여 f(n) ≤ c × g(n)인 양의 상수 c와 n0가 존재한다}
 f(n) = a1n + a0, 양의 정수 c, n0가 주어질 경우 O(n) 정의를 만족하는지 알아보자.

*/

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int a1 = Integer.parseInt(st.nextToken());
		int a0 = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(br.readLine());
		int n0 = Integer.parseInt(br.readLine());		
/*
		if(a1*n0+a0 <= c*n0) System.out.println(1); 로 시도했을때 
		-> c>=a1 조건 추가해 주어야한다.
		-> a0가 음수일 경우 문제가 생기기 때문
		
		ex) a1 = 5, a0 = -2, c = 3일 경우 f(n) ≤ c × g(n)
		  1) n = 1, f(n) ≤ c × g(n)성립 (3<=3)
		  2) n = 2, f(n) ≤ c × g(n)성립하지 않음 (8<=3)
		
*/
		
		if((a1*n0+a0 <= c*n0) && (c>=a1)) System.out.println(1);
		else System.out.println(0);
	}

}
