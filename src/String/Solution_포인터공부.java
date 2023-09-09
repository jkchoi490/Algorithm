import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Solution_포인터공부 { // BJ 25703. 포인터 공부

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		solution(N);

	}

private static void solution(int n) {
	String basic = "int a;\n"+"int *ptr = &a;";
	if(n == 1) {
		System.out.println(basic);
	}
	else if(n==2) {
		System.out.println(basic);
		System.out.println("int **ptr2 = &ptr;");
	}
	else {
		System.out.println(basic);
		System.out.println("int **ptr2 = &ptr;");
		for(int i = 3; i<=n; i++) {
			System.out.print("int "+"*".repeat(i)+"ptr"+i+" = &ptr"+(i-1)+";"+"\n");
		}
	}
	
	
}

}
