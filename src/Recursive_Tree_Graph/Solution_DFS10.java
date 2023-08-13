package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_DFS10 { // 수열 추측하기
	
	static int N, F;
	static int[][] memo = new int[35][35]; //메모이제이션을 위한 배열 생성
	static int[] comb, perm, ch; //조합, 순열 수들을 구할 배열 생성
	static boolean flag=false; // 맨 윗줄 수들 구했으면 true 아니면 false
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		F = Integer.parseInt(st.nextToken());
		
		comb = new int[N];
		perm = new int[N];
		ch=new int[N+1];
		for(int i=0; i<N; i++){
			comb[i]=combi(N-1, i);
		}
		DFS(0, 0);
	}

	//조합수 먼저 구하기	-> comb 배열에 nC0부터 nCn까지 구함
	private static int combi(int n, int r) {
		if(memo[n][r] > 0) return memo[n][r]; //이미 구했으면 구한값 리턴
		if(n==r || r==0) return 1; //1을 리턴하는 경우
		else return memo[n][r] = combi(n-1, r-1) + combi(n-1, r); //memo 배열에 구한 값 저장
	
	}
	
	//순열 구하기
	public static void DFS(int L, int sum){
		if(flag) return; // 구해야하는 수열(맨 윗줄) 발견하면 종료
		if(L==N){
			if(sum==F){ //sum이 final number이면
				for(int x : perm) System.out.print(x+" "); //순열 출력 -> 구해야하는 가장 윗줄의 숫자들
				flag=true; //구해야하는 수열(맨 윗줄) 발견하면 true로 변경
			}
		}
		else{
			for(int num=1; num<=N; num++){ //1부터 가장 윗줄에 있는 숫자의 개수(N)까지 
				if(ch[num]==0){
					ch[num]=1;
					perm[L]=num;
					DFS(L+1, sum+(perm[L]*comb[L])); //조합 수 x 순열 수 들의 총 합 = final number
					ch[num]=0;
				}
			}
		}
	}
	
	
	
	}


