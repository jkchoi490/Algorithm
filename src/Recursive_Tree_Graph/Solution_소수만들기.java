import java.util.*;

class Solution_소수만들기 {
    static int[] combi;
    static ArrayList<Integer> list;
    public int solution(int[] nums) {
        int answer = -1;
        combi = new int[3];
        list = new ArrayList<>();
        DFS(0, 0, nums);
        answer = countPrimeNumber(list);
        
        return answer;
    }
    
    public int countPrimeNumber(ArrayList<Integer> list){
        int cnt = 0;
        for(int num : list){
            int count = 1;
            for(int i = 2; i<=num; i++){
                if(num%i == 0) count++;
            }
            if(count<=2) {
                cnt++;
            }
        }
        
        return cnt;
    }
    
    public void DFS(int L, int start, int[] nums){
        if(L==3){
            int sum = 0;
            for(int x : combi){
                sum +=x;
            }
            list.add(sum);
            return;
        }else{
            for(int i = start; i<nums.length; i++){
                combi[L] = nums[i];
                DFS(L+1, i+1, nums);
            }
        }
    }
    
}