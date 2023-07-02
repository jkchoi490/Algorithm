package Recursive_Tree_Graph;

/*
 * 자연수 N이 입력되면 재귀함수를 이용하여 1부터 N까지를 출력하는
 * 프로그램을 작성하세요  
 * 입력 3
 * 출력 1 2 3
 * 
 * */

public class Solution_Recursive1 {
	public static void DFS(int n) {
		if(n == 0) return;
		else {
			DFS(n-1);
			System.out.print(n+" ");
		}
	}
	
	public static void main(String[] args) {
		DFS(3);

	}

}
