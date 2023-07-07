package TimeComplexity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJ_24267알고리즘수업6 {

/*
MenOfPassion(A[], n) {
    sum <- 0;
    for i <- 1 to n - 2 
        for j <- i + 1 to n - 1
            for k <- j + 1 to n
                sum <- sum + A[i] × A[j] × A[k]; # 코드1
    return sum;
}

 n(1 ≤ n ≤ 500,000) -> 
 시간복잡도 :
 if n = 7 
 i -> j -> k
 1 -> 2 -> 3
 1 -> 2 -> 5
 1 -> 2 -> 6
 1 -> 2 -> 7
 1 -> 3 -> 4
 1 -> 3 -> 5
 1 -> 3 -> 6
 1 -> 3 -> 7
 ...
 
 조합 : nC3 -> n!/r!*(n-r)! -> n(n-1)(n-2)/6
*/	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long n = Long.parseLong(br.readLine());
		System.out.println((n*n*n-3*n*n+2*n)/6); //코드 1의 수행횟수
		System.out.println(3); // 
	}

}
