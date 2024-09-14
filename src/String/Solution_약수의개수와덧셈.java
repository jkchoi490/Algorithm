class Solution_약수의개수와덧셈 {
    public int solution(int left, int right) {
        int answer = 0;
        for(int x = left; x<=right; x++){
            int cnt = 0;
            for(int i = 1; i<=x; i++){
                if(x%i == 0) cnt++;
            }
            if(cnt % 2 == 0){
                answer +=x;
            }
            else answer -=x;
        }
        
        
        return answer;
    }
}