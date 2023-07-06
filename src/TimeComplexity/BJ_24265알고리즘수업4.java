package TimeComplexity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJ_24265알고리즘수업4 {
/*

MenOfPassion(A[], n) {
    sum <- 0;
    for i <- 1 to n - 1
        for j <- i + 1 to n
            sum <- sum + A[i] × A[j]; # 코드1
    return sum;
}

입력의 크기 n(1 ≤ n ≤ 500,000) 

n이 7이라 가정하면, 첫번째 for문 : i는 1부터 6까지 반복
두번째 for문
1) i=1, j=2,3,4,5,6,7 -> 6번
2) i=2, j=3,4,5,6,7 -> 5번
3) i=3, j=4,5,6,7 -> 4번
4) i=4, j=5,6,7 -> 3번
5) i=5, j=6,7 -> 2번
6) i=6, j=7  -> 1번
-> 입력 n이 주어질 경우 수행횟수 : 1부터  n-1까지의 합 = (n*(n-1))/2
-> n^2이 최고차항이므로 최고 차수는 2
-> 1 ≤ n ≤ 500,000이므로  int형이 아닌 long (-9223372036854775808 ~ 9223372036854775807)
*/
		
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long n = Long.parseLong(br.readLine());
		System.out.println((n*(n-1))/2);
		System.out.println(2);
	}

}
