import java.util.*;

class Solution_가장많이받은선물 {
    public int solution(String[] friends, String[] gifts) {
        int answer = 0;
        int n = friends.length;
        int[] result = new int[n];
        int[][] arr = new int[n][n];
        for(String g : gifts){
            String[] gift = g.split(" ");
            String giver = gift[0];
            String receiver = gift[1];
            int a = 0, b = 0;
            for(int i = 0; i<n; i++){
                if(friends[i].equals(giver)) a = i;
                if(friends[i].equals(receiver)) b = i;
            }
            arr[a][b]+=1;
        }
        
        int[] pre_jisu = new int[n];

        for(int i = 0; i<n; i++){
             int give = 0, receive = 0;
            for(int j = 0; j<n; j++){
                give += arr[i][j];
                receive += arr[j][i];
            }
            pre_jisu[i] = give - receive;
        }
        
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(i==j) continue;
                if(arr[i][j] > arr[j][i] || (arr[i][j] == arr[j][i]) && pre_jisu[i]>pre_jisu[j]){ 
                    result[i]++;
                }
                
            }
        }

        Arrays.sort(result);
        answer = result[result.length-1];
        return answer;
    }
}