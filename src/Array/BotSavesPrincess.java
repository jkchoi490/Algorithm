package Array;

import java.io.*;

//Bot Saves Princess (공주님 구하기)
public class BotSavesPrincess {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        char[][] grid = new char[n][n];

        for(int i = 0; i < n; i++) {
            String line = br.readLine();
            for(int j = 0; j < n; j++) {
                grid[i][j] = line.charAt(j);
            }
        }
        savePrincess(n, grid);

    }

    static void savePrincess(int n, char[][] grid) {

        int botRow = -1, botCol = -1;
        int princessRow = -1, princessCol = -1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                char cell = grid[i][j];
                if (cell == 'm') {
                    botRow = i;
                    botCol = j;
                } else if (cell == 'p') {
                    princessRow = i;
                    princessCol = j;
                }
            }
        }

        int rowDiff = princessRow - botRow;
        String verticalMove;
        if (rowDiff < 0) {
            verticalMove = "UP";
        } else {
            verticalMove = "DOWN";
        }


        int moves = Math.abs(rowDiff);
        for (int i = 0; i < moves; i++) {
            System.out.println(verticalMove);
        }

        int colDiff = princessCol - botCol;

        String horizontalMove;
        if (colDiff < 0) {
            horizontalMove = "LEFT";
        } else {
            horizontalMove = "RIGHT";
        }

        moves = Math.abs(colDiff);
        for (int i = 0; i < moves; i++) {
            System.out.println(horizontalMove);
        }
    }
}