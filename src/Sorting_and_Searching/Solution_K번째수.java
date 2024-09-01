import java.util.*;

class Solution_K번째수 {
    public ArrayList<Integer> solution(int[] array, int[][] commands) {
        ArrayList<Integer> answer = new ArrayList<>();
        int len = commands.length;
        for(int n = 0; n<len; n++){
            int i = commands[n][0];
            int j = commands[n][1];
            int k = commands[n][2];
            int[] parts = new int[j-i+1];
            for(int m = i; m<=j; m++){
                parts[m-i]= array[m-1];
            }
            Arrays.sort(parts);
            answer.add(parts[k-1]);
        }
     
        return answer;
    }
}