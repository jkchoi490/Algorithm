import java.util.*;

class Solution_크레인인형뽑기 {
    public int solution(int[][] board, int[] moves) {
        int answer = 0;
        Stack<Integer> stack = new Stack<>();
        for(int move : moves){
            for(int i = 0; i<board.length; i++){
                if(board[i][move-1] > 0) {
                    if(!stack.isEmpty() && board[i][move-1]==stack.peek()){
                        answer+=2;
                        stack.pop();
                    }
                    else stack.push(board[i][move-1]);
                    board[i][move-1] = 0;
                    break;
                }
            }
        }

        
        return answer;
    }
}