package TimeComplexity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJ_24264알고리즘수업3 {
/*
MenOfPassion(A[], n) {
    sum <- 0;
    for i <- 1 to n
        for j <- 1 to n
            sum <- sum + A[i] × A[j]; # 코드1
    return sum;
}

입력의 크기 n(1 ≤ n ≤ 500,000)

*/
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));		
/* int로 시도했을때 
		int n = Integer.parseInt(br.readLine()); -> 입력의 크기 n의 범위가 (1 ≤ n ≤ 500,000)이므로
		System.out.println(n*n);  n*n 했을때 overflow가 발생할 수 있다
*/		
		long n = Long.parseLong(br.readLine());
		
		System.out.println(n*n);// 시간복잡도O(N^2)
		System.out.println(2); //최고차항의 차수 2
	}

}
