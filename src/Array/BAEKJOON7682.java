import java.io.*;
import java.util.*;

public class BAEKJOON7682 {
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = "";
        while (true) {
            str = br.readLine();
            if (str.equals("end")) break;
            System.out.println(solve(str));
        }
    }

    public static String solve(String str) {
        char[][] board = new char[3][3];
        int cnt = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = str.charAt(cnt++);
            }
        }
        
        int xCount = countX(board);
        int oCount = countO(board);
        boolean xWins = checkWin(board, 'X');
        boolean oWins = checkWin(board, 'O');

        if (xCount < oCount || xCount > oCount + 1) {
            return "invalid";
        }
        
        if (xWins && oWins) {
            return "invalid";
        }
        
        if (xWins && xCount != oCount + 1) {
            return "invalid";
        }
        
        if (oWins && xCount != oCount) {
            return "invalid";
        }
        
        if (!xWins && !oWins && xCount + oCount != 9) {
            return "invalid";
        }

        return "valid";
    }

    public static int countX(char[][] board) {
        int cnt = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'X') cnt++;
            }
        }
        return cnt;
    }

    public static int countO(char[][] board) {
        int cnt = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'O') cnt++;
            }
        }
        return cnt;
    }

    public static boolean checkWin(char[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }
}
