package Implementation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// Bot saves princess - 2
public class Solution_BotSavesPrincess2 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        String[] grid = new String[n];

        for (int i = 0; i < n; i++) {
            grid[i] = br.readLine();
        }

        br.close();

        solve(n, r, c, grid);
    }

    static void solve(int n, int r, int c, String[] grid) {
        int pr = -1, pc = -1;

        for (int i = 0; i < n; i++) {
            int idx = grid[i].indexOf('p');
            if (idx != -1) {
                pr = i;
                pc = idx;
                break;
            }
        }

        if (pr < r) {
            System.out.println("UP");
        } else if (pr > r) {
            System.out.println("DOWN");
        }

        else if (pc < c) {
            System.out.println("LEFT");
        } else if (pc > c) {
            System.out.println("RIGHT");
        }
    }
}