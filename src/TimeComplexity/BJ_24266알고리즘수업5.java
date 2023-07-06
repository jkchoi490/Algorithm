package TimeComplexity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJ_24266알고리즘수업5 {
	/*

MenOfPassion(A[], n) {
    sum <- 0;
    for i <- 1 to n
        for j <- 1 to n
            for k <- 1 to n
                sum <- sum + A[i] × A[j] × A[k]; # 코드1
    return sum;
}

i = 1 ~ n
j = 1 ~ n
k = 1 ~ n
시간복잡도 -> n*n*n = O(N^3)
n(1 ≤ n ≤ 500,000) -> 125*10^15 -> long형

	*/
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long n = Long.parseLong(br.readLine());
		System.out.println(n*n*n);
		System.out.println(3);

	}

}
