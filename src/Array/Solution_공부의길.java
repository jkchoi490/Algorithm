
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class lecture { //lecture 클래스 생성
    int l; //1초 뒤에 시작 
    int r; //r초 뒤에 끝남
    int index; //index 번째 수업
}

public class Solution_공부의길 { //공부의 길

    static lecture[] arr;
    static int[][] map;//i번째 수업에서 j번째 수업 이동 시간을 담을 배열
    static int N, S;
    static int[] DP; //DP 사용을 위한 배열 생성
    static lecture[] L, R;
    static int MAX_VALUE = 500;

    static void merge(int l, int m, int r) {
        int n1 = m - l + 1; //중간값 - 시작시간 +1
        int n2 = r - m; // 끝나는시간 - 중간값
        for (int i = 0; i < n1; i++) {
            L[i] = arr[l + i]; 
        }
        for (int j = 0; j < n2; j++) {
            R[j] = arr[m + 1 + j];
        }
        int i = 0, j = 0, n = l;
        while (i < n1 && j < n2) {
            if (L[i].r < R[j].r) {
                arr[n] = L[i];
                i++;
            } else {
                arr[n] = R[j];
                j++;
            }
            n++;
        }
        while (i < n1) {
            arr[n] = L[i];
            i++;
            n++;
        }
        while (j < n2) {
            arr[n] = R[j];
            j++;
            n++;
        }
    }

    static void mergeSort(int l, int r) { //분할 정복 
        if (l < r) { //끝나는 시간이 시작 시간보다 크면 
            int m = (l + r) / 2; //중간값 
            mergeSort(l, m); 
            mergeSort(m + 1, r); //merge sort 진행
            merge(l, m, r);
        }
    }

    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringTokenizer st;
        for (int test_case = 1; test_case <= T; test_case++) {
        	st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken()); // 수업의 번호
            S = Integer.parseInt(st.nextToken()); // S번째 수업
            --S;
            arr = new lecture[MAX_VALUE]; //입력값 받기
            for (int i = 0; i < N; i++) {
            	st = new StringTokenizer(br.readLine());
                arr[i] = new lecture(); //i번째 수업
                arr[i].l = Integer.parseInt(st.nextToken()); //l초 뒤에 시작
                arr[i].r = Integer.parseInt(st.nextToken()); //r초 뒤에 끝난다
                arr[i].index = i; //i번째 수업
            }
            map = new int[MAX_VALUE][MAX_VALUE]; //i번 수업에서 j번 수업 이동시간 Wi,j map에 저장한다
            for (int i = 0; i < N; i++) {
            	st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            DP = new int[MAX_VALUE]; // 방문 처리할 dp 배열 생성
            L = new lecture[N];
            R = new lecture[N];
            for (int k = 0; k < N; k++) {
            	for (int i = 0; i < N; i++) {
            		for (int j = 0; j < N; j++) {
                        if (map[i][j] > map[i][k] + map[k][j]) { 
                            map[i][j] = map[i][k] + map[k][j]; 
                        }
                    }
                }
            }
            mergeSort(0, N - 1); //합병 정렬
            
            int ans = 0;
            for (int i = 0; i < N; i++) {
            	DP[i] = arr[i].r - Math.max(arr[i].l, map[S][arr[i].index]);
            	
                for (int j = 0; j < i; j++) {
                	DP[i] = Math.max(DP[i], DP[j] + arr[i].r - Math.max(arr[i].l, arr[j].r+map[arr[j].index][arr[i].index]));
                }
                ans = Math.max(ans, DP[i]);
            }
            System.out.println("#"+test_case+" "+ans); // 강의를 들은 총 시간 최대치
        }
  
    }
}
