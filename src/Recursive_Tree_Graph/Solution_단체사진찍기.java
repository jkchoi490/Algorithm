import java.util.*;

class Solution_단체사진찍기 {
    static char[] arr = {'A','C','F','J','M','N','R','T'};
    static boolean[] visited;
    static char[] perm;
    static ArrayList<char[]> list;
    public int solution(int n, String[] data) {
        int answer = 0;
        visited = new boolean[8];
        list = new ArrayList<>();
        perm = new char[8];
        DFS(0, n, data);
        answer = list.size();
        return answer;
    }
    
    public void solve(char[] perm, int n, String[] data){
        boolean isCorrect = true;
        for(String d : data){
            String str = d;
            char p1 = str.charAt(0);
            char p2 = str.charAt(2);
            char yeonsan = str.charAt(3);
            int idx_diff = (int) str.charAt(4) - '0';
            int p1_idx = 0, p2_idx = 0;
            for(int i = 0; i<8; i++){
                if(perm[i] == p1) p1_idx = i;
                else if(perm[i]==p2) p2_idx = i;
            }
            if(yeonsan == '='){
                if(Math.abs(p1_idx - p2_idx)-1 != idx_diff){
                    isCorrect = false;
                    break;
                }
                
            }
            else if(yeonsan == '>'){
                if(Math.abs(p1_idx - p2_idx)-1 <= idx_diff){
                    isCorrect = false;
                    break;
                }
                
            }else if(yeonsan == '<'){
                if(Math.abs(p1_idx - p2_idx)-1 >= idx_diff){
                    isCorrect = false;
                    break;
                }
                
            }
        }
         if(isCorrect) list.add(perm);
    }
    
    public void DFS(int L, int n, String[] data){
        if(L==8){
            solve(perm, n, data);
            return;
        }else{
            for(int i = 0; i<8; i++){
                if(!visited[i]){
                    visited[i] = true;
                    perm[L] = arr[i];
                    DFS(L+1, n, data);
                    visited[i] = false;
                }
            }
        }
    }
}