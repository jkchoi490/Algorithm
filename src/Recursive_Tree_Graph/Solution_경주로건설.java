import java.util.*;

class Node implements Comparable<Node> {
    int x, y, direction, cost;

    public Node(int x, int y, int direction, int cost) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.cost = cost;
    }

    @Override
    public int compareTo(Node other) {
        return this.cost - other.cost;
    }
}

class Solution_경주로건설 {

    public static int solution(int[][] board) {
        int n = board.length;
        int[][][] cost = new int[n][n][4]; 
        for (int[][] row : cost) {
            for (int[] col : row) {
                Arrays.fill(col, Integer.MAX_VALUE);
            }
        }

        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (int i = 0; i < 4; i++) {
            pq.add(new Node(0, 0, i, 0));
            cost[0][0][i] = 0;
        }

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int x = current.x;
            int y = current.y;
            int direction = current.direction;
            int currentCost = current.cost;

            if (x == n - 1 && y == n - 1) {
                return currentCost;
            }

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (0 <= nx && nx < n && 0 <= ny && ny < n && board[nx][ny] == 0) {
                    int newCost = currentCost + (direction == i ? 100 : 600);
                    if (newCost < cost[nx][ny][i]) {
                        cost[nx][ny][i] = newCost;
                        pq.add(new Node(nx, ny, i, newCost));
                    }
                }
            }
        }

        return Integer.MAX_VALUE;
    }
}
