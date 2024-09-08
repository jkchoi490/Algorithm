import java.util.*;

class Solution_쿼드압축후개수세기 {
    static int[] answer;
    
    public int[] solution(int[][] arr) {
        answer = new int[2];
        
        DFS(arr, 0, 0, arr.length);
        return answer;
    }
    
    public static void DFS(int[][] arr, int x, int y, int size){
        if(checkAllSame(arr, x, y, size)){
            answer[arr[x][y]]++;
            return;
        }
        int newSize = size / 2;
        DFS(arr, x, y, newSize);
        DFS(arr, x, y+newSize, newSize);
        DFS(arr, x+newSize, y, newSize);
        DFS(arr, x+newSize, y+newSize, newSize);
    }
    
    public static boolean checkAllSame(int[][] arr, int x, int y, int size){
        int value = arr[x][y];
        for(int i = x; i<x+size; i++){
            for(int j = y; j<y+size; j++){
                if(arr[i][j] != value){
                    return false;
                }
            }
        }
        return true;
    }
}