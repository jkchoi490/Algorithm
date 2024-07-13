import java.util.*;

class Solution_모음사전 {
    static char[] arr = {'A','E','I','O','U'};
    static boolean[] visited;
    static char[] combi;
    static ArrayList<String> list;
    public int solution(String word) {
        int answer = 0;
        visited = new boolean[arr.length];
        list = new ArrayList<>();
        for(int cnt = 1; cnt<=arr.length; cnt++){
            combi = new char[cnt];    
             DFS(0,cnt);
        }
        
        Collections.sort(list);
        for(String x: list){
            answer++;
            if(x.equals(word)) break; 
        }
        return answer;
    }
    public void DFS(int L, int cnt){
        if(L == cnt){
            String ans = "";
            for(char x: combi){
                ans += x;
            }
            list.add(ans);
        }else{
            for(int i = 0; i<arr.length; i++){
                    combi[L] = arr[i];
                    DFS(L+1, cnt);
            }
        }
    }
  
}