import java.util.*;

class Solution_신고결과받기 {
    public int[] solution(String[] id_list, String[] report, int k) {
        int n = id_list.length;
        int[] answer = new int[n];
        int[][] arr = new int[n][n];
        for(int i = 0; i<report.length; i++){
            String[] repo = report[i].split(" ");
            String report_a = repo[0];
            String report_b = repo[1];
            int a = 0, b = 0;
            for(int j = 0; j<n; j++){
                if(report_a.equals(id_list[j])) a = j;
                if(report_b.equals(id_list[j])) b = j;
            }
            arr[a][b] = 1;
        }
        int[] singo = new int[n];
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
               singo[i] += arr[j][i];
            }
        }
        ArrayList<Integer> singo_idx = new ArrayList<>();
        for(int i = 0; i<n; i++){
            if(singo[i] >= k) singo_idx.add(i);
        }
        
        for(int i = 0; i<n; i++){
            for(int idx : singo_idx){
                if(arr[i][idx] > 0) answer[i]++;
            }
          
        }
        
        
        return answer;
    }
}