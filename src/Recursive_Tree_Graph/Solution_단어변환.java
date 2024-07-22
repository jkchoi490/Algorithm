class Solution_단어변환 {

	static int answer = 51;
    static boolean[] visited;
    public int solution(String begin, String target, String[] words) {
        visited = new boolean[words.length];
        DFS(0,begin,target,words);
        
        if(answer == 51) return 0;
        return answer;
    }
    
    public static void DFS(int L, String begin, String target, String[] words){
       
        if(begin.equals(target)){
                answer = Math.min(answer, L);
            }
        else{
        for(int i = 0; i<words.length; i++){
            if(!visited[i] && check(begin, words[i])){
                visited[i] = true;
                DFS(L+1, words[i], target, words);
                visited[i] = false;
            }
        }
            
        }
    }
    public static boolean check(String begin, String target){
        int cnt = 0;
        for(int i = 0; i<begin.length(); i++){
            if(begin.charAt(i) == target.charAt(i)){
                cnt++;
            }
        }
        if(cnt == begin.length()-1) return true;
        return false;
    }
}