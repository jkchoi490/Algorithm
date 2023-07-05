package TimeComplexity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJ_24263알고리즘수업2 {

/*

MenOfPassion(A[], n) {
    sum <- 0;
    for i <- 1 to n  
        sum <- sum + A[i]; # 코드1 -> for문을 n번 반복하므로 코드1의 수행횟수는 n
    return sum;
}
  
*/
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		
		System.out.println(n); //-> for문을 n번 반복하므로 코드1의 수행횟수는 n
		System.out.println(1); // 시간복잡도가 O(N) 이므로 최고차항의 차수는 1
	}

}
