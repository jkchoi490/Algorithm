package BinarySearch;

import java.util.*;

class Solution_징검다리건너기 {
    public long solution(int[] stones, int k) {
        
        long left = 1;
        long right = 200000000;
        while(left<=right){
            long mid = (left+right)/2;
            if(canCross(k, stones, mid)){
                left = mid + 1;
            }else{
                right = mid - 1;
            }
            
        }
        return right;
    }
    
    public boolean canCross(int k, int[] stones, long mid){
        int skip = 0;
        
        for(int stone : stones){
            if(stone-mid < 0){
                skip++;
                if(skip >= k){
                    return false;
                }
            }else{
                skip = 0;
            }
        }
        return true;
    }
}