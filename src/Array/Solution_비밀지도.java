import java.util.*;

class Solution_비밀지도 {
    public char[][] getArr(int[] arr,int n){
        char[][] result = new char[n][n];
        for(int i = 0; i<arr.length; i++){
           String line =  Integer.toBinaryString(arr[i]);
            String ans = "";
            int cnt = line.length();
            if(line.length() < n){
               while(true){
                ans += '0';
                cnt++;
                if(cnt == n){
                    ans += line;
                     break;
                }
               }
            }
            else ans = line;
            result[i] = ans.toCharArray();
        }
        
        return result;
    }
    public String[] solution(int n, int[] arr1, int[] arr2) {
        String[] answer = new String[n];
        char[][] a1 = getArr(arr1,n);
        char[][] a2 = getArr(arr2,n);
        char[][] ans = new char[n][n];
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(a1[i][j] == '1' || a2[i][j] == '1'){
                    ans[i][j] = '#';
                }
                else ans[i][j] = ' ';
            }
        }
        
        for(int i = 0; i<n; i++){
            String str = "";
             for(int j = 0; j<n; j++){
                str += ans[i][j];
             }
            answer[i] = str;
        }
        
        return answer;
    }
}